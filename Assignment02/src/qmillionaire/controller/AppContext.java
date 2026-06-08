/*
 * AppContext.java
 * CST8221 - JAP - Assignment 02
 * Author:      Andrei Cojocaru, 041032238
 * Professor:  Dr. James Mwangi
 */
package qmillionaire.controller;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import qmillionaire.model.Theme;
import qmillionaire.view.DesignView;
import qmillionaire.view.MainMenuView;
import qmillionaire.view.PlayView;

/**
 * Central controller of the application.
 * <p>
 * The {@code AppContext} object owns the primary stage and the current set of
 * user preferences (active {@link Locale}, color {@link Theme} and JavaFX
 * stylesheet). Every view delegates navigation and preference reads to this
 * controller so that switching language or theme triggers a global UI
 * rebuild from a single place.
 * </p>
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public final class AppContext {

    /**
     * Identifier of the screen currently shown on the primary stage. The
     * controller uses this value to know which view to rebuild when the
     * language or theme is changed.
     */
    public enum Screen {
        /** The main menu (DESIGN / PLAY buttons). */
        MAIN_MENU,
        /** The design mode placeholder. */
        DESIGN,
        /** The play mode quiz. */
        PLAY
    }

    /**
     * Available JavaFX stylesheet families.
     */
    public enum LookAndFeel {
        /** Default modern stylesheet shipped with JavaFX 8+. */
        MODENA,
        /** Legacy stylesheet preserved for compatibility. */
        CASPIAN
    }

    private final Stage stage;
    private Locale currentLocale;
    private Theme currentTheme;
    private LookAndFeel currentLaf;
    private Screen currentScreen;
    private ResourceBundle bundle;

    /**
     * Builds an {@code AppContext} bound to the supplied primary stage.
     * The default locale is English, the default theme is
     * {@link Theme#CLASSIC} and the default Look &amp; Feel is
     * {@link LookAndFeel#MODENA}.
     *
     * @param stage the primary stage owned by the JavaFX runtime
     */
    public AppContext(Stage stage) {
        this.stage = stage;
        this.currentLocale = Locale.ENGLISH;
        this.currentTheme = Theme.CLASSIC;
        this.currentLaf = LookAndFeel.MODENA;
        this.currentScreen = Screen.MAIN_MENU;
        reloadBundle();
    }

    /**
     * Reloads the localized message bundle for the current locale. Bundles
     * are looked up under the {@code resources} package using the keys in
     * {@code messages_en.properties} and {@code messages_fr.properties}.
     */
    private void reloadBundle() {
        this.bundle = ResourceBundle.getBundle("resources.messages", currentLocale);
    }

    /**
     * Returns the primary JavaFX stage owned by the controller.
     *
     * @return the primary JavaFX stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Returns the resource bundle that corresponds to the active locale.
     *
     * @return the active localized message bundle
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Returns the currently active color theme.
     *
     * @return the active color theme
     */
    public Theme getTheme() {
        return currentTheme;
    }

    /**
     * Returns the currently active locale.
     *
     * @return the active locale
     */
    public Locale getLocale() {
        return currentLocale;
    }

    /**
     * Returns the currently active JavaFX user-agent stylesheet.
     *
     * @return the active JavaFX stylesheet
     */
    public LookAndFeel getLookAndFeel() {
        return currentLaf;
    }

    /**
     * Switches the application language and rebuilds the current screen so
     * that all visible labels are re-translated.
     *
     * @param locale the new active locale
     */
    public void setLocale(Locale locale) {
        if (locale == null || locale.equals(currentLocale)) {
            return;
        }
        currentLocale = locale;
        reloadBundle();
        refresh();
    }

    /**
     * Switches the color theme and rebuilds the current screen so that the
     * new palette is applied to every node.
     *
     * @param theme the new active theme
     */
    public void setTheme(Theme theme) {
        if (theme == null || theme == currentTheme) {
            return;
        }
        currentTheme = theme;
        refresh();
    }

    /**
     * Switches between the supported JavaFX stylesheets.
     *
     * @param laf the new active stylesheet
     */
    public void setLookAndFeel(LookAndFeel laf) {
        if (laf == null || laf == currentLaf) {
            return;
        }
        currentLaf = laf;
        Application.setUserAgentStylesheet(
                laf == LookAndFeel.CASPIAN
                        ? Application.STYLESHEET_CASPIAN
                        : Application.STYLESHEET_MODENA);
        refresh();
    }

    /**
     * Displays the main menu (logo + DESIGN / PLAY buttons).
     */
    public void showMainMenu() {
        currentScreen = Screen.MAIN_MENU;
        MainMenuView view = new MainMenuView(this);
        applyScene(view.build());
    }

    /**
     * Displays the design mode.
     */
    public void showDesign() {
        currentScreen = Screen.DESIGN;
        DesignView view = new DesignView(this);
        applyScene(view.build());
    }

    /**
     * Displays the play mode quiz.
     */
    public void showPlay() {
        currentScreen = Screen.PLAY;
        PlayView view = new PlayView(this);
        applyScene(view.build());
    }

    /**
     * Rebuilds the active screen. Called after a language or theme change.
     */
    public void refresh() {
        switch (currentScreen) {
            case MAIN_MENU:
                showMainMenu();
                break;
            case DESIGN:
                showDesign();
                break;
            case PLAY:
                showPlay();
                break;
            default:
                showMainMenu();
                break;
        }
    }

    /**
     * Installs the supplied scene on the primary stage and refreshes the
     * window title from the active resource bundle.
     *
     * @param scene the scene that should become the active one
     */
    private void applyScene(Scene scene) {
        stage.setScene(scene);
        stage.setTitle(bundle.getString("app.title"));
    }
}
