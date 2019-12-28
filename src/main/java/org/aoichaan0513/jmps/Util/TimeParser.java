package org.aoichaan0513.jmps.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser {
    private static final Pattern TIME_PATTERN = Pattern.compile("([0-9]+)([ywdhms])");
    private static final int SECOND = 1;
    private static final int MINUTE = SECOND * 60;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY = HOUR * 24;
    private static final int WEEK = DAY * 7;
    private static final int YEAR = DAY * 365;

    public static int parseString(String string) {
        Matcher m = TIME_PATTERN.matcher(string);
        int total = 0;
        while (m.find()) {
            int amount = Integer.valueOf(m.group(1));
            switch (m.group(2).charAt(0)) {
                case 's':
                    total += amount * SECOND;
                    break;
                case 'm':
                    total += amount * MINUTE;
                    break;
                case 'h':
                    total += amount * HOUR;
                    break;
                case 'd':
                    total += amount * DAY;
                    break;
                case 'w':
                    total += amount * WEEK;
                    break;
                case 'y':
                    total += amount * YEAR;
                    break;
            }
        }
        return total;
    }
}
