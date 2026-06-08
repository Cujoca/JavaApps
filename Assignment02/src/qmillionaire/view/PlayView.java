/*
 * PlayView.java
 * CST8221 - JAP - Assignment 02
 * Author:      Andrei Cojocaru, 041032238
 * Professor:  Dr. James Mwangi
 */
package qmillionaire.view;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import qmillionaire.controller.AppContext;
import qmillionaire.model.PrizeLadder;
import qmillionaire.model.Theme;

/**
 * Play Mode of the application. The layout mirrors the assignment
 * specification:
 * <ul>
 *   <li>Left: PLAYER INFO + QUANTUM HELPS</li>
 *   <li>Center: question header, answer grid (2 x 2) and action buttons</li>
 *   <li>Right: 15 step prize ladder with the current rung highlighted</li>
 * </ul>
 * Only the layout is required at this assignment level; the controls are
 * wired to handlers that display a "not implemented" notice.
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public final class PlayView {

    /** Index of the question currently displayed (1-based). */
    private static final int CURRENT_QUESTION = 1;

    private final AppContext context;
    private final ToggleGroup answersGroup = new ToggleGroup();

    /**
     * Creates the view bound to the application context.
     *
     * @param context the application context
     */
    public PlayView(AppContext context) {
        this.context = context;
    }

    /**
     * Assembles the play mode scene.
     *
     * @return the JavaFX {@link Scene} ready to be installed on the stage
     */
    public Scene build() {
        ResourceBundle bundle = context.getBundle();
        Theme theme = context.getTheme();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + Theme.toCss(theme.background()) + ";");
        root.setTop(new MenuBarBuilder(context).build());

        VBox leftPanel = buildLeftPanel(bundle, theme);
        VBox centerPanel = buildCenterPanel(bundle, theme);
        VBox rightPanel = buildPrizeLadder(bundle, theme);

        HBox columns = new HBox(12, leftPanel, centerPanel, rightPanel);
        HBox.setHgrow(centerPanel, Priority.ALWAYS);
        columns.setPadding(new Insets(12));
        root.setCenter(columns);

        Button back = new Button(bundle.getString("main.back"));
        back.setStyle("-fx-font-weight: bold; -fx-padding: 6 18 6 18;");
        back.setOnAction(new BackHandler());
        HBox bottom = new HBox(back);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(8, 14, 12, 14));
        root.setBottom(bottom);

        return new Scene(root);
    }

    /**
     * Builds the left column of the play view (player info + quantum
     * helps).
     *
     * @param bundle the active resource bundle
     * @param theme  the active theme
     * @return the assembled left column
     */
    private VBox buildLeftPanel(ResourceBundle bundle, Theme theme) {
        Label info = new Label(bundle.getString("play.player.info"));
        info.setStyle("-fx-text-fill: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-font-weight: bold; -fx-font-size: 14px;");

        VBox playerLines = new VBox(4,
                row(bundle.getString("play.player.name"), bundle.getString("play.player.sampleName"), theme),
                row(bundle.getString("play.player.login"), bundle.getString("play.player.sampleLogin"), theme),
                row(bundle.getString("play.player.historical"), "$50450", theme));

        Label currentMatch = new Label(bundle.getString("play.player.current") + " $0");
        currentMatch.setStyle("-fx-text-fill: " + Theme.toCss(theme.textSuccess()) + ";"
                + "-fx-font-weight: bold; -fx-font-size: 13px;");

        Label time = new Label(bundle.getString("play.player.time") + " 7s");
        time.setStyle("-fx-text-fill: " + Theme.toCss(theme.textWarning()) + ";"
                + "-fx-font-weight: bold; -fx-font-size: 14px;");

        Label helpsHeader = new Label(bundle.getString("play.helps"));
        helpsHeader.setStyle("-fx-text-fill: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-font-weight: bold; -fx-font-size: 14px;");

        Button superposition = helpButton(bundle.getString("play.helps.superposition"), "#b266ff");
        superposition.setOnAction(new HelpHandler("superposition"));
        Button entanglement = helpButton(bundle.getString("play.helps.entanglement"), "#ff5fa2");
        entanglement.setOnAction(new HelpHandler("entanglement"));
        Button interference = helpButton(bundle.getString("play.helps.interference"), "#33b5e5");
        interference.setOnAction(new HelpHandler("interference"));

        VBox helps = new VBox(8, helpsHeader, superposition, entanglement, interference);

        VBox panel = new VBox(12, info, playerLines, currentMatch, time, helps);
        panel.setPadding(new Insets(12));
        panel.setStyle("-fx-background-color: " + Theme.toCss(theme.panel()) + ";"
                + "-fx-border-color: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-border-width: 1;");
        panel.setPrefWidth(200);
        return panel;
    }

    /**
     * Builds the central column of the play view (question + answers +
     * action buttons).
     *
     * @param bundle the active resource bundle
     * @param theme  the active theme
     * @return the assembled center column
     */
    private VBox buildCenterPanel(ResourceBundle bundle, Theme theme) {
        String dollar = NumberFormat.getNumberInstance(context.getLocale())
                .format(PrizeLadder.amountFor(CURRENT_QUESTION));
        String header = MessageFormat.format(
                bundle.getString("play.question.header"),
                CURRENT_QUESTION, dollar);

        Label headerLabel = new Label(header);
        headerLabel.setStyle("-fx-text-fill: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-font-weight: bold; -fx-font-size: 18px;");

        Label question = new Label(bundle.getString("play.question.sample"));
        question.setStyle("-fx-text-fill: " + Theme.toCss(theme.textPrimary()) + ";"
                + "-fx-font-size: 16px;");
        question.setWrapText(true);
        question.setTextAlignment(TextAlignment.CENTER);

        ToggleButton a = answerButton(bundle.getString("play.answer.a"), "A", theme);
        ToggleButton b = answerButton(bundle.getString("play.answer.b"), "B", theme);
        ToggleButton c = answerButton(bundle.getString("play.answer.c"), "C", theme);
        ToggleButton d = answerButton(bundle.getString("play.answer.d"), "D", theme);

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(16);
        grid.setAlignment(Pos.CENTER);
        grid.add(a, 0, 0);
        grid.add(b, 1, 0);
        grid.add(c, 0, 1);
        grid.add(d, 1, 1);

        Button confirm = new Button(bundle.getString("play.button.confirm"));
        confirm.setStyle("-fx-background-color: #cccccc;"
                + "-fx-text-fill: #444444;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 8 22 8 22;");
        confirm.setOnAction(new ConfirmAnswerHandler());

        Button stop = new Button(bundle.getString("play.button.stop"));
        stop.setStyle("-fx-background-color: #d92626;"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 8 22 8 22;");
        stop.setOnAction(new StopHandler());

        HBox actions = new HBox(16, confirm, stop);
        actions.setAlignment(Pos.CENTER);

        VBox panel = new VBox(18, headerLabel, question, grid, actions);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPadding(new Insets(16));
        panel.setStyle("-fx-background-color: " + Theme.toCss(theme.panel()) + ";"
                + "-fx-border-color: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-border-width: 1;");
        return panel;
    }

    /**
     * Builds the right column with the 15 step prize ladder.
     *
     * @param bundle the active resource bundle
     * @param theme  the active theme
     * @return the assembled prize ladder
     */
    private VBox buildPrizeLadder(ResourceBundle bundle, Theme theme) {
        Label header = new Label(bundle.getString("play.ladder"));
        header.setStyle("-fx-text-fill: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-font-weight: bold; -fx-font-size: 14px;");

        VBox rows = new VBox(2);
        NumberFormat fmt = NumberFormat.getNumberInstance(context.getLocale());
        for (int i = PrizeLadder.LEVELS; i >= 1; i--) {
            int amount = PrizeLadder.amountFor(i);
            Label row = new Label(i + ": $" + fmt.format(amount));
            row.setPrefWidth(120);
            row.setPadding(new Insets(2, 6, 2, 6));
            if (i == CURRENT_QUESTION) {
                row.setStyle("-fx-background-color: " + Theme.toCss(theme.highlight()) + ";"
                        + "-fx-text-fill: " + Theme.toCss(theme.background()) + ";"
                        + "-fx-font-weight: bold;");
            } else {
                row.setStyle("-fx-text-fill: " + Theme.toCss(theme.textPrimary()) + ";");
            }
            rows.getChildren().add(row);
        }

        VBox panel = new VBox(10, header, rows);
        panel.setPadding(new Insets(12));
        panel.setStyle("-fx-background-color: " + Theme.toCss(theme.panel()) + ";"
                + "-fx-border-color: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-border-width: 1;");
        panel.setPrefWidth(160);
        return panel;
    }

    /**
     * Renders one labeled "key: value" line on the left panel.
     *
     * @param label the label shown on the left
     * @param value the value shown on the right
     * @param theme the active theme
     * @return the assembled row
     */
    private HBox row(String label, String value, Theme theme) {
        Label l = new Label(label);
        l.setStyle("-fx-text-fill: " + Theme.toCss(theme.textPrimary()) + ";");
        Label v = new Label(value);
        v.setStyle("-fx-text-fill: " + Theme.toCss(theme.textPrimary()) + "; -fx-font-weight: bold;");
        HBox row = new HBox(6, l, v);
        return row;
    }

    /**
     * Creates a quantum help button with the supplied label and background.
     *
     * @param text the label displayed on the button
     * @param bg   the CSS color used as the background
     * @return the assembled button
     */
    private Button helpButton(String text, String bg) {
        Button b = new Button(text);
        b.setPrefWidth(140);
        b.setStyle("-fx-background-color: " + bg + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 11px;");
        return b;
    }

    /**
     * Creates one of the four answer toggle buttons.
     *
     * @param text   the label displayed on the button
     * @param letter the answer letter (A/B/C/D)
     * @param theme  the active theme
     * @return the assembled toggle button
     */
    private ToggleButton answerButton(String text, String letter, Theme theme) {
        ToggleButton btn = new ToggleButton(text);
        btn.setToggleGroup(answersGroup);
        btn.setPrefSize(220, 60);
        btn.setStyle(answerStyleUnselected(theme));
        btn.setOnAction(new AnswerHandler(letter));
        btn.selectedProperty().addListener(new AnswerSelectionListener(btn, theme));
        return btn;
    }

    /**
     * Builds the CSS rule applied to an answer button when it is NOT
     * selected.
     *
     * @param theme the active theme
     * @return the CSS string to apply to the button
     */
    private String answerStyleUnselected(Theme theme) {
        return "-fx-background-color: " + Theme.toCss(theme.background()) + ";"
                + "-fx-text-fill: " + Theme.toCss(theme.textPrimary()) + ";"
                + "-fx-border-color: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-border-width: 2;"
                + "-fx-background-radius: 30;"
                + "-fx-border-radius: 30;"
                + "-fx-font-size: 13px;";
    }

    /**
     * Builds the CSS rule applied to an answer button when it IS the one
     * currently selected.
     *
     * @param theme the active theme
     * @return the CSS string to apply to the button
     */
    private String answerStyleSelected(Theme theme) {
        return "-fx-background-color: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-text-fill: " + Theme.toCss(theme.background()) + ";"
                + "-fx-border-color: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-border-width: 3;"
                + "-fx-background-radius: 30;"
                + "-fx-border-radius: 30;"
                + "-fx-font-size: 13px;"
                + "-fx-font-weight: bold;"
                + "-fx-effect: dropshadow(gaussian, " + Theme.toCss(theme.highlight()) + ", 12, 0.4, 0, 0);";
    }

    /**
     * Shows a localized "not implemented" alert. Used by the action buttons
     * that have no behavior at this assignment level.
     *
     * @param key the resource bundle key to display
     */
    private void showNotImplemented(String key) {
        ResourceBundle bundle = context.getBundle();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("about.title"));
        alert.setHeaderText(null);
        alert.setContentText(bundle.getString("dialog.notImplemented") + "\n[" + key + "]");
        alert.showAndWait();
    }

    /**
     * Returns to the main menu when the "Back" button is pressed.
     */
    private final class BackHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            context.showMainMenu();
        }
    }

    /**
     * Records the selected answer letter. The actual quiz logic is wired
     * in later assignments.
     */
    private final class AnswerHandler implements EventHandler<ActionEvent> {
        private final String letter;

        /**
         * Creates a handler bound to the supplied answer letter.
         *
         * @param letter the answer letter (A/B/C/D)
         */
        AnswerHandler(String letter) {
            this.letter = letter;
        }

        @Override
        public void handle(ActionEvent event) {
            /* Selection is tracked by the ToggleGroup. */
        }

        /**
         * @return the letter associated with this handler
         */
        public String getLetter() {
            return letter;
        }
    }

    /**
     * Handles the "Confirm Answer" button.
     */
    private final class ConfirmAnswerHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            showNotImplemented("confirmAnswer");
        }
    }

    /**
     * Handles the "Stop &amp; Take Money" button.
     */
    private final class StopHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            showNotImplemented("stopAndTake");
        }
    }

    /**
     * Handles one of the three quantum help buttons.
     */
    private final class HelpHandler implements EventHandler<ActionEvent> {
        private final String kind;

        /**
         * Creates a handler bound to the supplied help kind.
         *
         * @param kind the help identifier
         */
        HelpHandler(String kind) {
            this.kind = kind;
        }

        @Override
        public void handle(ActionEvent event) {
            showNotImplemented("help:" + kind);
        }
    }

    /**
     * Restyles an answer toggle button each time its selection state
     * changes. The selected state uses the highlight color as a background
     * plus a glow effect; the unselected state restores the default look.
     */
    private final class AnswerSelectionListener implements ChangeListener<Boolean> {
        private final ToggleButton button;
        private final Theme theme;

        /**
         * Creates a listener bound to the supplied button and theme.
         *
         * @param button the answer toggle button being observed
         * @param theme  the active theme used to rebuild the CSS
         */
        AnswerSelectionListener(ToggleButton button, Theme theme) {
            this.button = button;
            this.theme = theme;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> obs,
                            Boolean wasSelected, Boolean isSelected) {
            if (Boolean.TRUE.equals(isSelected)) {
                button.setStyle(answerStyleSelected(theme));
            } else {
                button.setStyle(answerStyleUnselected(theme));
            }
        }
    }
}
