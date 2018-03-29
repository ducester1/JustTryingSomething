package TheSmith.Tasks;

import TheSmith.Task;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class Smithing extends Task {

    private static int[] choice;

    public Smithing(ClientContext ctx, int[] choice) {
        super(ctx);
        this.choice = choice;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(choice[0]).count() > choice[2] && choice[3] == 0 || (choice[3] != 0 && ctx.backpack.select().id(choice[3]).count() > choice[5]);
    }

    @Override

    public void execute() {
        System.out.println("Smithing");

        GameObject workStation;
        if (choice[7] == 0) {
            workStation = ctx.objects.select().id(45310).poll();
        } else {
            workStation = ctx.objects.select().id(12692).poll();
        }
        if (ctx.widgets.widget(1371).valid()) {
            ctx.input.send(" ");
        }
        if (workStation.inViewport()) {
            if (ctx.players.local().speed() == 0 && !ctx.widgets.widget(1251).valid()) {
                workStation.click();
            }

        } else {
            ctx.camera.turnTo(workStation, 25);
        }
    }
}
