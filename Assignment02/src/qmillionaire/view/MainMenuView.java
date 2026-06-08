/*
 * MainMenuView.java
 * CST8221 - JAP - Assignment 02
 * Author:      Andrei Cojocaru, 041032238
 * Professor:  Dr. James Mwangi
 */
package qmillionaire.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import qmillionaire.controller.AppContext;
import qmillionaire.model.Theme;

/**
 * The main menu shown at startup. It hosts the QMillionaire banner together
 * with two large {@code DESIGN} and {@code PLAY} buttons that switch the
 * application to the corresponding mode. A {@link javafx.scene.layout.BorderPane}
 * organizes the menu bar (top), the banner (center) and the buttons
 * (bottom).
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public final class MainMenuView {

    private final AppContext context;

    /**
     * Creates the view bound to the application context.
     *
     * @param context the application context
     */
    public MainMenuView(AppContext context) {
        this.context = context;
    }

    /**
     * Assembles the main menu scene.
     *
     * @return the JavaFX {@link Scene} ready to be installed on the stage
     */
    public Scene build() {
        ResourceBundle bundle = context.getBundle();
        Theme theme = context.getTheme();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + Theme.toCss(theme.background()) + ";");

        root.setTop(new MenuBarBuilder(context).build());

        ImageView banner = new ImageView(ImageFactory.createBanner());
        banner.setPreserveRatio(true);
        banner.setFitWidth(ImageFactory.BANNER_WIDTH);

        VBox center = new VBox(banner);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(20));
        root.setCenter(center);

        Button design = new Button(bundle.getString("main.design"));
        design.setPrefSize(160, 48);
        design.setStyle(buttonStyle(Theme.toCss(theme.accent()), Theme.toCss(theme.highlight())));
        design.setOnAction(new ShowDesignHandler());

        Button play = new Button(bundle.getString("main.play"));
        play.setPrefSize(160, 48);
        play.setStyle(buttonStyle("#a020a0", "#ffd700"));
        play.setOnAction(new ShowPlayHandler());

        HBox buttons = new HBox(24, design, play);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(0, 0, 32, 0));
        root.setBottom(buttons);

        return new Scene(root);
    }

    /**
     * Builds an inline CSS string for the launcher buttons.
     *
     * @param bg   button background color in CSS form
     * @param text button text color in CSS form
     * @return the CSS that styles the launcher buttons
     */
    private String buttonStyle(String bg, String text) {
        return "-fx-background-color: " + bg + ";"
                + "-fx-text-fill: " + text + ";"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;"
                + "-fx-border-color: " + text + ";"
                + "-fx-border-width: 2;"
                + "-fx-padding: 6 18 6 18;";
    }

    /**
     * Navigates to the Design screen when the DESIGN button is pressed.
     */
    private final class ShowDesignHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            context.showDesign();
        }
    }

    /**
     * Navigates to the Play screen when the PLAY button is pressed.
     */
    private final class ShowPlayHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            context.showPlay();
        }
    }
}
