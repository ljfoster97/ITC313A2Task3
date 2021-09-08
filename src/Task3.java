
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public static final String BLANK = "";
    public static final String inputFilePath = "taxrates.txt";


    Map<IncomeRange, TaxModel> dataMap = new LinkedHashMap<>();

    @Override
    public void start(Stage stage) {

        try {
            File file = new File(inputFilePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            int rowIndex = 1;
            IncomeRange incomeRange = null;
            TaxModel taxModel = null;
            while ((line = br.readLine()) != null) {
                if (rowIndex !=1) {
                    String[] lineArr = line.split("\s+");
                    System.out.println("Linearr:"+ Arrays.toString(lineArr));
                    String limit = lineArr[0].trim();
                    System.out.println("limit:"+limit);
                    String taxInfo = lineArr[1].trim();
                    System.out.println("taxinfo:"+taxInfo);
                    limit = limit.replace(",", "").replace("$", "");
                    System.out.println("Limit:"+limit);

                    if (limit.contains("-")) {
                        String[] limitArr = limit.split("-");
                        System.out.println("LimitArr:"+ Arrays.toString(limitArr));
                        incomeRange = new IncomeRange(Double.parseDouble(limitArr[0]), Double.parseDouble(limitArr[1]));
                    } else {
                        String[] limitArr = limit.split("and");
                        System.out.println("LimitArr:"+ Arrays.toString(limitArr));
                        incomeRange = new IncomeRange(Double.parseDouble(limitArr[0]), -1);
                    }


                    String baseTax, additionalTaxInfo;

                    if (taxInfo.length()==1) {
                        System.out.println("Length is 1:"+taxInfo);
                        taxModel = new TaxModel(0, 0, 0);
                    } else {
                        if (taxInfo.contains("plus")) {
                            String[] taxInfoArr = taxInfo.split("plus");
                            baseTax = taxInfoArr[0];
                            additionalTaxInfo = taxInfoArr[1];
                            System.out.println("Foo");
                        } else {
                            System.out.println("Bar");
                            baseTax = "0";
                            additionalTaxInfo = taxInfo;
                        }
                        additionalTaxInfo = additionalTaxInfo.replace("$", "");
                        String[] additionalTaxInfoArr = additionalTaxInfo.split("cents for each 1 over");
                        String cents = additionalTaxInfoArr[0];
                        String overLimit = additionalTaxInfoArr[1];

                        baseTax = baseTax.replace(",", "").replace("$", "");
                        overLimit = overLimit.replace(",", "").replace("$", "");
                        taxModel = new TaxModel(Double.parseDouble(cents), Double.parseDouble(baseTax), Double.parseDouble(overLimit));
                    }
                    dataMap.put(incomeRange, taxModel);
                }
                rowIndex++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        stage.setTitle("Tax Calculation");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label incomeLabel = new Label("Taxable Income:");
        grid.add(incomeLabel, 0, 0);

        TextField incomeField = new TextField();
        grid.add(incomeField, 1, 0);

        Label taxLabel = new Label("Tax:");
        grid.add(taxLabel, 0, 1);

        TextField taxField = new TextField();
        taxField.setEditable(false);
        grid.add(taxField, 1, 1);


        Button calculateButton = new Button("Calcuate");
        grid.add(calculateButton, 1, 2);

        calculateButton.setOnAction(actionEvent -> {
            String taxableIncome = incomeField.getText();
            if (BLANK.equals(taxableIncome)) {
                this.alert("Input Missing", "Please enter Taxable income!!", AlertType.ERROR);
            } else {
                double calculatedTax = 0;
                double taxableIncomeDbl = Double.parseDouble(taxableIncome);
                for (IncomeRange range: dataMap.keySet()) {
                    if (taxableIncomeDbl >= range.getLowerLimit() && taxableIncomeDbl <= range.getUpperLimit()) {
                        TaxModel taxModel = dataMap.get(range);
                        calculatedTax = taxModel.getBaseTax() + (taxableIncomeDbl - taxModel.getOverLimit()) * (taxModel.getCentsPerDollar() / 100);
                    }
                }
                taxField.setText(String.valueOf(calculatedTax));
            }

        });

        Scene scene = new Scene(grid, 500, 275);
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
