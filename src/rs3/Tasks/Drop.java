package rs3.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;
import rs3.Task;

import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class Drop extends Task {

    public Drop(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28;
    }

    @Override
    public void execute() {



        for (Item copperOre : ctx.backpack.select().name(Pattern.compile("(.*ore) | Clay | Coal"))){

            if (ctx.controller.isStopping()) {
                break;
            }
            final int startAmtInventory = ctx.backpack.select().count();
            copperOre.interact("Drop");

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.select().count() != startAmtInventory;
                }
            }, 25, 20);
        }
    }
}
