/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Mortos
 */
public class Calculator {

    private double resultValue = 0;
    private int operator = -1;
    private boolean isOperationPerformed = false;
    private boolean mathError = false;
    private double memo = 0;
    private double numberMemo = 0;
    private boolean m = false;
    private boolean check = false;
    private boolean btnEqualChecked = false;
    private boolean isShowing = false;
    private double number = 0;

    public Calculator() {
        resultValue = 0;
        operator = -1;
        isOperationPerformed = false;
        mathError = false;
        memo = 0;
        numberMemo = 0;
        m = false;
        check = false;
        btnEqualChecked = false;
        isShowing = false;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    //Function use to click number button
    public void numberClick(JTextField txtScreen, ActionEvent evt) {
        if (isShowing) {
            txtScreen.setText("");
            isShowing = false;
        }
        if (isOperationPerformed) {
            txtScreen.setText("0");
            isOperationPerformed = false;

        }
        if (m) {
            txtScreen.setText("0");
            m = false;
        }
        if (btnEqualChecked) {
            txtScreen.setText("0");
            btnEqualChecked = false;
        }
        JButton button = (JButton) evt.getSource();//Get button from screen

        //Enter number and dot
        if (button.getText().equals(".")) {
            if (!txtScreen.getText().contains(".")) {
                txtScreen.setText(txtScreen.getText() + button.getText());
            }
        } else {
            txtScreen.setText(txtScreen.getText() + button.getText());
        }

        //Remove number zero
        String text = txtScreen.getText();
        // 01234567 then remove 0
        if (text.charAt(0) == '0' && text.charAt(1) != '.') {
            txtScreen.setText(text.substring(1, text.length()));
        }

        // -012345 then remove 0;
        if (text.charAt(0) == '-' && text.charAt(1) == '0'
                && text.charAt(2) != '.') {
            txtScreen.setText("-" + text.substring(2, text.length()));
        }
        check = false;
    }

    void displayResult(double number, JTextField txtScreen) {
        DecimalFormat df = new DecimalFormat("#.########");
        String result = df.format(number);
        txtScreen.setText(result);
        isShowing = true;
    }

    public void calculate(JTextField txtScreen) {
        //Math error will reset resultValue
        if (mathError) {
            resultValue = number;
            mathError = false;
        }
        if (!check) {
            if (operator == -1) {
                resultValue = Double.parseDouble(txtScreen.getText());
            } else {
                if (mathError) {
                    numberMemo = 1;
                } else {
                    numberMemo = Double.parseDouble(txtScreen.getText());
                }
                switch (operator) {
                    case 1:
                        resultValue = resultValue + numberMemo;
                        break;
                    case 2:
                        resultValue = resultValue - numberMemo;
                        break;
                    case 3:
                        resultValue = resultValue * numberMemo;
                        break;
                    case 4:
                        if (numberMemo == 0) {
                            number = resultValue;
                            txtScreen.setText("Math Error");
                            mathError = true;
                            break;
                        } else {
                            resultValue = resultValue / numberMemo;
                        }
                        break;
                }

            }
            if (txtScreen.getText().equals("Math Error")) {
                isShowing = true;
            } else {
                displayResult(resultValue, txtScreen);
            }
            check = true;
        }
        isOperationPerformed = true;
    }

    public void equalsButtonClick(JTextField txtScreen) {
        //math error will reset resultValue
        if (!txtScreen.getText().equals("Math Error")) {
            calculate(txtScreen);
            operator = -1;
        } else {
            resultValue = 0;
            mathError = false;
            displayResult(resultValue, txtScreen);
        }

        isOperationPerformed = false;
        btnEqualChecked = true;
    }

    public void clearAll(JTextField txtScreen) {
        txtScreen.setText("0");
        resultValue = 0;
        operator = -1;
        isOperationPerformed = false;
        mathError = false;
        memo = 0;
        numberMemo = 0;
        m = false;
        check = false;
        btnEqualChecked = false;
        isShowing = false;
    }

    public void negativeSign(JTextField txtScreen) {
        if (!mathError && !txtScreen.getText().equals("0")) {
            String text = txtScreen.getText();
            //Check number has negative or positive
            if (!text.contains("-")) {
                txtScreen.setText("-" + text);
            } else {
                txtScreen.setText(text.substring(1, text.length()));
            }
            check = false;
        }
    }

    public void squareRoot(JTextField txtScreen) {
        if (mathError) {
            resultValue = number;
        } else {
            equalsButtonClick(txtScreen);
        }
        //Check number < 0 or not
        if (resultValue < 0) {
            txtScreen.setText("Math Error");
            number = resultValue;
            mathError = true;
            operator = -1;
        } else {
            //If number >= 0 then implement sqrt
            resultValue = Math.sqrt(resultValue);
            displayResult(resultValue, txtScreen);
            check = false;
        }

    }

    public void percentCalcultor(JTextField txtScreen) {
        if (txtScreen.getText().equals("Math Error")) {
            resultValue = number;
        } else {
            equalsButtonClick(txtScreen);
        }
        resultValue = resultValue / 100;
        displayResult(resultValue, txtScreen);
        check = false;
    }

    public void onePerX(JTextField txtScreen) {
        if (txtScreen.getText().equals("Math Error")) {
            resultValue = number;
        } else {
            equalsButtonClick(txtScreen);
        }
        resultValue = 1 / resultValue;
        displayResult(resultValue, txtScreen);
        check = false;
    }

    public void mPlusClick(JTextField txtScreen) {
        memo += Double.parseDouble(txtScreen.getText());
        m = true; //determind user clicked m+
        check = false;
    }

    public void mMinusClick(JTextField txtScreen) {
        memo -= Double.parseDouble(txtScreen.getText());
        m = true; //determind user clicked m-
        check = false;
    }

    public void MR_Click(JTextField txtScreen) {
        //dislay result on screen and save it in memory
        displayResult(memo, txtScreen);
        m = true;
    }

    public void MC_Click(JTextField txtScreen) {
        memo = 0; //reset memory
    }

    public void MS_Click(JTextField txtScreen) {
        equalsButtonClick(txtScreen);
        memo = resultValue;
        m = true;
    }

    public void BackClear(JTextField txtScreen) {
        if (m == false  && btnEqualChecked == false) {
            String text = txtScreen.getText();
            if (text.length() != 1) {
                if (text.equals("-0.")) {
                    text = "0";
                } else if (text.length() == 2 && text.contains("-")) {
                    text = "0";
                } else if (text.charAt(text.length() - 2) == '.') {
                    text = text.substring(0, text.length() - 2);
                } else {
                    text = text.substring(0, text.length() - 1);
                }
            } else {
                text = "0";
            }
            txtScreen.setText(text);
        }
    }

}
