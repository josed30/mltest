package com.ml.test.mutant.detector.util;

import java.util.regex.Pattern;

public class MutantConstants {

    public static final String ADN_DATABASE_SEPARATOR = "-";
    public static final int MIN_N = 4;
    public static final int MIN_MUTANT_SEQUENCE = 2;
    public static final Pattern PATTERN = Pattern.compile("A{4}|T{4}|C{4}|G{4}");
    public static final double RATIO_SCALE = 10;
}
