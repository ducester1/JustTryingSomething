package TheSmith;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.ClientContext;

import javax.swing.*;

public class Choices extends ClientAccessor {

    private static ClientContext ctx;

    public Choices(ClientContext ctx) {
        super(ctx);
        this.ctx = ctx;
    }

    public static int[] itemsFromBank = {0, 0};
    public static int itemId = 0;
    public static String smeltOrSmith;
    public static String widgetText;
    public static Component widgetComponent;
    private static String bar;
    private static String item;

    public static String[] UserChoice() {
        String whatToDo[] = {"Smelt", "Smith"};
        smeltOrSmith = "" + (String) JOptionPane.showInputDialog(null, "Do you wanna Smelt or Smith", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatToDo, whatToDo[0]);

        if (smeltOrSmith == "Smelt") {
            //Add Smelt Options

        } else {
            String whatBarToUse[] = {"Bronze", "Iron", "Silver", "Steel", "Gold", "Mithril", "Adamant", "Rune"};
            bar = "" + (String) JOptionPane.showInputDialog(null, "What bar would you like to use?", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatBarToUse, whatBarToUse[0]);
            String whatItemToMake[] = {"Platebody"};
            item = "" + (String) JOptionPane.showInputDialog(null, "What bar would you like to use?", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatItemToMake, whatItemToMake[0]);
            switch (bar) {
                case "Bronze": {
                    switch (item) {
                        case "Platebody": {
                            itemsFromBank[0] = 2349;
                            itemId = 1117;
                            //widgetComponent = ctx.widgets.widget(1371).component(44).component(145);
                            //ctx.widgets.widget(1371).component(94).text();
                            widgetText = "Bronze platebody";
                        }
                    }
                }
            }
        }
        return new String[]{smeltOrSmith, bar, item};
    }
}

