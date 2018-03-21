package TheSmith;

import TheSmith.Tasks.Smelting;
import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.Widget;
import org.powerbot.script.rt6.ClientContext;

import javax.swing.*;

public class choices extends ClientAccessor {

    public choices(ClientContext ctx) {
        super(ctx);
    }

    public int[] getItemsFromBank() {
        return itemsFromBank;
    }

    public int getItemToMakeId() {
        return itemToMakeId;
    }

    public String getSmeltOrSmith() {
        return smeltOrSmith;
    }

    public String getWidgetText() {
        return widgetText;
    }

    public Component getWidgetComponent() {
        return widgetComponent;
    }

    private int[] itemsFromBank = {};
    private int itemToMakeId = 0;
    private String smeltOrSmith;


    private String widgetText;


    private Component widgetComponent;

    public String[] UserChoice() {
        String whatToDo[] = {"Smelt", "Smith"};
        smeltOrSmith = "" + (String) JOptionPane.showInputDialog(null, "Do you wanna Smelt or Smith", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatToDo, whatToDo[0]);

        if (smeltOrSmith == "Smelt") {
            //Add Smelt Options

        } else {
            String whatBarToUse[] = {"Bronze", "Iron", "Silver", "Steel", "Gold", "Mithril", "Adamant", "Rune"};
            String barToUse = "" + (String) JOptionPane.showInputDialog(null, "What bar would you like to use?", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatBarToUse, whatBarToUse[0]);
            String whatItemToMake[] = {"Platebody"};
            String itemToMake = "" + (String) JOptionPane.showInputDialog(null, "What bar would you like to use?", "The Smith", JOptionPane.PLAIN_MESSAGE, null, whatItemToMake, whatItemToMake[0]);
            switch (barToUse) {
                case "Bronze": {
                    switch (itemToMake) {
                        case "Platebody": {
                            itemsFromBank[0] = 2349;
                            itemToMakeId = 1117;
                            widgetComponent = ctx.widgets.widget(1371).component(44).component(145);
                            ctx.widgets.widget(1371).component(94).text();
                            widgetText = "Bronze platebody";
                        }
                    }
                }
            }
        }

        return null;
    }

}
