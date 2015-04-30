/**
 * 
 */
package com.glueball.snoop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author karesz
 */
public final class ScheduleHelper {

    private static final int SEC_IN_MIN = 60;
    private static final int SEC_IN_HOUR = 3600;

    // private static final int SEC_IN_DAY = 86400;

    public static int getIntervalInSeconds(final String interval) {

        int hours = 0;
        int mins = 0;
        int secs = 0;

        if (StringUtils.isEmpty(interval)) {

            throw new IllegalArgumentException();
        }

        final Pattern pS = Pattern.compile("([0-1]*)s");
        final Matcher ms = pS.matcher(interval.toLowerCase());

        if (ms.find()) {

            secs += Integer.parseInt(ms.group(1));
        }

        final Pattern pM = Pattern.compile("([0-1]*)m");
        final Matcher mm = pM.matcher(interval.toLowerCase());

        if (mm.find()) {

            mins += Integer.parseInt(mm.group(1));
        }

        final Pattern pH = Pattern.compile("([0-1]*)h");
        final Matcher mh = pH.matcher(interval.toLowerCase());

        if (mh.find()) {

            hours += Integer.parseInt(mh.group(1));
        }

        return hours * SEC_IN_HOUR + mins * SEC_IN_MIN + secs;
    }
}
