package roles;

import actions.RoleInfo;

public class Witch extends RoleControl {

    public Witch(String name, int num) {
        super(name, num);
    }

    public void Process() {

        if (!jailed) {
            target = validTownTarget(playerNum); // Source target
            target2 = validTownTarget(playerNum); // Destination target
            nightAction = "You have decided to control " + String.valueOf(target) + "(" +
                    players.get(target).roleName + ") to " + String.valueOf(target2) + "(" +
                    players.get(target2).roleName + ").";
            target = checkTargetSwitch(target);
            target2 = checkTargetSwitch(target2);
            if (players.get(target).jailed) {
                players.get(target).activity.add("WitchJail");
            } else if (RoleInfo.ControlImmune.contains(players.get(target).roleName)) {
                players.get(target).activity.add("WitchImmune");
                checkVetVisit(target); // Only check vet visits here because he is immune
            } else {
                players.get(target).activity.add("Witched");
                if (players.get(target).witched != 0) {
                    nightResult = "Another witch has controlled your target first!";
                } else {
                    players.get(target).witched = target2;
                }
            }
        }

    }

}
