package com.gmail.Xeiotos.HabitatAPI.Enumerations;

/**
 *
 * @author Xeiotos
 */

public enum HabitatType {

    INVALID("Invalid"), ALPHA("Alpha"), BETA("Beta"), GAMMA("Gamma"), DELTA("Delta"), EPSILON("Epsilon"), ZETA("Zeta"), ETA("Eta");
    
    private String habitatName;

    private HabitatType(String habitatName) {
        this.habitatName = habitatName;
    }

    public String getTypeName() {
        return habitatName;
    }
}