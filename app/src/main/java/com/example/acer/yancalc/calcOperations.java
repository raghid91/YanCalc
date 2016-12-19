package com.example.acer.yancalc;

/**
 * Created by Acer on 12/18/2016.
 */

public class calcOperations {
    private double leftOperand;
    private double rightOperand;
    private double result;

    // Initialize Default Constructor
    public calcOperations()
    {
        leftOperand = 0.00;
        rightOperand = 0.00;
        result = 0.00;
    }

    public void performOperation(Double a, Double b, String s)
    {
        switch (s)
        {
            case "+":
                add(a,b);
                break;
            case "-":
                subtract(a,b);
                break;
            case "x":
                multiply(a,b);
                break;
            case "/":
                divide(a,b);
                break;
            case "^":
                exponent(a,b);
                break;
            case "%":
                modulo(a,b);
                break;
            default:
                add(a,b);
        }
    }

    // ADD operation
    private void add(double a, double b)
    {
        leftOperand = a;
        rightOperand = b;
        result = leftOperand + rightOperand;
    }

    // SUBTRACT operation
    private void subtract(double a, double b)
    {
        leftOperand = a;
        rightOperand = b;
        result = leftOperand - rightOperand;
    }

    // MULTIPLY operation
    private void multiply(double a, double b)
    {
        leftOperand = a;
        rightOperand = b;
        result = leftOperand * rightOperand;
    }

    // DIVIDE operation
    private void divide(double a, double b)
    {
        leftOperand = a;
        rightOperand = b;
        result = leftOperand / rightOperand;
    }

    // EXPONENT operation
    private void exponent(double a, double b)
    {
        leftOperand = a;
        rightOperand = b;
        result = Math.pow(leftOperand, rightOperand);
    }

    // MODULO operation
    private void modulo(double a, double b)
    {
        leftOperand = a;
        rightOperand = b;
        result = leftOperand % rightOperand;
    }

    // SQUARE ROOT operation
    public String squareRoot(double a)
    {
        result = Math.sqrt(a);

        if (result % 1 == 0)
        {
            return String.format("%d", (int) result);
        }

        else
        {
            return String.format("%s", result);
        }
    }

    // SQUARE operation
    public String square(double a)
    {
        result = Math.pow(a, 2);

        if (result % 1 == 0)
        {
            return String.format("%d", (int) result);
        }

        else
        {
            return String.format("%s", result);
        }
    }

    // TEN TO POWER operation
    public String tenPower(double a)
    {
        result = Math.pow(10, a);

        if (result % 1 == 0)
        {
            return String.format("%d", (int) result);
        }

        else
        {
            return String.format("%s", result);
        }
    }

    // Return result
    public String getResult()
    {
        if (result % 1 == 0)
        {
            return String.format("%d", (int) result);
        }

        else
        {
            return String.format("%s", result);
        }
    }

    // Reset object to 0
    public void reset()
    {
        leftOperand = 0.00;
        rightOperand = 0.00;
        result = 0.00;
    }
}
