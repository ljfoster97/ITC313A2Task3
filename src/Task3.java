
import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Task3 extends Application {

    @Override
    public void start(Stage stage) {
        TaxController taxController = new TaxController();
        taxController.getTaxRates();

        stage.setTitle("Tax Calculation");
 
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label incomeLabel = new Label("Taxable Income:");
        gridPane.add(incomeLabel, 0, 0);

        TextField incomeField = new TextField();
        incomeField.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(incomeField, 1, 0);

        Label taxLabel = new Label("Tax:");
        gridPane.add(taxLabel, 0, 1);

        TextField taxField = new TextField();
        taxField.setAlignment(Pos.CENTER_RIGHT);
        taxField.setEditable(false);
        gridPane.add(taxField, 1, 1);


        Button buttonCalculate = new Button("Calculate");
        gridPane.add(buttonCalculate, 1, 2);

        buttonCalculate.setOnAction(actionEvent -> {
            String taxableIncome = incomeField.getText();
            if (TaxController.BLANK.equals(taxableIncome)) {
                this.alert("Input Missing", "Please enter a valid taxable income amount.", AlertType.ERROR);
            } else {
                double calculatedTax = 0;
                double taxableIncomeDbl = Double.parseDouble(taxableIncome);
                for (IncomeRange range: TaxController.dataMap.keySet()) {
                    if (taxableIncomeDbl >= range.getLowerLimit() && taxableIncomeDbl <= range.getUpperLimit()) {
                        TaxModel taxModel = TaxController.dataMap.get(range);
                        System.out.println("taxmodel:"+ taxModel);
                        calculatedTax = taxModel.getBaseTax() + (taxableIncomeDbl - taxModel.getOverLimit()) * (taxModel.getCentsPerDollar());
                    }
                }
                taxField.setText("$"+ calculatedTax);
            }

        });

        Scene scene = new Scene(gridPane, 500, 275);
        stage.setScene(scene);

        stage.show();
    }

    public void alert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

