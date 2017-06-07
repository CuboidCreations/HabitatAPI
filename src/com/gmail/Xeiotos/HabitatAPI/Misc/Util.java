package com.gmail.Xeiotos.HabitatAPI.Misc;

/**
 *
 * @author Xeiotos
 */
public class Util {

    public static int round(int num) {
        if (num > 0) {
            num = num + 126;
        } else {
            num = num - 126;
        }
        return Math.round(num / 253);
    }
}
