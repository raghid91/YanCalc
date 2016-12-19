package com.example.acer.yancalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Scientific extends AppCompatActivity implements OnClickListener
{
    private TextView calcDisplay;
    private StringBuilder concatenate;
    private StringBuilder expression;
    private double leftOperand;
    private double leftOperandParenthesis;
    private double rightOperand;
    private double memory;
    private String operator;
    private String operatorParenthesis;
    private calcOperations calc;
    private boolean equalDone;
    private int calcVariable;
    private String detectButtonText;
    private boolean checkParenthesis;
    private boolean checkParenthesisAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific);

        // Set variable of display
        calcDisplay = (TextView) findViewById(R.id.displayDigits);
        // Initialize variable for concatenation of digits
        expression = new StringBuilder();
        concatenate = new StringBuilder();
        // Initialize variables for calculation operation
        leftOperand = 0;
        leftOperandParenthesis = 0;
        rightOperand = 0;
        memory = 0;
        operator = "";
        operatorParenthesis = "";
        // Initialize calculation object
        calc = new calcOperations();
        // Initialize temporary variables
        equalDone = false;
        calcVariable = 0;
        detectButtonText = "";
        // Check to make sure not more than 1 parenthesis is open for calculation
        checkParenthesis = false;
        checkParenthesisAfter = false;

        // Start listening to all buttons of calculator
        findViewById(R.id.zero).setOnClickListener(this);
        findViewById(R.id.one).setOnClickListener(this);
        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
        findViewById(R.id.four).setOnClickListener(this);
        findViewById(R.id.five).setOnClickListener(this);
        findViewById(R.id.six).setOnClickListener(this);
        findViewById(R.id.seven).setOnClickListener(this);
        findViewById(R.id.eight).setOnClickListener(this);
        findViewById(R.id.nine).setOnClickListener(this);

        findViewById(R.id.equal).setOnClickListener(this);
        findViewById(R.id.dot).setOnClickListener(this);
        findViewById(R.id.square_root).setOnClickListener(this);

        findViewById(R.id.plus).setOnClickListener(this);
        findViewById(R.id.subtract).setOnClickListener(this);
        findViewById(R.id.multiply).setOnClickListener(this);
        findViewById(R.id.divide).setOnClickListener(this);

        findViewById(R.id.ce).setOnClickListener(this);
        findViewById(R.id.c).setOnClickListener(this);
        findViewById(R.id.mPlus).setOnClickListener(this);
        findViewById(R.id.mStore).setOnClickListener(this);
        findViewById(R.id.mClear).setOnClickListener(this);
        findViewById(R.id.mRestore).setOnClickListener(this);

        // Scientific functions
        findViewById(R.id.exponent_2).setOnClickListener(this);
        findViewById(R.id.exponent_y).setOnClickListener(this);
        findViewById(R.id.exponent_10).setOnClickListener(this);
        findViewById(R.id.mod).setOnClickListener(this);
        findViewById(R.id.pie).setOnClickListener(this);
        findViewById(R.id.open_bracket).setOnClickListener(this);
        findViewById(R.id.close_bracket).setOnClickListener(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    // Listen to buttons clicked
    public void onClick (View view)
    {
        // Listen to button clicked
        String buttonPressed = ((Button) view).getText().toString();

        /*
            1. Conditions for reacting to Clear Memory or clear actual number
        */
        if (buttonPressed.equals("C"))
        {
            reset();
            calcDisplay.setText("0");
        }

        else if (buttonPressed.equals("CE") && leftOperand == 0 || buttonPressed.equals("CE") && leftOperand == 0 && concatenate.length() == 0)
        {
            expression.setLength(0);
            expression.append(0);
            calcDisplay.setText(expression.toString());
            checkParenthesis = false;
            reset();
        }

        else if (buttonPressed.equals("CE") && expression.length() != 0 || buttonPressed.equals("CE") && !operator.isEmpty() && leftOperand != 0 && concatenate.length() != 0)
        {
            try
            {
                expression.setLength(expression.length() - concatenate.length());
                concatenate.setLength(0);
                operator = "";
                calcDisplay.setText(expression.toString());
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        /*
            2. Conditions to react when using Memory function buttons
        */

        else if (buttonPressed.equals("MC"))
        {
            memory = 0;
        }

        else if (buttonPressed.equals("M+") || buttonPressed.equals("MS"))
        {
            try
            {
                switch (buttonPressed)
                {
                    case "M+":
                        if (concatenate.length() != 0)
                        {
                            memory += Double.parseDouble(concatenate.toString());
                        }

                        else
                        {
                            // Display error message for unknown error
                            Toast.makeText(this, "Error: no number detected to store.", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "MS":
                        if (concatenate.length() != 0)
                        {
                            memory = Double.parseDouble(concatenate.toString());
                        }

                        else
                        {
                            // Display error message for unknown error
                            Toast.makeText(this, "Error: no number detected to store.", Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        memory = Double.parseDouble(concatenate.toString());
                }
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "Error: cannot add to memory as no value found!", Toast.LENGTH_LONG).show();
            }
        }

        else if (buttonPressed.equals("MR") && leftOperand == 0 && concatenate.length() == 0)
        {
            try
            {
                expression.setLength(0);
                if (memory % 1 == 0)
                {
                    calcVariable = (int) memory;
                    expression.append(calcVariable);
                    concatenate.append(calcVariable);
                }

                else
                {
                    expression.append(memory);
                    concatenate.append(memory);
                }
                calcDisplay.setText(expression.toString());
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "Error: cannot retrieve from memory as no value found!", Toast.LENGTH_LONG).show();
            }
        }

        else if (buttonPressed.equals("MR") && leftOperand == 0 && concatenate.length() != 0)
        {
            try
            {
                expression.setLength(0);
                concatenate.setLength(0);
                if (memory % 1 == 0)
                {
                    calcVariable = (int) memory;
                    expression.append(calcVariable);
                    concatenate.append(calcVariable);
                }

                else
                {
                    expression.append(memory);
                    concatenate.append(memory);
                }
                calcDisplay.setText(expression.toString());
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "Error: cannot retrieve from memory as no value found!", Toast.LENGTH_LONG).show();
            }
        }

        else if (buttonPressed.equals("MR") && leftOperand != 0 && concatenate.length() == 0)
        {
            try
            {
                if (memory % 1 == 0)
                {
                    calcVariable = (int) memory;
                    expression.append(calcVariable);
                    concatenate.append(calcVariable);
                }

                else
                {
                    expression.append(memory);
                    concatenate.append(memory);
                }
                calcDisplay.setText(expression.toString());
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "Error: cannot retrieve from memory as no value found!", Toast.LENGTH_LONG).show();
            }
        }

        /*
            3. Conditions for reacting to square, square root or 10^x function
        */

        else if (buttonPressed.equals("âˆš") && leftOperand == 0 && concatenate.length() == 0 || buttonPressed.equals("xÂ²") && leftOperand == 0 && concatenate.length() == 0 || buttonPressed.equals("10^x") && leftOperand == 0 && concatenate.length() == 0)
        {
            // Display error message if user try click on Square Root button after an operator is clicked
            Toast.makeText(this, "Error: this function must be applied on a number entered first!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.equals("âˆš") && leftOperand != 0 && concatenate.length() == 0 && !operator.isEmpty() || buttonPressed.equals("10^x") && leftOperand != 0 && concatenate.length() == 0 && !operator.isEmpty())
        {
            // Display error message if user try click on Square Root button after an operator is clicked
            Toast.makeText(this, "Error: this function cannot be applied after an operator!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.equals("âˆš") && concatenate.length() != 0 && leftOperand == 0 || buttonPressed.equals("xÂ²") && concatenate.length() != 0 && leftOperand == 0 || buttonPressed.equals("10^x") && concatenate.length() != 0 && leftOperand == 0)
        {
            try
            {
                switch (buttonPressed)
                {
                    case "âˆš":
                        expression.setLength(0);
                        expression.append(calc.squareRoot(Double.parseDouble(concatenate.toString())));
                        concatenate.setLength(0);
                        concatenate.append(expression.toString());
                        calcDisplay.setText(expression.toString());
                        break;

                    case "xÂ²":
                        expression.setLength(0);
                        expression.append(calc.square(Double.parseDouble(concatenate.toString())));
                        concatenate.setLength(0);
                        concatenate.append(expression.toString());
                        calcDisplay.setText(expression.toString());
                        break;

                    case "10^x":
                        expression.setLength(0);
                        expression.append(calc.tenPower(Double.parseDouble(concatenate.toString())));
                        concatenate.setLength(0);
                        concatenate.append(expression.toString());
                        calcDisplay.setText(expression.toString());
                        break;

                    default:
                        expression.setLength(0);
                        expression.append(calc.squareRoot(Double.parseDouble(concatenate.toString())));
                        concatenate.setLength(0);
                        concatenate.append(expression.toString());
                        calcDisplay.setText(expression.toString());
                }
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred, and cannot perform Square or Square Root operation. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        else if (buttonPressed.equals("âˆš") && concatenate.length() != 0 && leftOperand != 0 && !operator.isEmpty() || buttonPressed.equals("xÂ²") && concatenate.length() != 0 && leftOperand != 0 && !operator.isEmpty() || buttonPressed.equals("10^x") && concatenate.length() != 0 && leftOperand != 0 && !operator.isEmpty())
        {
            try
            {
                expression.setLength(0);
                if (leftOperand % 1 == 0)
                {
                    calcVariable = (int) leftOperand;
                    expression.append(calcVariable);
                }

                else
                {
                    expression.append(leftOperand);
                }

                switch (buttonPressed)
                {
                    case "âˆš":
                        expression.append(operator);
                        expression.append(calc.squareRoot(Double.parseDouble(concatenate.toString())));

                        // Storing the square root result temporarily to rightOperand variable
                        rightOperand = Double.parseDouble(calc.squareRoot(Double.parseDouble(concatenate.toString())));
                        concatenate.setLength(0);
                        concatenate.append(rightOperand);
                        // Reset rightOperand
                        rightOperand = 0;
                        break;

                    case "xÂ²":
                        expression.append(operator);
                        expression.append(calc.square(Double.parseDouble(concatenate.toString())));

                        // Storing the square result temporarily to rightOperand variable
                        rightOperand = Double.parseDouble(calc.square(Double.parseDouble(concatenate.toString())));
                        concatenate.setLength(0);
                        concatenate.append(rightOperand);
                        // Reset rightOperand
                        rightOperand = 0;
                        break;

                    case "10^x":
                        expression.append(operator);
                        expression.append(calc.tenPower(Double.parseDouble(concatenate.toString())));

                        // Storing the square result temporarily to rightOperand variable
                        rightOperand = Double.parseDouble(calc.tenPower(Double.parseDouble(concatenate.toString())));
                        concatenate.setLength(0);
                        concatenate.append(rightOperand);
                        // Reset rightOperand
                        rightOperand = 0;
                        break;

                    default:
                        expression.append(operator);
                        expression.append(calc.squareRoot(Double.parseDouble(concatenate.toString())));

                        // Storing the square root result temporarily to rightOperand variable
                        rightOperand = Double.parseDouble(calc.squareRoot(Double.parseDouble(concatenate.toString())));
                        concatenate.setLength(0);
                        concatenate.append(rightOperand);
                        // Reset rightOperand
                        rightOperand = 0;
                }
                calcDisplay.setText(expression.toString());
            }

            catch (Exception e)
            {
                // Display error message for exception caught
                Toast.makeText(this, "An error has occurred, and cannot perform Square or Square Root operation. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        /*
            4. Conditions for reacting when entering numbers and decimal numbers
        */

        else if (buttonPressed.matches(".*[.].*") && concatenate.toString().contains("."))
        {
            // Display error message if user try to enter more than 1 decimal point
            Toast.makeText(this, "Number already has a decimal!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.matches(".*[0123456789.Ï€].*"))
        {
            if (buttonPressed.equals("Ï€"))
            {
                buttonPressed = "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342";
            }

            if (equalDone && operator.isEmpty())
            {
                rightOperand = 0;
                leftOperand = 0;
                operator = "";
                concatenate.setLength(0);
                expression.setLength(0);
                equalDone = false;
            }
            concatenate.append(buttonPressed);
            expression.append(buttonPressed);
            calcDisplay.setText(expression.toString());
        }

        else if (buttonPressed.matches(".*[+x/-].*") && concatenate.length() == 0 && leftOperand == 0 && operator.isEmpty() && expression.length() == 0)
        {
            concatenate.append(0);
            expression.append(0);
            expression.append(buttonPressed);
            operator = buttonPressed;
            calcDisplay.setText(expression.toString());

        }

        else if (buttonPressed.matches(".*[+x/-].*") && concatenate.toString().equals("0") && leftOperand == 0 || buttonPressed.matches(".*[+x/-].*") && concatenate.toString().isEmpty() && leftOperand == 0)
        {
            // Display error message if first number is zero
            Toast.makeText(this, "Error: First, operands cannot be zero or two operators following each other!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.matches(".*[+x/-].*") && concatenate.length() == 0 && leftOperand != 0 && !operator.isEmpty())
        {
            // Display error message if first number is zero
            Toast.makeText(this, "Error: cannot apply operator after another operator!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.matches(".*[+x/-].*") && leftOperand == 0)
        {
            try
            {
                equalDone = false;
                leftOperand = Double.parseDouble(concatenate.toString());
                concatenate.setLength(0);
                operator = buttonPressed;
                expression.append(buttonPressed);
                calcDisplay.setText(expression.toString());
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        else if (buttonPressed.matches(".*[+x/-].*") && leftOperand != 0)
        {
            try
            {
                rightOperand = Double.parseDouble(concatenate.toString());
                calc.performOperation(leftOperand, rightOperand, operator);

                concatenate.setLength(0);
                rightOperand = 0;

                operator = buttonPressed;
                expression.append(buttonPressed);

                leftOperand = Double.parseDouble(calc.getResult());
                calcDisplay.setText(expression.toString());
                calc.reset();
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        /*
            5. Scientific functions
        */

        // First number to the exponent of the next number
        else if (buttonPressed.equals("X^y") && concatenate.toString().equals("0") && leftOperand == 0 || buttonPressed.equals("X^y") && concatenate.toString().isEmpty() && leftOperand == 0)
        {
            // Display error message if first number is zero
            Toast.makeText(this, "First or both operands cannot be zero!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.equals("X^y") && concatenate.length() == 0 && leftOperand != 0 && !operator.isEmpty())
        {
            // Display error message if first number is zero
            Toast.makeText(this, "Error: cannot apply operator after another operator!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.equals("X^y") && leftOperand == 0)
        {
            try
            {
                detectButtonText = "^";
                leftOperand = Double.parseDouble(concatenate.toString());
                concatenate.setLength(0);
                operator = detectButtonText;
                expression.append(detectButtonText);
                calcDisplay.setText(expression.toString());
                detectButtonText = "";
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        else if (buttonPressed.equals("X^y") && leftOperand != 0)
        {
            try
            {
                detectButtonText = "^";
                rightOperand = Double.parseDouble(concatenate.toString());
                calc.performOperation(leftOperand, rightOperand, operator);

                concatenate.setLength(0);
                rightOperand = 0;

                operator = detectButtonText;
                expression.append(detectButtonText);

                leftOperand = Double.parseDouble(calc.getResult());
                calcDisplay.setText(expression.toString());
                calc.reset();
                detectButtonText = "";
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        // First number MOD second number
        else if (buttonPressed.equals("MOD") && concatenate.toString().equals("0") && leftOperand == 0 || buttonPressed.equals("MOD") && concatenate.toString().isEmpty() && leftOperand == 0)
        {
            // Display error message if first number is zero
            Toast.makeText(this, "First or both operands cannot be zero!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.equals("MOD") && concatenate.length() == 0 && leftOperand != 0 && !operator.isEmpty())
        {
            // Display error message if first number is zero
            Toast.makeText(this, "Error: cannot apply operator after another operator!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.equals("MOD") && leftOperand == 0)
        {
            try
            {
                detectButtonText = "%";
                leftOperand = Double.parseDouble(concatenate.toString());
                concatenate.setLength(0);
                operator = detectButtonText;
                expression.append(detectButtonText);
                calcDisplay.setText(expression.toString());
                detectButtonText = "";
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        else if (buttonPressed.equals("MOD") && leftOperand != 0)
        {
            try
            {
                detectButtonText = "%";
                rightOperand = Double.parseDouble(concatenate.toString());
                calc.performOperation(leftOperand, rightOperand, operator);

                concatenate.setLength(0);
                rightOperand = 0;

                operator = detectButtonText;
                expression.append(detectButtonText);

                leftOperand = Double.parseDouble(calc.getResult());
                calcDisplay.setText(expression.toString());
                calc.reset();
                detectButtonText = "";
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        /*
        ****************************************************************************************************************
        *   This is an important function in the scientific mode.                                                      *
        *   This make sure, for simplicity, that calculation within parenthesis is done before exterior calculation.   *
        *   Also, there is no nested parenthesis, as otherwise, this will be too complicated to check.                 *
        ****************************************************************************************************************
        */

        else if (buttonPressed.equals("(") && !checkParenthesis && !operator.isEmpty() && concatenate.length() == 0)
        {
            try
            {
                checkParenthesisAfter = true;
                checkParenthesis = true;
                operatorParenthesis = operator;
                leftOperandParenthesis = leftOperand;
                calc.reset();
                leftOperand = 0;
                operator = "";
                concatenate.setLength(0);

                expression.append(buttonPressed);
                calcDisplay.setText(expression.toString());
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        else if (buttonPressed.equals(")") && checkParenthesisAfter)
        {
            try
            {
                checkParenthesisAfter = false;
                checkParenthesis = false;
                rightOperand = Double.parseDouble(concatenate.toString());
                calc.performOperation(leftOperand, rightOperand, operator);

                concatenate.setLength(0);
                concatenate.append(calc.getResult());
                rightOperand = 0;

                operator = operatorParenthesis;
                leftOperand = leftOperandParenthesis;
                calc.reset();

                expression.append(buttonPressed);
                calcDisplay.setText(expression.toString());
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        else if (buttonPressed.equals("(") && !checkParenthesis && concatenate.length() != 0 && operator.isEmpty())
        {
            // Display error message for unknown error
            Toast.makeText(this, "Error: apply an operator before opening parenthesis!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.equals("(") && !checkParenthesis && expression.length() == 0 || buttonPressed.equals("(") && !checkParenthesis && expression.length() == 0 && concatenate.length() == 0 && leftOperand == 0)
        {
            try
            {
                expression.append(buttonPressed);
                calcDisplay.setText(expression.toString());
                checkParenthesis = true;
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        else if (buttonPressed.equals("(") && checkParenthesis)
        {
            // Display error message for unknown error
            Toast.makeText(this, "Error: a parenthesis is already open for calculation!", Toast.LENGTH_LONG).show();
        }

        else if (buttonPressed.equals(")") && checkParenthesis)
        {
            try
            {
                expression.append(buttonPressed);
                calcDisplay.setText(expression.toString());
                checkParenthesis = false;
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                errorException();
            }
        }

        else if (buttonPressed.equals(")") && !checkParenthesis)
        {
            // Display error message for unknown error
            Toast.makeText(this, "Error: a parenthesis is not open!", Toast.LENGTH_LONG).show();
        }

        /*
            6. Conditions for reacting to trigger of calculation result
        */

        else if (buttonPressed.matches(".*[=].*"))
        {
            try
            {

                if (concatenate.length() == 0)
                {
                    concatenate.append(0);
                }

                rightOperand = Double.parseDouble(concatenate.toString());
                calc.performOperation(leftOperand, rightOperand, operator);

                concatenate.setLength(0);
                expression.setLength(0);
                expression.append(calc.getResult());
                concatenate.append(calc.getResult());
                calcDisplay.setText(expression);

                operator = "";
                leftOperand = 0;
                rightOperand = 0;
                equalDone = true;
                calc.reset();
            }

            catch (Exception e)
            {
                // Display error message for unknown error
                Toast.makeText(this, "An error has occurred. Calculator reset.", Toast.LENGTH_LONG).show();
                // Reset calculator
                errorException();
            }
        }
    }

    // Actions when an Exception is caught
    public void errorException()
    {
        // Reset calculator
        expression.setLength(0);
        expression.append(0);
        calcDisplay.setText(expression.toString());
        reset();
    }

    // Reset Calculator
    public void reset()
    {
        rightOperand = 0;
        leftOperand = 0;
        leftOperandParenthesis = 0;
        operator = "";
        operatorParenthesis = "";
        checkParenthesis = false;
        checkParenthesisAfter = false;
        concatenate.setLength(0);
        expression.setLength(0);
        equalDone = false;
        calcVariable = 0;
        detectButtonText = "";
        calc.reset();
    }

    // Inflate menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.the_menu2, menu);
        return true;
    }

    // Initialize options in menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle items selection
        switch (item.getItemId())
        {
            // Switch to scientific mode
            case R.id.action_standard:
                Intent switchMode = new Intent(Scientific.this, Standard.class);
                startActivity(switchMode);
                this.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
                return true;
            // Help screen for standard mode
            case R.id.action_help2:
                Intent askHelp = new Intent(Scientific.this, showHelp.class);
                askHelp.putExtra("mode", 2); // Help to detect if to display standard help text or scientific text
                startActivity(askHelp);
                this.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Method to switch to standard mode from button
    public void goToStandard(View view)
    {
        Intent switchMode = new Intent(Scientific.this, Standard.class);
        startActivity(switchMode);
        this.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
    }

    // Method to switch to help mode from button
    public void goToHelp(View view)
    {
        Intent askHelp = new Intent(Scientific.this, showHelp.class);
        askHelp.putExtra("mode", 2); // Help to detect if to display standard help text or scientific text
        startActivity(askHelp);
        this.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
    }
}