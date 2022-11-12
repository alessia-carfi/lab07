package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    private enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        Month(final int days) {
            this.days = days;
        }

        public static Month fromString(String month) {
            try {
                return valueOf(month);
            } catch (IllegalArgumentException e) {
                Month match = null;
                for (Month m : values()) {
                    if (m.toString().toLowerCase().startsWith(month.toLowerCase())) {
                        if (match != null) {
                            throw new IllegalArgumentException("Marching with more than one month");
                        }
                        match = m;
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("Match with nothing");
                }
                return match;
            }
        }
    }

    public static class SortByMonthOrder implements Comparator<String> {

        @Override
        public int compare(String arg0, String arg1) {
            Month month1 = Month.fromString(arg0);
            Month month2 = Month.fromString(arg1);
            return month1.compareTo(month2);
        }
        
    }

    public static class SortedByDate implements Comparator<String> {

        @Override
        public int compare(String arg0, String arg1) {
            Month month1 = Month.fromString(arg0);
            Month month2 = Month.fromString(arg1);
            
            return Integer.compare(month1.days, month2.days);
        }
    }

    final Comparator<String> by_days = new SortedByDate();
    final Comparator<String> by_order = new SortByMonthOrder(); 

    @Override
    public Comparator<String> sortByDays() {
        return by_days;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return by_order;
    }
}
