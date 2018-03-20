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
        return ctx.backpack.select().id(436).count() > 1 && ctx.backpack.select().id(438).count() > 1;
    }

    @Override

    public void execute() {

        GameObject furnace = ctx.objects.select().id(45310).poll();
        if (ctx.widgets.widget(1371).valid()) {
            ctx.input.send(" ");
        }
        if (furnace.inViewport()) {
            if (ctx.players.local().speed() == 0 && !ctx.widgets.widget(1251).valid()) {
                furnace.click();
            }

        } else {
            ctx.camera.turnTo(furnace, 25);
        }
    }
}
