import java.util.ArrayList;
import java.util.Arrays;

public class Notation {
    /**
     * Converts an infix string to a postfix string
     * @param inFix the infix string to be converted
     * @return the converted postfix string
     * @throws InvalidNotationFormatException if the infix string is formatted incorrectly
     */
    public static String convertInfixToPostfix(String inFix) throws InvalidNotationFormatException {
        NotationStack<Character> operatorStack = new NotationStack<>(inFix.length());
        NotationQueue<Character> postFix = new NotationQueue<>(inFix.length());

        try {
            for (char nextChar: inFix.toCharArray()) {
                if (nextChar >= '0' && nextChar <= '9') {
                    postFix.enqueue(nextChar);
                } else if (nextChar == '(') {
                    operatorStack.push(nextChar);
                } else if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') {
                    while (!operatorStack.isEmpty() && operatorPrecedence(nextChar, operatorStack.top())) {
                        postFix.enqueue(operatorStack.pop());
                    }
                    operatorStack.push(nextChar);
                } else if (nextChar == ')') {
                    char topOperator = operatorStack.pop();
                    while (topOperator != '(') {
                        if (operatorStack.isEmpty()) {
                            throw new InvalidNotationFormatException();
                        }
                        postFix.enqueue(topOperator);
                        topOperator = operatorStack.pop();
                    }
                } // space characters and letters are ignored
            }
            while (!operatorStack.isEmpty()) {
                postFix.enqueue(operatorStack.pop());
            }
        } catch (StackOverflowException | StackUnderflowException | QueueOverflowException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return postFix.toString();

    }

    /**
     * Converts the postfix string to infix format
     * @param postFix the postfix string to be converted
     * @return the converted infix string
     * @throws InvalidNotationFormatException if the postfix string is formatted incorrectly
     */
    public static String convertPostfixToInfix(String postFix) throws InvalidNotationFormatException {
        NotationStack<String> inFix = new NotationStack<>(postFix.length());

        try {
            for (char nextChar : postFix.toCharArray()) {
                if (nextChar >= '0' && nextChar <= '9') {
                    inFix.push(String.valueOf(nextChar));
                } else if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') {
                    if (inFix.size() < 2) {
                        throw new InvalidNotationFormatException();
                    }
                    String val1 = inFix.pop();
                    String val2 = inFix.pop();
                    String expression = "(" + val2 + nextChar + val1 + ")";
                    inFix.push(expression);
                }
            }
        } catch (StackOverflowException | StackUnderflowException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        if (inFix.size() == 1) {
            return inFix.toString();
        }
        throw new InvalidNotationFormatException();
    }

    /**
     * Evaluates a postfix expression
     * @param postFix the postfix expression to be evaluated
     * @return the result of the evaluation, as a double
     * @throws InvalidNotationFormatException if the postfix expression is formatted incorrectly
     */
    public static double evaluatePostfixExpression(String postFix) throws InvalidNotationFormatException {
        NotationStack<Double> evalResult = new NotationStack<>(postFix.length());
        double finalResult = 0;
        try {
            for (char nextChar : postFix.toCharArray()) {
                if ((nextChar >= '0' && nextChar <= '9')) {
                    evalResult.push(Double.parseDouble(String.valueOf(nextChar)));
                } else if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') {
                    if (evalResult.size() < 2) {
                        throw new InvalidNotationFormatException();
                    }
                    double val1 = evalResult.pop();
                    double val2 = evalResult.pop();
                    double result = calculate(val2, val1, nextChar);
                    evalResult.push(result);
                }
            }
            if (evalResult.size() == 1) {
                finalResult = evalResult.pop();
            } else {
                throw new InvalidNotationFormatException();
            }
        } catch (StackOverflowException | StackUnderflowException e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
        return finalResult;
    }

    /**
     * Used in converting infix to postfix, this method determines operator precedence using the order of operations
     * @param nextChar the next character in the postFix string
     * @param top the character on top of the current operator stack
     * @return true if top is greater than nextChar, false otherwise
     */
    private static boolean operatorPrecedence(char nextChar, char top) {
        // since the various operations are already in the correct order except for the * in ascii code,
        // all this needs to do is convert * to some character above + and - and compare the values of the characters
        if (nextChar == '*') {
            nextChar += 6;
        } if (top == '*') {
            top += 6;
        }
        return nextChar <= top;
    }

    /**
     * This method performs the basic arithmetic to convert two numbers and a character into a single number
     * @param left the number on the left of the arithmetic expression
     * @param right the number on the right of the arithmetic expression
     * @param operator the operation to be performed (will only be +, -, *, or /)
     * @return the result of the operation
     */
    private static double calculate(double left, double right, char operator) {
        return switch (operator) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> left / right;
            default -> 0;
        };
    }

}
