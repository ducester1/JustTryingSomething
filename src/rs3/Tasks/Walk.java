package rs3.Tasks;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import rs3.Task;
import rs3.Walker;

public class Walk extends Task {

    public  Tile pathToBank[];

    private final Walker walker = new Walker(ctx);

    public Walk(ClientContext ctx, Tile[] pathToBank) {
        super(ctx);

        this.pathToBank = pathToBank;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() > 27 || (ctx.backpack.select().count() < 28 && pathToBank[0].distanceTo(ctx.players.local()) > 6);
    }

    @Override
    public void execute() {
        if (ctx.movement.running(false) && ctx.movement.energyLevel() > Random.nextInt(35,55)){
            ctx.movement.running(true);
        }

        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.distance(ctx.players.local()) < 5) {
            if (ctx.backpack.select().count() > 27) {
                System.out.println("to bank");
                System.out.println(pathToBank);
                walker.walkPath(pathToBank);
                System.out.println(pathToBank);
            }
            else {
                System.out.println("to ore");
                walker.walkPathReverse(pathToBank);
            }
        }
    }
}