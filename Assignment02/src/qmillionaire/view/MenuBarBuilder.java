/*
 * MenuBarBuilder.java
 * CST8221 - JAP - Assignment 02
 * Author:      Andrei Cojocaru, 041032238
 * Professor:  Dr. James Mwangi
 */
package qmillionaire.view;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import qmillionaire.controller.AppContext;
import qmillionaire.model.Theme;

/**
 * Builds the shared menu bar (File / Configuration / Look &amp; Feel /
 * Help). The same menu bar is added at the top of every view so the user
 * can change language, theme or stylesheet from anywhere.
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public final class MenuBarBuilder {

    private final AppContext context;

    /**
     * Creates a builder bound to the application context. The context is
     * needed because each menu item triggers a navigation or preference
     * change on the application.
     *
     * @param context the application context
     */
    public MenuBarBuilder(AppContext context) {
        this.context = context;
    }

    /**
     * Builds and returns a brand new {@link MenuBar} populated with all
     * application menus.
     *
     * @return a fully populated menu bar
     */
    public MenuBar build() {
        ResourceBundle bundle = context.getBundle();

        Menu fileMenu = new Menu(bundle.getString("menu.file"));
        MenuItem exit = new MenuItem(bundle.getString("menu.file.exit"));
        exit.setOnAction(new ExitHandler());
        fileMenu.getItems().add(exit);

        Menu configMenu = new Menu(bundle.getString("menu.configuration"));

        Menu languageMenu = new Menu(bundle.getString("menu.configuration.language"));
        MenuItem english = new MenuItem(bundle.getString("menu.configuration.language.english"));
        english.setOnAction(new LanguageHandler(Locale.ENGLISH));
        MenuItem french = new MenuItem(bundle.getString("menu.configuration.language.french"));
        french.setOnAction(new LanguageHandler(Locale.FRENCH));
        languageMenu.getItems().addAll(english, french);

        Menu colorMenu = new Menu(bundle.getString("menu.configuration.color"));
        MenuItem classic = new MenuItem(bundle.getString("menu.configuration.color.classic"));
        classic.setOnAction(new ThemeHandler(Theme.CLASSIC));
        MenuItem dark = new MenuItem(bundle.getString("menu.configuration.color.dark"));
        dark.setOnAction(new ThemeHandler(Theme.DARK));
        MenuItem contrast = new MenuItem(bundle.getString("menu.configuration.color.contrast"));
        contrast.setOnAction(new ThemeHandler(Theme.HIGH_CONTRAST));
        colorMenu.getItems().addAll(classic, dark, contrast);

        configMenu.getItems().addAll(languageMenu, colorMenu);

        Menu lafMenu = new Menu(bundle.getString("menu.lookAndFeel"));
        MenuItem modena = new MenuItem(bundle.getString("menu.lookAndFeel.modena"));
        modena.setOnAction(new LookAndFeelHandler(AppContext.LookAndFeel.MODENA));
        MenuItem caspian = new MenuItem(bundle.getString("menu.lookAndFeel.caspian"));
        caspian.setOnAction(new LookAndFeelHandler(AppContext.LookAndFeel.CASPIAN));
        lafMenu.getItems().addAll(modena, caspian);

        Menu helpMenu = new Menu(bundle.getString("menu.help"));
        MenuItem about = new MenuItem(bundle.getString("menu.help.about"));
        about.setOnAction(new AboutHandler());
        helpMenu.getItems().add(about);

        MenuBar bar = new MenuBar();
        bar.getMenus().addAll(fileMenu, configMenu, lafMenu, helpMenu);
        return bar;
    }

    /**
     * Closes the primary stage when "File / Exit" is chosen.
     */
    private final class ExitHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            context.getStage().close();
        }
    }

    /**
     * Switches the application locale. Each instance is bound to a specific
     * target {@link Locale} so the handler can be registered against a
     * dedicated menu item.
     */
    private final class LanguageHandler implements EventHandler<ActionEvent> {
        private final Locale target;

        /**
         * Creates a handler that switches the application to the supplied
         * locale.
         *
         * @param target the locale to install
         */
        LanguageHandler(Locale target) {
            this.target = target;
        }

        @Override
        public void handle(ActionEvent event) {
            context.setLocale(target);
        }
    }

    /**
     * Switches the active {@link Theme}.
     */
    private final class ThemeHandler implements EventHandler<ActionEvent> {
        private final Theme target;

        /**
         * Creates a handler that activates the supplied theme.
         *
         * @param target the theme to install
         */
        ThemeHandler(Theme target) {
            this.target = target;
        }

        @Override
        public void handle(ActionEvent event) {
            context.setTheme(target);
        }
    }

    /**
     * Switches the JavaFX user-agent stylesheet (Modena / Caspian).
     */
    private final class LookAndFeelHandler implements EventHandler<ActionEvent> {
        private final AppContext.LookAndFeel target;

        /**
         * Creates a handler that activates the supplied stylesheet.
         *
         * @param target the Look &amp; Feel to install
         */
        LookAndFeelHandler(AppContext.LookAndFeel target) {
            this.target = target;
        }

        @Override
        public void handle(ActionEvent event) {
            context.setLookAndFeel(target);
        }
    }

    /**
     * Displays the localized "About" dialog.
     */
    private final class AboutHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ResourceBundle bundle = context.getBundle();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("about.title"));
            alert.setHeaderText(bundle.getString("about.title"));
            alert.setContentText(bundle.getString("about.message"));
            alert.showAndWait();
        }
    }
}
