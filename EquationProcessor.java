package org.example;

import java.util.*;

public class EquationProcessor {
    // The blueprint of EquationProcessor that stores different components of given equations
    private Stack<Double> operands; // Stores numbers given in equations
    private Stack<Character> operators; // Stores operators +-/* and brackets ()
    public Map<Character, Double> variables;
    // Stores a character and their associated number.
    // For example, x + 2 where x = 5 then it would be stored in variables as ('x', 5.0)
    // why 5.0 because we store the numbers as doubles.

    public EquationProcessor() {
        operands = new Stack<>();
        operators = new Stack<>();
        variables = new HashMap<>();
    }

    // The real work starts from here. Asks the user for input, sends it to evaluateEquation
    // then spits back the result.
    public static void main(String[] args) {
        EquationProcessor processor = new EquationProcessor();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter an equation: ");
        String equation = scanner.nextLine(); // input

        try {
            double result = processor.evaluateEquation(equation);
            System.out.println("Result: " + result);
        } catch (Exception e) { // If an exception occurs, show error message for a graceful crash
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

    // evaluateEquation takes the given equation by the user and sends it to parseEquation for
    // starters which breaks down the equations into numbers, operators, and variables where each
    // item is pushed to its appropriate place in the Stacks or HashMap.
    public double evaluateEquation(String equation) throws Exception {
        parseEquation(equation);
        return calculateResult();
    }

    private void parseEquation(String equation) throws Exception {
        for (int i = 0; i < equation.length(); i++) {
            char ch = equation.charAt(i); // This is specific for characters and not numbers

            if (Character.isDigit(ch)) {
                // StringBuilder number is used to append the digits and specifically if it's a decimal
                StringBuilder number = new StringBuilder();
                // The while loop checks whether i is less than the equations length
                // and whether the current element is a digit or a '.'
                while (i < equation.length() && (Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.')) {
                    number.append(equation.charAt(i++));
                }
                i--; // Decrements so not to skip the next element in the equation
                // The below pushes the appended digit to operands and ensures it's a double
                operands.push(Double.parseDouble(number.toString()));
            } else if (Character.isLetter(ch)) {
                if (!variables.containsKey(ch)) { // Checks if the character is in the HashMap
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter value for " + ch + ": ");
                    double value = scanner.nextDouble();
                    // The below inserts or updates the HashMap with the character and digit
                    // Associates the character with the digit
                    variables.put(ch, value);
                }
                operands.push(variables.get(ch)); // pushes the digit associated with ch to operands
            } else if (isOperator(ch)) { // if +, -, *, /
                // The while loop runs if the operators stack is not empty
                // and the top operator's priority is higher than or equal ch. * and / are highest
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    processOperator();
                }
                operators.push(ch);
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') { // while not '(' but the top is +,-,*,or /
                    // Operations inside of parentheses are processed first as priority in the entire equation
                    processOperator();
                }
                if (operators.isEmpty() || operators.peek() != '(') {
                    throw new Exception("Mismatched parentheses");
                }
                operators.pop(); // Remove the opening bracket '(' from the stack
            } else {
                throw new Exception("Invalid character in equation: " + ch);
            }

        }
        while (!operators.isEmpty()) {
            // Ensures that all remaining operations in the Stack are processed
            processOperator();
        }
    }

    private boolean isOperator(char ch) { // Determines if ch is a valid operator
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private int precedence(char operator) { // Determines the operator priority
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    // processOperator deals directly with the operands stack which already contains data
    private void processOperator() throws Exception {
        if (operands.size() < 2) {
            throw new Exception("Insufficient operands");
        }

        char operator = operators.pop();
        // b popping before a to maintain order of operations
        // to keep the bigger number on the right for division and subtraction
        double b = operands.pop(); // b should be bigger than a
        double a = operands.pop();
        double result = 0.0;

        switch (operator) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                if (b == 0) {
                    throw new Exception("Division by zero");
                }
                result = a / b;
                break;
        }
        operands.push(result);
    }

    private double calculateResult() throws Exception {
        if (operands.size() != 1) { // There should be the final calculated number of equation
            throw new Exception("Invalid equation");
        }
        return operands.pop();
    }
}