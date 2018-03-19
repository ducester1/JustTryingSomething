package rs3.Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import rs3.QuickMining;
import rs3.Task;

import java.util.concurrent.Callable;

public class Mine extends Task {

    final static int COPPER_ROCK_IDS[] = {3027, 3229};
    final static int TIN_ROCK_IDS[] = {3038,3245};

    Tile rockLocation = Tile.NIL;

    public Mine(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.objects.select().at(rockLocation).id(COPPER_ROCK_IDS).poll() .equals(ctx.objects.nil()) || ctx.players.local().animation() == -1;
    }

    @Override
    public void execute() {

        GameObject rockToMine = ctx.objects.select().id(COPPER_ROCK_IDS).nearest().poll();
        rockLocation = rockToMine.tile();
        rockToMine.click();

        Condition.wait(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() != -1;
            }
        }, 200, 10);
    }
}
