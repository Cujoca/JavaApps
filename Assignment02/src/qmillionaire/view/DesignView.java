/*
 * DesignView.java
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import qmillionaire.controller.AppContext;
import qmillionaire.model.PrizeLadder;
import qmillionaire.model.Theme;

/**
 * Design Mode of the application. This is a layout-only placeholder for
 * the future database integration: a player editor on the left and a
 * question editor on the right. No functional logic is required by the
 * assignment beyond presenting the controls.
 *
 * @author Andrei Cojocaru
 * @version 1.0
 */
public final class DesignView {

    private final AppContext context;

    /**
     * Creates the view bound to the application context.
     *
     * @param context the application context
     */
    public DesignView(AppContext context) {
        this.context = context;
    }

    /**
     * Assembles the design mode scene.
     *
     * @return the JavaFX {@link Scene} ready to be installed on the stage
     */
    public Scene build() {
        ResourceBundle bundle = context.getBundle();
        Theme theme = context.getTheme();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + Theme.toCss(theme.background()) + ";");
        root.setTop(new MenuBarBuilder(context).build());

        Label title = new Label(bundle.getString("design.title"));
        title.setStyle("-fx-text-fill: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox playersPanel = buildPlayersPanel(bundle, theme);
        VBox questionsPanel = buildQuestionsPanel(bundle, theme);

        HBox columns = new HBox(20, playersPanel, questionsPanel);
        columns.setPadding(new Insets(20));
        columns.setAlignment(Pos.TOP_CENTER);

        VBox center = new VBox(12, title, columns);
        center.setAlignment(Pos.TOP_CENTER);
        center.setPadding(new Insets(16));
        root.setCenter(center);

        Button back = new Button(bundle.getString("main.back"));
        back.setStyle("-fx-font-weight: bold; -fx-padding: 6 18 6 18;");
        back.setOnAction(new BackHandler());

        Label info = new Label(bundle.getString("design.placeholder.info"));
        info.setStyle("-fx-text-fill: " + Theme.toCss(theme.textPrimary()) + ";"
                + "-fx-font-style: italic;");

        HBox bottom = new HBox(20, back, info);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setPadding(new Insets(12, 20, 16, 20));
        root.setBottom(bottom);

        return new Scene(root);
    }

    /**
     * Builds the left column where players are edited.
     *
     * @param bundle the active resource bundle
     * @param theme  the active color theme
     * @return the player editor panel
     */
    private VBox buildPlayersPanel(ResourceBundle bundle, Theme theme) {
        Label header = new Label(bundle.getString("design.label.players"));
        styleHeader(header, theme);

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.add(new Label(bundle.getString("design.label.name") + ":"), 0, 0);
        form.add(new TextField(), 1, 0);
        form.add(new Label(bundle.getString("design.label.login") + ":"), 0, 1);
        form.add(new TextField(), 1, 1);
        form.add(new Label(bundle.getString("design.label.historical") + ":"), 0, 2);
        form.add(new TextField("0"), 1, 2);
        styleFormLabels(form, theme);

        Button add = new Button(bundle.getString("design.button.add"));
        Button remove = new Button(bundle.getString("design.button.remove"));
        Button save = new Button(bundle.getString("design.button.save"));
        HBox buttons = new HBox(8, add, remove, save);

        ListView<String> list = new ListView<>();
        list.getItems().addAll("Alice Johnson", "Bob Smith", "Carol Lee");
        list.setPrefHeight(160);

        VBox panel = new VBox(10, header, form, buttons, list);
        panel.setPadding(new Insets(12));
        panel.setStyle("-fx-background-color: " + Theme.toCss(theme.panel()) + ";"
                + "-fx-border-color: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-border-width: 1;");
        panel.setPrefWidth(340);
        return panel;
    }

    /**
     * Builds the right column where questions are edited.
     *
     * @param bundle the active resource bundle
     * @param theme  the active color theme
     * @return the question editor panel
     */
    private VBox buildQuestionsPanel(ResourceBundle bundle, Theme theme) {
        Label header = new Label(bundle.getString("design.label.questions"));
        styleHeader(header, theme);

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.add(new Label(bundle.getString("design.label.level") + ":"), 0, 0);
        ComboBox<Integer> levelBox = new ComboBox<>();
        for (int i = 1; i <= PrizeLadder.LEVELS; i++) {
            levelBox.getItems().add(i);
        }
        levelBox.getSelectionModel().selectFirst();
        form.add(levelBox, 1, 0);

        TextArea question = new TextArea();
        question.setPromptText(bundle.getString("design.label.question"));
        question.setPrefRowCount(2);
        form.add(new Label(bundle.getString("design.label.question") + ":"), 0, 1);
        form.add(question, 1, 1);

        form.add(new Label(bundle.getString("design.label.optionA") + ":"), 0, 2);
        form.add(new TextField(), 1, 2);
        form.add(new Label(bundle.getString("design.label.optionB") + ":"), 0, 3);
        form.add(new TextField(), 1, 3);
        form.add(new Label(bundle.getString("design.label.optionC") + ":"), 0, 4);
        form.add(new TextField(), 1, 4);
        form.add(new Label(bundle.getString("design.label.optionD") + ":"), 0, 5);
        form.add(new TextField(), 1, 5);

        ComboBox<String> correct = new ComboBox<>();
        correct.getItems().addAll("A", "B", "C", "D");
        correct.getSelectionModel().selectFirst();
        form.add(new Label(bundle.getString("design.label.correct") + ":"), 0, 6);
        form.add(correct, 1, 6);

        styleFormLabels(form, theme);

        Button addQ = new Button(bundle.getString("design.button.addQuestion"));
        Button saveQ = new Button(bundle.getString("design.button.save"));
        HBox buttons = new HBox(8, addQ, saveQ);

        VBox panel = new VBox(10, header, form, buttons);
        panel.setPadding(new Insets(12));
        panel.setStyle("-fx-background-color: " + Theme.toCss(theme.panel()) + ";"
                + "-fx-border-color: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-border-width: 1;");
        panel.setPrefWidth(420);
        return panel;
    }

    /**
     * Applies the panel header style.
     *
     * @param header the header label
     * @param theme  the active theme
     */
    private void styleHeader(Label header, Theme theme) {
        header.setStyle("-fx-text-fill: " + Theme.toCss(theme.highlight()) + ";"
                + "-fx-font-size: 16px; -fx-font-weight: bold;");
    }

    /**
     * Recolors every {@link Label} that lives in the supplied grid so the
     * text matches the panel theme.
     *
     * @param grid  the grid to scan
     * @param theme the active theme
     */
    private void styleFormLabels(GridPane grid, Theme theme) {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (node instanceof Label) {
                ((Label) node).setStyle("-fx-text-fill: " + Theme.toCss(theme.textPrimary()) + ";");
            }
        }
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
}
