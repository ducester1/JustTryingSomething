package TheSmith.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Nillable;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

public class Bank extends TheSmith.Task {

    private static String[] choice;
    private static int[] itemsFromBank = {0, 0};
    private static int[] amountItemsFromBank = {0, 0};
    private static int[] amountNeeded = {0, 0};
    private static int itemId;

    public Bank(ClientContext ctx, String[] choice) {
        super(ctx);
        this.choice = choice;

    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 0 || ctx.backpack.select().id(itemsFromBank[0]).count() < amountNeeded[0] ||
                (itemsFromBank[1] != 0 && ctx.backpack.select().id(itemsFromBank[1]).count() < amountNeeded[1]);
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
                boolean emptyBank = (ctx.bank.select().id(itemsFromBank[0]).count() < amountNeeded[0] || (itemsFromBank[1] != 0 && ctx.bank.select().id(itemsFromBank[1]).count() < amountNeeded[1]));
                if (emptyBank) {
                    ctx.controller.stop();
                }
                ctx.bank.withdraw(itemsFromBank[0], amountItemsFromBank[0]);
                ctx.bank.withdraw(438, 14);
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

    public static void whatToDo() {
        //switch welke bar soort
        switch (choice[1]) {
            case "Bronze": {
                //switch welk item
                switch (choice[2]) {
                    case "Bar": {

                        break;
                    }
                    case "Platebody": {
                        itemsFromBank[0] = 2349;
                        amountItemsFromBank[0] = 28;
                        amountNeeded[0] = 5;

                        itemsFromBank[1] = 0;
                        amountItemsFromBank[1] = 0;
                        amountNeeded[1] = 0;

                        itemId = 1117;
                        break;
                    }
                }
            }
            case "Iron": {
                switch (choice[2]) {
                    case "Bar": {

                        break;
                    }
                    case "Platebody": {

                        break;
                    }
                }
            }
        }
    }
}
