package TheSmith;

import TheSmith.Tasks.Smelting;

import javax.swing.*;

public class choices {

    private int[] itemsFromBank = {};
    private int itemToMake = 0;
    private String smeltOrSmith;


    {
    }

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

                        }
                    }
                }
            }
        }

        return null;
    }

}
