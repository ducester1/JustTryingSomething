package rs3.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;
import rs3.Task;

import java.util.concurrent.Callable;

public class Drop extends Task {

    final static int COPPER_ORE = 436;

    public Drop(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28;
    }

    @Override
    public void execute() {

            for(Item copperOre : ctx.backpack.select().id(COPPER_ORE)){

                if(ctx.controller.isStopping()){
                    break;
                }
                final int startAmtCopper = ctx.backpack.select().id(COPPER_ORE).count();
                copperOre.interact("Drop", "Copper");

                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.backpack.select().id(COPPER_ORE).count() != startAmtCopper;
                    }
                },25,20);
            }
    }
}
