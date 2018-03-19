package scripts;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Npc;

import java.util.concurrent.Callable;

@Script.Manifest(name = "Cow Killer", description = "Kills Cows", properties = "author=Scott; topic=999; client=6;")
public class CowKiller extends PollingScript<ClientContext> {

    final static int COW_IDS[] = {12362, 12363};
    final static int FOOD_ID = 315;

    @Override
    public void start() {
        System.out.println("Started.");
    }

    @Override
    public void stop() {
        System.out.println("Stopped.");
    }

    @Override
    public void poll() {
        //constant loop
        if (!needsHealing()) {
        }

        if (shouldAttack()) {
            System.out.println("attack");
            Attack();
        }
    }

    public boolean needsHealing() {
        return ctx.combatBar.health() < 500;
    }

    public boolean shouldAttack() {
        return !ctx.players.local().inCombat();
    }

    public boolean hasFood() {
        return ctx.backpack.select().id(FOOD_ID).count() > 0;
    }

    public void Attack() {
        final Npc goblinToAttack = ctx.npcs.select().id(COW_IDS).select(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return !npc.inCombat();
            }
        }).nearest().poll();

        goblinToAttack.interact("Attack");

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().inCombat();
            }
        }, 200, 20);
    }

    public void heal() {
        Item foodToEat = ctx.backpack.select().id(FOOD_ID).poll();

        foodToEat.interact("eat");

        final int startingHealt = ctx.combatBar.health();

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final int currentHealth = ctx.combatBar.health();
                return currentHealth != startingHealt;
            }
        }, 150, 20);
    }
}
