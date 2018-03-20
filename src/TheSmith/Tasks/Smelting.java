package TheSmith.Tasks;

import TheSmith.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import java.util.concurrent.Callable;

public class Smelting extends Task {

    public Smelting(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(436).count() == 14 && ctx.backpack.select().id(438).count() == 14;
    }

    @Override

    public void execute() {

        GameObject furnace = ctx.objects.select().id(45310).poll();
        if (ctx.widgets.widget(1371).valid()) {
            ctx.input.send(" ");
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().animation() != -1;
                }
            }, 200, 10);
        }
        if (furnace.inViewport()) {
            if (ctx.players.local().animation() == -1) {
                furnace.click();
            }

        } else {
            ctx.camera.turnTo(furnace);
        }
    }
}
