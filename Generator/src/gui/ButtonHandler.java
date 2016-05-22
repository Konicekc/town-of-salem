package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * ButtonHandler - The main button handling class of the GUI
 */

public class ButtonHandler implements ActionListener {

    public static RoleStack roleList;
    public static RoleSelect categorySorter;

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add":
                Random randomizer = new Random();
                int res = randomizer.nextInt(47);
                if (res > 33) { // RANDOM
                    roleList.pushRole("neutral killing");
                } else if (res > 24) { // MAFIA
                    roleList.pushRole("executioner");
                } else if (res > 15) { // NEUTRAL
                    roleList.pushRole("vampire");
                } else { // TOWN
                    roleList.pushRole("town investigative");
                }
                break;
            case "Remove":
                roleList.popRole();
                break;
            case "Town":
                categorySorter.showNewPanel(command);
                break;
            case "Mafia":
                categorySorter.showNewPanel(command);
                break;
            case "Neutral":
                categorySorter.showNewPanel(command);
                break;
            case "Random":
                categorySorter.showNewPanel(command);
                break;
            default:
                break;
        }
    }

}
