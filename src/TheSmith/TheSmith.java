package TheSmith;

import TheSmith.Tasks.Bank;
import TheSmith.Tasks.Smithing;
import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Script.Manifest(name = "The Smith", description = "Smiths your stuff", properties = "client=6; author=Scott; topic=999")

public class TheSmith extends PollingScript<ClientContext> implements PaintListener {

    List<Task> taskList = new ArrayList<Task>();

    private int startExp = 0;
    private int startSmithingLvl = 14;
    private int smithingLvl = 1;

    private int[] choice = {0, 0, 0, 0, 0, 0, 0};

    @Override
    public void start() {
        startSmithingLvl = ctx.skills.level(Constants.SKILLS_SMITHING);
        smithingLvl = startSmithingLvl;
        startExp = ctx.skills.experience(Constants.SKILLS_SMITHING);

        choice = Choices.UserChoice();

        taskList.add(new Bank(ctx, choice));
        taskList.add(new Smithing(ctx, choice));

    }

    @Override
    public void poll() {
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return choice[0] != 0;
            }
        }, 250, 20);
        //click skill on lvlup
        if (ctx.skills.level(Constants.SKILLS_SMITHING) > smithingLvl) {
            ctx.widgets.widget(1466).component(2).component(5).click();
            smithingLvl = ctx.skills.level(Constants.SKILLS_SMITHING);

            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return true;
                }
            }, 200, 10);
        }

        //closes the smithing window
        if (ctx.widgets.widget(1477).component(659).component(1).visible()) {
            ctx.widgets.widget(1477).component(659).component(1).click();
        }

        for (Task task : taskList) {
            //breaks if pressed stop
            if (ctx.controller.isStopping()) {
                break;
            }

            if (task.activate()) {
                task.execute();
                break;
            }
        }
    }

    @Override
    public void repaint(Graphics graphics) {
        long millisecconds = this.getTotalRuntime();
        long secconds = (millisecconds / 1000) % 60;
        long minutes = (millisecconds / 60000) % 60;
        long hours = (millisecconds / (1000 * 60 * 60)) % 60;

        int expGained = ctx.skills.experience(Constants.SKILLS_SMITHING) - startExp;

        Graphics2D g = (Graphics2D) graphics;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 300, 200);

        g.setColor(new Color(255, 255, 255));
        g.drawRect(0, 0, 300, 200);

        g.drawString("The Smith", 20, 20);
        g.drawString("Running: " + String.format("%02d:%02d:%02d", hours, minutes, secconds), 20, 40);
        g.drawString("Exp gained: " + expGained, 20, 60);
        g.drawString("Exp/Hour" + (int) (expGained * (36000000 / millisecconds)), 20, 80);
    }
}
