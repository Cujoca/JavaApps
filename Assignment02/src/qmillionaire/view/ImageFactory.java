/*
 * ImageFactory.java
 * CST8221 - JAP - Assignment 02
 * Author:      Andrei Cojocaru, 041032238
 * Professor:  Dr. James Mwangi
 */
package qmillionaire.view;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Builds programmatic placeholder images for the application. Until real
 * artwork is dropped into the {@code images/} folder, the title icon and the
 * main banner are painted on a {@link Canvas} and converted into an
 * {@link Image} on demand.
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public final class ImageFactory {

    /** Width of the main menu banner in pixels. */
    public static final double BANNER_WIDTH = 520;

    /** Height of the main menu banner in pixels. */
    public static final double BANNER_HEIGHT = 320;

    private ImageFactory() {
        /* Utility class — instantiation suppressed. */
    }

    /**
     * Paints the main menu banner: a purple-to-navy radial background with
     * the MILLIONAIRE wordmark, a Quantum subtitle and the author name.
     *
     * @return the rendered banner as a JavaFX {@link Image}
     */
    public static Image createBanner() {
        Canvas canvas = new Canvas(BANNER_WIDTH, BANNER_HEIGHT);
        GraphicsContext g = canvas.getGraphicsContext2D();

        RadialGradient bg = new RadialGradient(
                0, 0,
                BANNER_WIDTH / 2, BANNER_HEIGHT / 2,
                Math.max(BANNER_WIDTH, BANNER_HEIGHT) / 2,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#3a1066")),
                new Stop(0.6, Color.web("#0e1a4a")),
                new Stop(1, Color.web("#04081f")));
        g.setFill(bg);
        g.fillRect(0, 0, BANNER_WIDTH, BANNER_HEIGHT);

        drawConcentricRing(g, BANNER_WIDTH / 2, BANNER_HEIGHT / 2 - 30, 95, Color.web("#ffd700", 0.85));
        drawConcentricRing(g, BANNER_WIDTH / 2, BANNER_HEIGHT / 2 - 30, 70, Color.web("#ffe14a", 0.55));

        g.setEffect(new DropShadow(8, Color.web("#000000", 0.7)));
        g.setFill(Color.web("#ffd700"));
        g.setFont(Font.font("Arial", FontWeight.BOLD, 44));
        g.setTextAlign(TextAlignment.CENTER);
        g.fillText("MILLIONAIRE", BANNER_WIDTH / 2, BANNER_HEIGHT / 2 - 20);

        g.setFill(Color.web("#9bd6ff"));
        g.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        g.fillText("Quantum", BANNER_WIDTH / 2, BANNER_HEIGHT / 2 + 20);

        g.setEffect(null);
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        g.fillText("Andrei Cojocaru", BANNER_WIDTH / 2, BANNER_HEIGHT - 30);

        g.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        g.setFill(Color.web("#cccccc"));
        g.fillText("CST8221 - JAP - Spring 2026", BANNER_WIDTH / 2, BANNER_HEIGHT - 12);

        WritableImage out = new WritableImage((int) BANNER_WIDTH, (int) BANNER_HEIGHT);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        canvas.snapshot(params, out);
        return out;
    }

    /**
     * Builds the small icon used for the window title bar.
     *
     * @return a 64x64 JavaFX {@link Image} suitable for {@code Stage.getIcons()}
     */
    public static Image createTitleIcon() {
        double size = 64;
        Canvas canvas = new Canvas(size, size);
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.web("#1a2a55"));
        g.fillRoundRect(0, 0, size, size, 12, 12);
        g.setStroke(Color.web("#ffd700"));
        g.setLineWidth(2);
        g.strokeRoundRect(2, 2, size - 4, size - 4, 10, 10);
        g.setFill(Color.web("#ffd700"));
        g.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        g.setTextAlign(TextAlignment.CENTER);
        g.fillText("Q", size / 2, size / 2 + 10);
        WritableImage out = new WritableImage((int) size, (int) size);
        SnapshotParameters p = new SnapshotParameters();
        p.setFill(Color.TRANSPARENT);
        canvas.snapshot(p, out);
        return out;
    }

    /**
     * Helper that paints a soft glow ring used as background ornament on
     * the main banner.
     *
     * @param g      the graphics context to paint on
     * @param cx     x coordinate of the ring center
     * @param cy     y coordinate of the ring center
     * @param radius outer radius of the ring
     * @param color  ring stroke color
     */
    private static void drawConcentricRing(GraphicsContext g, double cx, double cy,
                                           double radius, Color color) {
        g.setStroke(color);
        g.setLineWidth(2);
        g.strokeOval(cx - radius, cy - radius, radius * 2, radius * 2);
    }
}
