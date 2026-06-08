/*
 * Theme.java
 * CST8221 - JAP - Assignment 02
 * Author:      Andrei Cojocaru, 041032238
 * Professor:  Dr. James Mwangi
 */
package qmillionaire.model;

import javafx.scene.paint.Color;

/**
 * Color palette used across the screens of the application. A theme is a
 * fixed bundle of colors that {@link qmillionaire.controller.AppContext} swaps in response to the
 * "Configuration / Color Theme" menu.
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public enum Theme {

    /** Default Quantum Millionaire palette (navy + purple). */
    CLASSIC(
            Color.web("#0b1d3a"),
            Color.web("#1a2a55"),
            Color.web("#5a1a8a"),
            Color.web("#ffd700"),
            Color.web("#ffffff"),
            Color.web("#ff4757"),
            Color.web("#00d26a")),

    /** Dark high-saturation palette (deep blacks + cyan accent). */
    DARK(
            Color.web("#0a0a0a"),
            Color.web("#1a1a1a"),
            Color.web("#1f3a5f"),
            Color.web("#00e5ff"),
            Color.web("#e0e0e0"),
            Color.web("#ff6b6b"),
            Color.web("#1abc9c")),

    /** Black on yellow palette for high contrast accessibility. */
    HIGH_CONTRAST(
            Color.web("#000000"),
            Color.web("#101010"),
            Color.web("#1a1a1a"),
            Color.web("#ffeb3b"),
            Color.web("#ffffff"),
            Color.web("#ff1744"),
            Color.web("#00e676"));

    private final Color background;
    private final Color panel;
    private final Color accent;
    private final Color highlight;
    private final Color textPrimary;
    private final Color textWarning;
    private final Color textSuccess;

    /**
     * Constructs a theme out of its seven semantic colors.
     *
     * @param background  outer window background
     * @param panel       inner panel background
     * @param accent      button/accent background
     * @param highlight   gold/highlight color (titles, current prize tier)
     * @param textPrimary primary text color
     * @param textWarning warning/timer text color
     * @param textSuccess success/current-match text color
     */
    Theme(Color background, Color panel, Color accent, Color highlight,
          Color textPrimary, Color textWarning, Color textSuccess) {
        this.background = background;
        this.panel = panel;
        this.accent = accent;
        this.highlight = highlight;
        this.textPrimary = textPrimary;
        this.textWarning = textWarning;
        this.textSuccess = textSuccess;
    }

    /**
     * Outer window background color.
     * @return the background color
     */
    public Color background() { return background; }

    /**
     * Inner panel background color.
     * @return the panel color
     */
    public Color panel() { return panel; }

    /**
     * Button / accent background color.
     * @return the accent color
     */
    public Color accent() { return accent; }

    /**
     * Highlight color used for titles and the current prize tier.
     * @return the highlight color
     */
    public Color highlight() { return highlight; }

    /**
     * Primary text color used by labels and answers.
     * @return the primary text color
     */
    public Color textPrimary() { return textPrimary; }

    /**
     * Warning text color used for the countdown timer.
     * @return the warning text color
     */
    public Color textWarning() { return textWarning; }

    /**
     * Success text color used for the current match amount.
     * @return the success text color
     */
    public Color textSuccess() { return textSuccess; }

    /**
     * Renders a JavaFX color as a CSS-compatible {@code #RRGGBB} string.
     *
     * @param c the color to encode
     * @return the corresponding hexadecimal CSS literal
     */
    public static String toCss(Color c) {
        int r = (int) Math.round(c.getRed() * 255);
        int g = (int) Math.round(c.getGreen() * 255);
        int b = (int) Math.round(c.getBlue() * 255);
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
