
package org.example;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

// Controller class for handling the user interface and logic of a calculator
public class PrimaryController {
    // Fields to keep track of the current and previous base for calculations
    String chosen;
    String prevBase;


    @FXML
    private ComboBox<String> listBox; // ComboBox for selecting base

    @FXML // fx:id="calcTF"
    private TextField calcTF; // TextField to display calculator input and results

    @FXML
    private Button ABtn;
    @FXML
    private Button BBtn;
    @FXML
    private Button CBtn;
    @FXML
    private Button DBtn;
    @FXML
    private Button EBtn;
    @FXML
    private Button FBtn;
    @FXML
    private Button ONEBtn;
    @FXML
    private Button TWOBtn;
    @FXML
    private Button THREEBtn;
    @FXML
    private Button FOURBtn;
    @FXML
    private Button FIVEBtn;
    @FXML
    private Button SIXBtn;
    @FXML
    private Button SEVENBtn;
    @FXML
    private Button EIGHTBtn;
    @FXML
    private Button NINEBtn;
    @FXML
    private Button PLUSBtn;
    @FXML
    private Button SUBBtn;
    @FXML
    private Button MULBtn;
    @FXML
    private Button DIVBtn;
    @FXML
    private Button EQBtn;
    @FXML
    private Button CLEARBtn;
    @FXML
    private Button ZEROBtn;

    @FXML
    Button[] buttons; // Array to hold all buttons for iteration


    @FXML
    public void initialize() {
        // Initialize the array of buttons
        buttons =new Button[] {ZEROBtn, ONEBtn , TWOBtn, THREEBtn, FOURBtn, FIVEBtn, SIXBtn, SEVENBtn, EIGHTBtn, NINEBtn,ABtn, BBtn,CBtn,DBtn, EBtn, FBtn };

        // Ensure the listBox is injected correctly and set its items
        assert listBox != null : "fx:id=\"listBox\" was not injected: check your FXML file 'primary.fxml'.";

        ObservableList<String> items = FXCollections.observableArrayList("BIN", "OCT", "DEC", "HEX");
        // Set the items in the listBox using setItems
        listBox.setItems(items);
        listBox.setOnAction(this::chooseFromList);
        // Set the actions for each button
        setButtonActions();
    }

    // Method to define actions for each calculator button
    private void setButtonActions() {
        ABtn.setOnAction(e -> updateTextField("A"));
        BBtn.setOnAction(e -> updateTextField("B"));
        CBtn.setOnAction(e -> updateTextField("C"));
        DBtn.setOnAction(e -> updateTextField("D"));
        EBtn.setOnAction(e -> updateTextField("E"));
        FBtn.setOnAction(e -> updateTextField("F"));
        ONEBtn.setOnAction(e -> updateTextField("1"));
        TWOBtn.setOnAction(e -> updateTextField("2"));
        THREEBtn.setOnAction(e -> updateTextField("3"));
        FOURBtn.setOnAction(e -> updateTextField("4"));
        FIVEBtn.setOnAction(e -> updateTextField("5"));
        SIXBtn.setOnAction(e -> updateTextField("6"));
        SEVENBtn.setOnAction(e -> updateTextField("7"));
        EIGHTBtn.setOnAction(e -> updateTextField("8"));
        NINEBtn.setOnAction(e -> updateTextField("9"));
        ZEROBtn.setOnAction(e -> updateTextField("0"));
        PLUSBtn.setOnAction(e -> updateTextField("+"));
        SUBBtn.setOnAction(e -> updateTextField("-"));
        MULBtn.setOnAction(e -> updateTextField("*"));
        DIVBtn.setOnAction(e -> updateTextField("/"));

        CLEARBtn.setOnAction(e -> calcTF.setText(""));

        EQBtn.setOnAction(event -> {
            int baseInDec = disableButtonsForBase(chosen);
            Func(calcTF.getText(), baseInDec);
        });
    }

    // Helper method to update the text field, clearing it if an error massage was displayed
    private void updateTextField(String input) {
        if (isErrorDisplayed()) {
            calcTF.setText(input);
        } else {
            calcTF.setText(calcTF.getText() + input);
        }
    }

    // Checks if the current text in calcTF is an error message all error massages start with Error
    private boolean isErrorDisplayed() {
        String currentText = calcTF.getText();
        return currentText.startsWith("Error:");
    }

    // Event handler for selecting a base from the listBox
    @FXML
    void chooseFromList(ActionEvent event) {
        String currentValue = calcTF.getText().trim();
        if (currentValue.isEmpty()) {
            prevBase = chosen;
            chosen = listBox.getSelectionModel().getSelectedItem();
            disableButtonsForBase(chosen);
            return;
        }

        if (chosen == null) {
            chosen = listBox.getSelectionModel().getSelectedItem();
        } else {
            prevBase = chosen;
            chosen = listBox.getSelectionModel().getSelectedItem();

            try {
                int oldBase = cases(prevBase);
                int newBase = cases(chosen);

                // Evaluate the current expression if it's valid
                try {
                    Legal(currentValue, oldBase);
                    int decimalValue = calc(currentValue, oldBase);

                    // Convert the decimal value to the new base
                    String newValue = Integer.toString(decimalValue, newBase).toUpperCase();

                    // Update the text field with the new value
                    calcTF.setText(newValue);
                } catch (IllegalArgumentException | ArithmeticException e) {
                    calcTF.setText("Error: invalid expression: \"\"");
                }

            } catch (NumberFormatException e) {
                calcTF.setText("Error: invalid expression: \"\"");
            }
        }

        disableButtonsForBase(chosen);
    }


    // Helper method to determine the numeric base from the chosen string
    @FXML
    int cases(String chosen) {


        if (chosen != null) {
            switch (chosen) {
                case "BIN":

                    return 2;

                case "OCT":

                    return 8;

                case "DEC":

                    return 10;

                case "HEX":

                    return 16;

                default:
                    break;
            }
        }
        return 0;
    }

    // Method to enable or disable buttons based on the selected base
    int disableButtonsForBase(String base) {
        // Enable all buttons first
        for (Button button : buttons) {
            button.setDisable(false);
        }
        switch (base) {
            case "BIN":
                for (int i = 2; i <= 15; i++) {
                    buttons[i].setDisable(true);
                }

                return 2;

            case "OCT":
                for (int i = 8; i <= 15; i++) {
                    buttons[i].setDisable(true);
                }
                return 8;

            case "DEC":
                for (int i = 10; i <= 15; i++) {
                    buttons[i].setDisable(true);
                }
                return 10;

            case "HEX":

                return 16;

            default:
                break;
        }

        return 0;
    }
    // a function that deals with the case of base=2/8/10
    //it checks that all chars are operators, and all operands are in range 0-base
    //if that wasn't the case we throw an exception and exit the program
    //it also checks the case of two operators being in a row, by using the prev flag as in indicator.
    public static void Iterate(String exp, int base) {
        int num = 0;
        char prev = ' ';
        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);

            if( Character.isLetter(ch))
            {  throw new IllegalArgumentException("Error: invalid expression: \"\"");}
            if (ch != '+' && ch != '-' && ch != '*' && ch != '/') {
                num = ch - '0';
                prev = ' ';
            }

            if (!(num < base || "+-*/".indexOf(ch) != -1)) {
                throw new IllegalArgumentException("Error: invalid expression: \"\"");
            }

            if ("+-*/".indexOf(ch) != -1) {
                if (prev != ' ') throw new IllegalArgumentException("Error: invalid expression: \"\"");
                prev = ch;
            }
        }
    }

    // a Function that gets the expression and the base: in case base was 16: it checks if all the chars in the expression are between A-F and numbers and *\+-
    //in case the expression was invalid we throw an exception and stop the program in the main function
    //in case base was 2 or 8 or 10, we jump to function iterate and deal with this case
    public static void Legal(String exp, int base) {
        char first = exp.charAt(0);
        char last = exp.charAt(exp.length() - 1);
        if ("+-/*".indexOf(first) != -1 || "+-/*".indexOf(last) != -1)
            throw new IllegalArgumentException("Error: invalid expression: \"\"");

        switch (base) {
            case 16:
                char prev = ' ';
                int num = 0;
                for (int i = 0; i < exp.length(); i++) {
                    char ch = exp.charAt(i);
                    if (ch != '+' && ch != '-' && ch != '*' && ch != '/' && ch != 'A' && ch != 'B' && ch != 'C' && ch != 'D' && ch != 'E' && ch != 'F') {
                        prev = ' ';
                        num = ch - '0';
                    }

                    if ("[A-F]".indexOf(ch) != -1) prev = ' ';

                    if (!(num < base || "+-*/".indexOf(ch) != -1 || "[A-F]".indexOf(ch) != -1)) {
                        throw new IllegalArgumentException("Error: invalid expression: \"\"");
                    }

                    if ("+-*/".indexOf(ch) != -1) {
                        if (prev != ' ') throw new IllegalArgumentException("Error: invalid expression: \"\"");
                        prev = ch;
                    }

                }
                break;

            default:
                Iterate(exp, base);
        }
    }


    // a function that gets two operands and an operator that's * or / and calculates the result
    // in case there was a division by zero, we throw an exception and exit the program
    public static int CalculateTwoElements(String left, String right, char operator) {
        switch (operator) {
            case '*':
                return Integer.parseInt(left) * Integer.parseInt(right);

            case '/':
                if (Integer.parseInt(right) == 0) {
                    throw new ArithmeticException("Error: trying to divide by 0 (evaluated: \"0\")");
                } else
                    return Integer.parseInt(left) / Integer.parseInt(right);

            default:
                return 0;
        }
    }


    //gets the str string and sums the values or subtracts them as needed.
    //it returns the right final result (res)
    public static int FinalCalculation(String[] str, int length) {
        int res = Integer.parseInt(str[0]);
        char c;
        for (int i = 1; i < length; i++) {
            c = str[i].charAt(0);
            if (c == '+')
                res += Integer.parseInt(str[i + 1]);
            else
                res -= Integer.parseInt(str[i + 1]);
            i++;
        }
        return res;
    }


    //this function is supposed to return the final correct value, given a base and an expression
    public static int calc(String exp, int base) {
        //we use split command in order to get a string with all the operands and operators being split in an array of strings. the result is saved in array parts
        String[] parts = exp.split("((?<=[-+/*])|(?=[-+/*]))");

        //the following loop is responsible for iterating over parts and converting each number from it's given base to the decimal base- in case base=10, we are not supposed to change anything
        for (int i = 0; i < parts.length; i++) {
            String str = parts[i];
            if ("+-*/".indexOf(str) == -1) {

                switch (base) {
                    case 2:
                        parts[i] = Integer.toString(Integer.parseInt(parts[i], 2));
                        break;
                    case 8:
                        parts[i] = Integer.toString(Integer.parseInt(parts[i], 8));
                        break;

                    case 16:
                        parts[i] = Integer.toString(Integer.parseInt(parts[i], 16));
                        break;
                    default:
                }
            }
        }


        //we use a new string called str that's suppose to hold the current values that are numbers if there were + or - between them
        // //and in the case of operand= * or operand =/ , we call the function CalculateTwoElements in order to calculte operand1 *OR/ opereand 2 and get the value, and save it in the right place in str.
        String[] str = new String[parts.length];
        int result = 0;
        int j = 0;
        int final_res = 0;
        for (int i = 0; i < parts.length; i++) {
            {
                char c = parts[i].charAt(0);
                if (c != '/' && c != '*') {
                    str[j++] = parts[i];

                } else {
                    result = CalculateTwoElements(str[j - 1], parts[i + 1], c);
                    str[j - 1] = String.valueOf(result);
                    i++;
                }
            }

        }

        // at this point we have in str all the numbers in decimal and operands + - only.
        // what's left to do is to sum and subtract these values as needed. that's what function FinalCalculation does
        return final_res = FinalCalculation(str, j);
    }



    private void Func(String exp, int base) {


        exp = exp.replaceAll("\\s", "");

        //a try scope for checking if the expression is legal or not: we throw an exception in case not, and stop the program
        try {
            Legal(exp, base);
        } catch (IllegalArgumentException  e) {
            //System.out.println(e.getMessage());
            calcTF.setText(e.getMessage());
            return;
            //System.exit(1);
        }

        // a try scope for calculating the expression after we found out that it's legal, we throw an exception in case there was a division by zero, and exit the program
        int res = 0;
        try {
            res = calc(exp, base);
        } catch ( ArithmeticException e) {

            calcTF.setText(e.getMessage());
            return;


        }


        String a7a = Integer.toString(res, base).toUpperCase();;

        // printing the result
        // System.out.println("The value of expression " + copy + " is : " + a7a);

        calcTF.setText(a7a);

    }



}