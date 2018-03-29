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

    public static String smeltOrSmith;
    public static String widgetText;
    private static String bar;
    public static String item;
    private static int[] value;
    public static Component widgetComponent;

    public static int[] UserChoice() {
        String whatToDo[] = {"Smelt", "Smith"};
        smeltOrSmith = "" + (String) JOptionPane.showInputDialog(null, "Do you wanna Smelt or Smith", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatToDo, whatToDo[0]);

        if (smeltOrSmith == "Smelt") {
            //Add Smelt Options

        } else {
            String whatBarToUse[] = {"Bronze", "Iron", "Silver", "Steel", "Gold", "Mithril", "Adamant", "Rune"};
            bar = "" + (String) JOptionPane.showInputDialog(null, "What bar would you like to use?", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatBarToUse, whatBarToUse[0]);
            String whatItemToMake[] = {"Bar", "Platebody"};
            item = "" + (String) JOptionPane.showInputDialog(null, "What bar would you like to use?", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatItemToMake, whatItemToMake[0]);
            switch (bar) {
                case "Bronze": {
                    switch (item) {
                        case "Bar": {
                            value = MConstants.BRONZE_BARS;
                            widgetText = "Bronze bar";
                            break;
                        }
                        case "Platebody": {
                            value = MConstants.BRONZE_PLATEBODY;
                            widgetText = "Bronze platebody";
                        }
                    }
                }
            }
        }
        return value;
    }
}

