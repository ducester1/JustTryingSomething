package TheSmith.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

public class Bank extends TheSmith.Task {

    public Bank(ClientContext ctx) {

        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(2349).count() == 14 || ctx.backpack.select().count() == 0 ||
                ctx.backpack.select().id(436).count() < 1 || ctx.backpack.select().id(438).count() < 1;

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
                ctx.bank.withdraw(436, 14);
                ctx.bank.withdraw(438, 14);
            }
        } else {
            if (ctx.bank.inViewport()) {
                if (ctx.bank.open()) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.bank.opened();
                        }
                    }, 250, 20);
                }
            } else {
                ctx.camera.turnTo(ctx.bank.nearest());
            }
        }
    }
}
