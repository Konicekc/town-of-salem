package generation;

import validator.MainValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generator {

    RoleStorage storage;
    MainValidator validator;
    ArrayList<String> roleList;
    ArrayList<String> FinalList;
    ArrayList<String> GeneralRoles;
    boolean VampPresent;
    boolean MafPresent;
    Random randomizer;

    public Generator(RoleStorage info, ArrayList<String> roles, MainValidator val) {

        validator = val;
        storage = info;
        roleList = roles;
        FinalList = new ArrayList<>();
        GeneralRoles = new ArrayList<>();
        Collections.sort(roleList);
        randomizer = new Random();
        VampPresent = false;
        MafPresent = false;

    }

    /**
     * Processes and returns the final role list
     *
     * @return The processed role list
     */
    public ArrayList<String> Generate() {

        // ADD THE UNIQUE STUFF HERE FIRST
        FactionModifier.modify(this);
        MafiaModifier.modify(roleList, validator.getMafiaCase(), storage);
        ArrayList<String> toRemove = new ArrayList<>();
        for (String role: roleList) {
            if (storage.findFaction(role).equals("Mafia")) {
                MafPresent = true;
            }
            if (storage.isUnique(role)) {
                toRemove.add(role);
                getRole(role);
            }
        }
        for (String role: toRemove) {
            roleList.remove(role);
        }
        VampireModifier.modify(this);
        //Modifier.MafiaChecker(this);
        /*
        for (String role : roleList) {
            if (storage.director.containsKey(role) || role.equals("Random Town")
                    || role.equals("Random Mafia") || role.equals("Random Neutral")
                    || role.equals("Any")) {
                GeneralRoles.add(role);
            } else {
                getRole(role);
            }
        }
        Modifier.VampireChecker(this);
        for (String genRole : GeneralRoles) {
            switch (genRole) {
                case "Random Town":
                    getRandomTown();
                    break;
                case "Random Mafia":
                    getRandomMafia();
                    break;
                case "Random Neutral":
                    getRandomNeutral();
                    break;
                case "Any":
                    getAnyRole();
                    break;
                default:
                    getRoleCategory(storage.director.get(genRole));
                    break;
            }
        }
        Collections.shuffle(FinalList);
        */
        return roleList;

    }

    private void getRandomTown() {
        int totalSize = storage.getTownSize();
        int chosen = randomizer.nextInt(totalSize);
        totalSize -= storage.TP.size();
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

    private void getAnyRole() {
        int totalSize = storage.getAllSize();
        int chosen = randomizer.nextInt(totalSize);
        if (VampPresent) {
            totalSize++;
        }
        totalSize -= storage.getMafiaSize();
        if (totalSize < chosen) {
            getRandomMafia();
            return;
        }
        totalSize -= storage.getTownSize();
        if (totalSize < chosen) {
            getRandomTown();
            return;
        }
        getRandomNeutral();
    }

    private void getRandomMafia() {
        int totalSize = storage.getMafiaSize();
        int chosen = randomizer.nextInt(totalSize);
        totalSize -= storage.MK.size();
        if (totalSize < chosen) {
            getRoleCategory(storage.MK);
            return;
        }
        totalSize -= storage.MD.size();
        if (totalSize < chosen) {
            getRoleCategory(storage.MD);
            return;
        }
        getRoleCategory(storage.MS);
    }

    private void getRandomNeutral() {
        int totalSize = storage.getNeutralSize();
        if (VampPresent && randomizer.nextInt(totalSize + 1) == totalSize) {
            getRole("Vampire");
            return;
        }
        int chosen = randomizer.nextInt(totalSize);
        totalSize -= storage.NK.size();
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
