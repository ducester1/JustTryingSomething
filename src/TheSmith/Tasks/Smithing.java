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

    /*
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

                            break;
                        }
                        default:
                            break;
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
                        default:
                            break;
                    }
                }
                default:
                    break;
            }
        }
    */
    private boolean WidgetOpen() {
        return false;
    }
}
