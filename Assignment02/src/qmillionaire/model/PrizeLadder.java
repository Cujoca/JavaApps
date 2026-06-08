/*
 * PrizeLadder.java
 * CST8221 - JAP - Assignment 02
 * Author:      Andrei Cojocaru, 041032238
 * Professor:  Dr. James Mwangi
 */
package qmillionaire.model;

/**
 * Fifteen-step prize ladder used by the play mode panel on the right hand
 * side. The amounts mirror the classic Who Wants To Be A Millionaire payout
 * table.
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public final class PrizeLadder {

    /**
     * Number of rungs on the prize ladder.
     */
    public static final int LEVELS = 15;

    /**
     * Prize amounts ordered from level 1 ($100) to level 15 ($1,000,000).
     */
    public static final int[] AMOUNTS = {
            100, 200, 300, 500, 1000,
            2000, 4000, 8000, 16000, 32000,
            64000, 125000, 250000, 500000, 1000000
    };

    private PrizeLadder() {
        /* Utility class — instantiation suppressed. */
    }

    /**
     * Returns the dollar amount that corresponds to a given ladder level.
     *
     * @param level a 1-based level index between 1 and {@link #LEVELS}
     * @return the dollar amount associated with {@code level}
     */
    public static int amountFor(int level) {
        return AMOUNTS[level - 1];
    }
}
