package rutil.security;

import rutil.container.Array;

public class RDS {

    public static boolean compare(double rds, Array<String> texts) {

        return allToRds(texts) == rds;

    }

    public static double toRDS(String str) {

        byte[] bytes = str.getBytes();
        double chars = 0;

        for(int i = 0; i < bytes.length; i++) {
            chars += bytes[i];
        }

        return chars;

    }

    public static double allToRds(Array<String> texts) {

        double chars = 0;

        for(int k = 0; k < texts.size(); k++) {
            chars += toRDS(texts.get(k));
        }

        return chars / texts.size();

    }

}
