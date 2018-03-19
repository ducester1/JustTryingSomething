package rs3;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;
import rs3.Tasks.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name ="QuickMining", description ="Turorial", properties = "client=6; author=Scott; topic=999")

public class QuickMining extends PollingScript<ClientContext> implements PaintListener{

    List<Task> taskList = new ArrayList<Task>();
    int startExp = 0;

    private String userChoice;

    @Override
    public void start(){

        String userOptions[] = {"Mixed","Copper","Tin", "Powermine"};
        userChoice = ""+(String)JOptionPane.showInputDialog(null, "Choose mining option", "QuickMining", JOptionPane.PLAIN_MESSAGE, null, userOptions, userOptions[0]);

        if (userChoice.equals("Mixed")  || userChoice.equals("Copper") || userChoice.equals("Tin")){
            taskList.add(new Bank(ctx));
            taskList.add(new Walk(ctx, MConstants.LUMBRIDGE_SWAMP));
        } else if (userChoice.equals("Powermine")){
            taskList.add(new Drop(ctx));
        } else {
            ctx.controller.stop();
        }

        taskList.add(new Mine(ctx));

        startExp = ctx.skills.experience(Constants.SKILLS_MINING);
    }

    @Override
    public void poll() {

        for(Task task : taskList){
            //breaks if pressed stop
            if(ctx.controller.isStopping()){
                break;
            }

            if(task.activate()){
                task.execute();
                break;
            }
        }
    }

    public String getUserChoice() {
        return userChoice;
    }

    @Override
    public void repaint(Graphics graphics) {
        long millisecconds = this.getTotalRuntime();
        long secconds = (millisecconds / 1000) % 60;
        long minutes = (millisecconds / 60000) % 60;
        long hours = (millisecconds/ (1000*60*60)) % 60;

        int expGained = ctx.skills.experience(Constants.SKILLS_MINING) - startExp;

        Graphics2D g = (Graphics2D)graphics;

        g.setColor(new Color(0,0,0,180));
        g.fillRect(0,0,300,200);

        g.setColor(new Color(255,255,255));
        g.drawRect(0,  0,300,200);

        g.drawString("QuickMiner", 20 ,20);
        g.drawString("Running: " + String.format("%02d:%02d:%02d", hours,minutes,secconds),20,40);
        g.drawString("Exp gained: " + expGained,20,60);
        g.drawString("Exp/Hour" + (int)(expGained * (36000000 / millisecconds)),20,80);
    }
}
