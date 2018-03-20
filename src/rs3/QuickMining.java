package rs3;

import org.powerbot.bot.rt6.WidgetCloser;
import org.powerbot.script.*;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;
import org.powerbot.script.rt6.Widget;
import org.powerbot.script.rt6.Widgets;
import rs3.Tasks.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Script.Manifest(name = "QuickMining", description = "Turorial", properties = "client=6; author=Scott; topic=999")

public class QuickMining extends PollingScript<ClientContext> implements PaintListener {

    List<Task> taskList = new ArrayList<Task>();
    int startExp = 0;
    int startMiningLvl = 1;
    int miningLvl = 1;

    @Override
    public void start() {
        startMiningLvl = ctx.skills.level(Constants.SKILLS_MINING);
        miningLvl = startMiningLvl;

        String userOptions[] = {"Bank", "Powermine"};
        String userChoice = "" + (String) JOptionPane.showInputDialog(null, "Bank or Powermining?", "QuickMining", JOptionPane.PLAIN_MESSAGE, null, userOptions, userOptions[0]);

        String oreOptions[] = {"Copper", "Iron", "Tin"};
        String oreChoice = "" + (String) JOptionPane.showInputDialog(null, "What do you want to mine?", "QuickMining", JOptionPane.PLAIN_MESSAGE, null, oreOptions, oreOptions[0]);

        if (userChoice.equals("Bank")) {
            taskList.add(new Bank(ctx));

            String locationOptions[] = {"Lumbridge Swamp", "Falador Dwarven"};
            String locatinoChoice = "" + (String) JOptionPane.showInputDialog(null, "Where do you want to mine?", "QuickMining", JOptionPane.PLAIN_MESSAGE, null, locationOptions, locationOptions[0]);

            if (locatinoChoice.equals("Lumbridge Swamp")) {
                taskList.add(new Walk(ctx, MConstants.LUMBRIDGE_SWAMP));
            } else if (locatinoChoice.equals("Falador Dwarven")) {
                List<Tile> finalPath = new ArrayList<Tile>();

                if (oreChoice.equals("Iron")) {
                    finalPath.addAll(Arrays.asList(MConstants.DWARVEN_IRON));
                } else {
                    ctx.controller.stop();
                }

                finalPath.addAll(Arrays.asList(MConstants.DWARVENMINE_STAIRS));

                taskList.add(new Walk(ctx, finalPath.toArray(new Tile[]{})));
            } else {
                ctx.controller.stop();
            }

        } else if (userChoice.equals("Powermine")) {
            taskList.add(new Drop(ctx));
        } else {
            ctx.controller.stop();
        }

        if (oreChoice.equals("Copper")) {
            taskList.add(new Mine(ctx, MConstants.COPPER_ROCK_IDS));
        } else if (oreChoice.equals("Tin")) {
            taskList.add(new Mine(ctx, MConstants.TIN_ROCK_IDS));
        } else if (oreChoice.equals("Iron")) {
            taskList.add(new Mine(ctx, MConstants.IRON_ROCK_IDS));
        }


        startExp = ctx.skills.experience(Constants.SKILLS_MINING);
    }

    @Override
    public void poll() {

        //click skill on lvlup
        if (ctx.skills.level(Constants.SKILLS_MINING) > miningLvl) {
            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return true;
                }
            }, 200, 10);

            ctx.widgets.widget(1466).component(2).component(2).click();
            miningLvl = ctx.skills.level(Constants.SKILLS_MINING);

            Condition.wait(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return true;
                }
            }, 200, 10);
        }
        //closes the mining window
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

        int expGained = ctx.skills.experience(Constants.SKILLS_MINING) - startExp;

        Graphics2D g = (Graphics2D) graphics;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 300, 200);

        g.setColor(new Color(255, 255, 255));
        g.drawRect(0, 0, 300, 200);

        g.drawString("QuickMiner", 20, 20);
        g.drawString("Running: " + String.format("%02d:%02d:%02d", hours, minutes, secconds), 20, 40);
        g.drawString("Exp gained: " + expGained, 20, 60);
        g.drawString("Exp/Hour" + (int) (expGained * (36000000 / millisecconds)), 20, 80);
    }
}
