package TheSmith.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Nillable;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

public class Bank extends TheSmith.Task {

    private static int[] choice;
    private static int[] itemsFromBank = {0, 0};
    private static int[] amountItemsFromBank = {0, 0};
    private static int[] amountNeeded = {0, 0};
    private static int itemId = 0;

    public Bank(ClientContext ctx, int[] choice) {
        super(ctx);
        this.choice = choice;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 0 || ctx.backpack.select().id(choice[0]).count() < choice[2] ||
                (choice[3] != 0 && ctx.backpack.select().id(choice[3]).count() < choice[5]);
    }

    @Override
    public void execute() {
        if (ctx.bank.opened()) {
            final int inventCount = ctx.backpack.select().count();
            if (inventCount > 0) {
                if (ctx.bank.depositInventory()) {

                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.backpack.select().count() != inventCount;
                        }
                    }, 250, 20);
                }
            }
            if (inventCount == 0) {
                System.out.println(choice[0] + " " + choice[1] + " " + choice[2] + " " + choice[3] + " " + choice[4] + " " + choice[5] + " " + choice[6]);
                boolean emptyBank = (ctx.bank.select().id(choice[0]).count() < choice[2] || (choice[3] != 0 && ctx.bank.select().id(choice[3]).count() < choice[5]));
                if (emptyBank) {
                    System.out.println("empty Bank");
                    ctx.controller.stop();
                }
                ctx.bank.withdraw(choice[0], choice[1]);

                if (choice[3] != 0) {
                    ctx.bank.withdraw(choice[3], choice[4]);
                }
                ctx.bank.close();
            }
        } else {
            if (ctx.bank.inViewport() && ctx.players.local().speed() == 0) {
                if (ctx.bank.open()) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.bank.opened();
                        }
                    }, 250, 20);
                }
            } else {
                ctx.camera.turnTo(ctx.bank.nearest(), 25);
            }
        }
    }
}
