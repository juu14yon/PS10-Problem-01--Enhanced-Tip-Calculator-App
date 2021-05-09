import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class TipCalculatorController {
    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percent = NumberFormat.getPercentInstance();
    private BigDecimal tipPercentage = new BigDecimal(0.15);

    @FXML private Label tipPercentageLabel;
    @FXML private TextField amountTextField;
    @FXML private Slider tipPercentageSlider;
    @FXML private TextField peopleAmountTextField;
    @FXML private TextField tipTextField;
    @FXML private TextField totalTextField;
    @FXML private Button calculateButton;
    @FXML private TextField perPersonTextField;

    @FXML
    void calculateButtonPressed(ActionEvent event) {
        try {
            BigDecimal amount = new BigDecimal(amountTextField.getText());

            try {
                int people = Integer.parseInt(peopleAmountTextField.getText());

                while (people<=0){
                    peopleAmountTextField.setText("Enter positive number");
                    peopleAmountTextField.selectAll();
                    peopleAmountTextField.requestFocus();
                    people = Integer.parseInt(peopleAmountTextField.getText());
                }

                BigDecimal tip = amount.multiply(tipPercentage);
                BigDecimal total = amount.add(tip);
                BigDecimal perPerson = total.divide(BigDecimal.valueOf(people));

                tipTextField.setText(currency.format(tip));
                totalTextField.setText(currency.format(total));
                perPersonTextField.setText(currency.format(perPerson));
            }
            catch (NumberFormatException ex1){
                peopleAmountTextField.setText("Enter positive integer");
                peopleAmountTextField.selectAll();
                peopleAmountTextField.requestFocus();
            }
        }
        catch (NumberFormatException ex) {
            amountTextField.setText("Enter amount");
            amountTextField.selectAll();
            amountTextField.requestFocus();
        }
        catch (ArithmeticException ex){
            peopleAmountTextField.setText("Enter positive number");
            peopleAmountTextField.selectAll();
            peopleAmountTextField.requestFocus();
        }
    }

    public void initialize(){
        currency.setRoundingMode(RoundingMode.HALF_UP);

        tipPercentageSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        tipPercentage = BigDecimal.valueOf(newValue.intValue()/100.0);
                        tipPercentageLabel.setText(percent.format(tipPercentage));
                    }
                }
        );
    }

}
