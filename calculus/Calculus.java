package calculus;

import java.util.Stack;

public class Calculus {
    /**
     * Checks if x in the epsilon neighbourhood of mid
     *
     * @param mid the middle of the mathematical neighbourhood
     * @param e epsilon, radius of the neighbourhood
     * @param x examined number
     * @return is x in the epsilon neighbourhood of mid
     */
    public static boolean isInNeighbourhood(double mid, double e, double x){
        return (x > mid - e && x < mid + e);
    }

    public static double max(double a, double b){
        return a>b ? a :b;
    }
    public static double min(double a, double b){
        return a<b ? a :b;
    }
    public static double abs(double x){
        return x>0 ? x: -x;
    }

    public static double sqrt(double n){
        if (n<0){
            return -1;
        }
        // Max and min are used to take into account numbers less than 1
        double lo = min(1, n), hi = max(1, n), mid = 0;

        // Update the bounds to be off the target by a factor of 10
        while(100 * lo * lo < n) lo *= 10;
        while(0.01 * hi * hi > n) hi *= 0.1;

        for(int i = 0 ; i < 100 ; i++){
            mid = (lo+hi)/2;
            if(mid*mid == n) return mid;
            if(mid*mid > n) hi = mid;
            else lo = mid;
        }
        return mid;
    }


    public static String sqrtI(double n) {
        if (n > 0){
            return sqrt(n) + "";
        }
        return sqrt(-n) + "i";
    }
    public static double valOfFunc(String function, double x) {
        // Replace 'x' in the function string with the actual value of x
        String expression = function.replaceAll("x", String.valueOf(x));

        // Parse the expression
        double result = evaluateExpression(expression);

        return result;
    }
    private static double evaluateExpression(String expression) {
        // Split the expression into tokens
        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");

        // Stack to hold operands
        Stack<Double> operandStack = new Stack<>();

        // Stack to hold operators
        Stack<Character> operatorStack = new Stack<>();

        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;

            char firstChar = token.charAt(0);

            if (Character.isDigit(firstChar)) {
                // If token is a number, push it onto operand stack
                operandStack.push(Double.parseDouble(token));
            } else {
                // If token is an operator
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(firstChar)) {
                    // Perform calculations until precedence of current operator is greater than the precedence of the operator on top of stack
                    performOperation(operandStack, operatorStack);
                }
                operatorStack.push(firstChar); // Push the current operator onto operator stack
            }
        }

        // Evaluate remaining operations in the stacks
        while (!operatorStack.isEmpty()) {
            performOperation(operandStack, operatorStack);
        }

        // The result should be the only item left on the operand stack
        return operandStack.pop();
    }

    private static int precedence(char op) {
        // Define operator precedence
        if (op == '+' || op == '-') {
            return 1;
        } else if (op == '*' || op == '/') {
            return 2;
        } else {
            return 0;
        }
    }

    private static void performOperation(Stack<Double> operandStack, Stack<Character> operatorStack) {
        char operator = operatorStack.pop();
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        double result = 0;

        // Perform the operation based on the operator
        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
        }
        operandStack.push(result); // Push the result onto the operand stack
    }
}
