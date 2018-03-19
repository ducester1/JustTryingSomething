package rs3.Tasks;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import rs3.Task;
import rs3.Walker;

public class Walk extends Task {

    public static final Tile pathToBank[] = {new Tile(3228,3148,0), new Tile(3235,3155,0), new Tile(3238,3163,0), new Tile(3238,3172,0), new Tile(3241,3181,0), new Tile(3243,3189,0),
            new Tile(3244,3197,0), new Tile(3238,3201,0), new Tile(3236,3207,0), new Tile(3236,3214,0), new Tile(3228,3218,0), new Tile(3219,3218,0), new Tile(3214,3214,0),
            new Tile(3209,3210,0), new Tile(3205,3209,0), new Tile(3205,3209,1), new Tile(3205,3209,2), new Tile(3206,3215,2), new Tile(3208,3220,2)};

    private final Walker walker = new Walker(ctx);

    public Walk(ClientContext arg0) {
        super(arg0);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() >27 || (ctx.backpack.select().count() <28 && pathToBank[0].distanceTo(ctx.players.local()) > 6);
    }

    @Override
    public void execute() {

    }
}
