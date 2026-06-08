/*
 * QuantumMillionaire.java
 * CST8221 - JAP - Assignment 02
 * Author:      Andrei Cojocaru, 041032238
 * Professor:  Dr. James Mwangi
 */
package qmillionaire;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import qmillionaire.controller.AppContext;
import qmillionaire.view.ImageFactory;

/**
 * Entry point of the Quantum Millionaire application.
 * <p>
 * The class extends {@link javafx.application.Application} and starts the
 * primary stage by delegating the construction of every screen to the
 * {@link qmillionaire.controller.AppContext} controller. The initial screen
 * displayed is the main menu, from which the user can switch to the Design
 * or Play modes.
 * </p>
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public class QuantumMillionaire extends Application {

    /**
     * Public no-argument constructor required by {@link Application#launch}.
     */
    public QuantumMillionaire() {
        super();
    }

    /**
     * Width of the primary stage at launch.
     */
    public static final double DEFAULT_WIDTH = 900;

    /**
     * Height of the primary stage at launch.
     */
    public static final double DEFAULT_HEIGHT = 650;

    /**
     * Starts the JavaFX runtime and displays the main menu.
     *
     * @param stage the primary stage created by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        AppContext context = new AppContext(stage);
        stage.setMinWidth(700);
        stage.setMinHeight(550);
        stage.setWidth(DEFAULT_WIDTH);
        stage.setHeight(DEFAULT_HEIGHT);
        stage.setResizable(true);
        Image icon = ImageFactory.createTitleIcon();
        if (icon != null) {
            stage.getIcons().add(icon);
        }
        context.showMainMenu();
        stage.show();
    }

    /**
     * Standard Java entry point. Delegates to {@link Application#launch}.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
