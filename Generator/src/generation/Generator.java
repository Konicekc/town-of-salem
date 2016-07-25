package generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generator {

    RoleStorage storage;
    ArrayList<String> roleList;
    ArrayList<String> FinalList;
    boolean VampPresent;
    int AnySize;
    int TownSize;
    int MafiaSize;
    int NeutralSize;
    Random randomizer;

    public Generator(RoleStorage info, ArrayList<String> roles) {

        storage = info;
        roleList = roles;
        FinalList = new ArrayList<>();
        Collections.sort(roleList);
        randomizer = new Random();
        VampPresent = false;
        TownSize = storage.TI.size() + storage.TP.size() + storage.TK.size() + storage.TS.size();
        NeutralSize = storage.NB.size() + storage.NE.size() + storage.NK.size();
        MafiaSize = storage.MK.size() + storage.MS.size() + storage.MD.size();
        AnySize = TownSize + NeutralSize + MafiaSize;

    }

    /**
     * Processes and returns the final role list
     *
     * @return The processed role list
     */
    public ArrayList<String> Generate() {

        ArrayList<String> GeneralRoles = new ArrayList<>();
        Modifier.VampireChecker(this);
        Modifier.MafiaChecker(this);
        for (String role : roleList) {
            if (storage.director.containsKey(role) || role.equals("Random Town")
                    || role.equals("Random Mafia") || role.equals("Random Neutral")
                    || role.equals("Any")) {
                GeneralRoles.add(role);
            } else {
                getRole(role);
            }
        }
        for (String genRole : GeneralRoles) {
            switch (genRole) {
                case "Random Town":
                    getRandomTown();
                    break;
                case "Random Mafia":
                    //FinalList.add(getRandomMafia());
                    break;
                case "Random Neutral":
                    getRandomNeutral();
                    break;
                case "Any":
                    //FinalList.add(getAny());
                    break;
                default:
                    getRoleCategory(storage.director.get(genRole));
                    break;
            }
        }
        Collections.shuffle(FinalList);
        return FinalList;

    }

    private void getRandomTown() {
        int totalSize = TownSize - storage.TP.size();
        int chosen = randomizer.nextInt(totalSize);
        if (totalSize < chosen) {
            getRoleCategory(storage.TP);
            return;
        }
        totalSize -= storage.TS.size();
        if (totalSize < chosen) {
            getRoleCategory(storage.TS);
            return;
        }
        totalSize -= storage.TI.size();
        if (totalSize < chosen) {
            getRoleCategory(storage.TI);
            return;
        }
        getRoleCategory(storage.TK);
    }

    private void getRandomNeutral() {
        int totalSize = TownSize - storage.NK.size();
        int chosen = randomizer.nextInt(totalSize);
        if (totalSize < chosen) {
            getRoleCategory(storage.NK);
            return;
        }
        totalSize -= storage.NB.size();
        if (totalSize < chosen) {
            getRoleCategory(storage.NB);
            return;
        }
        getRoleCategory(storage.NE);
    }

    private void getRole(String role) {
        if (!role.equals("Vampire") && storage.Unique.contains(role)) {
            ArrayList<String> category = storage.findCategory(role);
            category.remove(role);
        }
        FinalList.add(role);
    }

    private void getRoleCategory(ArrayList<String> catRoles) {
        String result = catRoles.get(randomizer.nextInt(catRoles.size()));
        if (storage.Unique.contains(result)) {
            catRoles.remove(result);
        }
        FinalList.add(result);
    }

}