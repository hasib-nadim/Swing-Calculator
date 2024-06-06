package org.assingment;


public class CalculationModel {
    private String expression = null;

    private CalculationModel(String expression) {
        this.expression = expression;
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String numberFormBeginning(String exp) {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            if (this.isNumber(String.valueOf(exp.charAt(i))) || exp.charAt(i) == '.') {
                num.append(exp.charAt(i));
            } else {
                break;
            }
        }
        return num.toString();
    }

    private String numberFormEnd(String exp) {
        StringBuilder num = new StringBuilder();
        for (int i = exp.length() - 1; i >= 0; i--) {
            if (this.isNumber(String.valueOf(exp.charAt(i))) || exp.charAt(i) == '.') {
                num.append(exp.charAt(i));
            } else {
                break;
            }
        }
        return num.reverse().toString();
    }

    private String evaluateSqrt() {
        StringBuilder cloneExp = new StringBuilder();
        double val = 0;
        for (int i = 0; i < this.expression.length(); i++) {
            if (this.expression.charAt(i) == '√') {
                String strNum = this.numberFormBeginning(this.expression.substring(i + 1));
                val = Math.sqrt(Double.parseDouble(strNum));
                cloneExp.append(val);
                i += strNum.length();
            } else {
                cloneExp.append(this.expression.charAt(i));
            }
        }
        return cloneExp.toString();
    }

    private String evaluateMod() {
        StringBuilder cloneExp = new StringBuilder();
        double val = 0;
        for (int i = 0; i < this.expression.length(); i++) {
            if (this.expression.charAt(i) == '%') {
                String left = this.numberFormEnd(this.expression.substring(0, i));
                String right = this.numberFormBeginning(this.expression.substring(i + 1));
                // mod in java

                val = ((int) Double.parseDouble(left) % (int) Double.parseDouble(right));

                // remove left form cloneExp
                cloneExp.delete(cloneExp.length() - left.length(), cloneExp.length());
                cloneExp.append(val);
                i += right.length() + left.length();
            } else {
                cloneExp.append(this.expression.charAt(i));
            }
        }

        return cloneExp.toString();
    }

    private String evaluateMultiplication() {
        StringBuilder cloneExp = new StringBuilder();
        double val = 0;
        int i;
        for (i = 0; i < this.expression.length(); i++) {
            if (this.expression.charAt(i) == '*') {
                String left = this.numberFormEnd(this.expression.substring(0, i));
                String right = this.numberFormBeginning(this.expression.substring(i + 1));
                // multiplication in java
                val = Double.parseDouble(left) * Double.parseDouble(right);
                // remove left form cloneExp
                cloneExp.delete(cloneExp.length() - left.length(), cloneExp.length());
                cloneExp.append(val);
                i += right.length() + left.length();
                break;
            } else {
                cloneExp.append(this.expression.charAt(i));
            }
        }
        if (i < this.expression.length())
            cloneExp.append(this.expression.substring(i));

        return cloneExp.toString();
    }

    private String evaluateDivision() {
        StringBuilder cloneExp = new StringBuilder();
        double val = 0;
        int i;
        for (i = 0; i < this.expression.length(); i++) {
            if (this.expression.charAt(i) == '/') {
                String left = this.numberFormEnd(this.expression.substring(0, i));
                String right = this.numberFormBeginning(this.expression.substring(i + 1));
                // division in java
                if (Double.parseDouble(right) == 0)
                    throw new ArithmeticException("Can't divide by zero");

                val = Double.parseDouble(left) / Double.parseDouble(right);
                // remove left form cloneExp
                cloneExp.delete(cloneExp.length() - left.length(), cloneExp.length());
                cloneExp.append(val);
                i += right.length() + left.length();
                break;
            } else {
                cloneExp.append(this.expression.charAt(i));
            }
        }
        if (i < this.expression.length())
            cloneExp.append(this.expression.substring(i));

        return cloneExp.toString();
    }

    private String evaluateAddition() {
        StringBuilder cloneExp = new StringBuilder();
        double val = 0;
        int i;
        for (i = 0; i < this.expression.length(); i++) {
            if (this.expression.charAt(i) == '+') {
                String left = this.numberFormEnd(this.expression.substring(0, i));
                String right = this.numberFormBeginning(this.expression.substring(i + 1));
                // addition in java
                val = Double.parseDouble(left) + Double.parseDouble(right);
                // remove left form cloneExp
                cloneExp.delete(cloneExp.length() - left.length(), cloneExp.length());
                cloneExp.append(val);
                i += right.length() + left.length();
                break;
            } else {
                cloneExp.append(this.expression.charAt(i));
            }
        }
        if (i < this.expression.length())
            cloneExp.append(this.expression.substring(i));

        return cloneExp.toString();
    }

    private String evaluateSubtraction() {
        StringBuilder cloneExp = new StringBuilder();
        double val = 0;
        int i;
        for (i = 0; i < this.expression.length(); i++) {
            if (this.expression.charAt(i) == '-') {
                String left = this.numberFormEnd(this.expression.substring(0, i));
                String right = this.numberFormBeginning(this.expression.substring(i + 1));
                // subtraction in java
                val = Double.parseDouble(left) - Double.parseDouble(right);
                // remove left form cloneExp
                cloneExp.delete(cloneExp.length() - left.length(), cloneExp.length());
                cloneExp.append(val);
                i += right.length() + left.length();
                break;
            } else {
                cloneExp.append(this.expression.charAt(i));
            }
        }
        if (i < this.expression.length())
            cloneExp.append(this.expression.substring(i));

        return cloneExp.toString();
    }

    private double getResult() {
        this.expression = evaluateSqrt();
        this.expression = evaluateMod();
        while (this.expression.contains("/")) {
            this.expression = evaluateDivision();
        }
        while (this.expression.contains("*")) {
            this.expression = evaluateMultiplication();
        }
        while (this.expression.contains("+")) {
            this.expression = evaluateAddition();
        }
        while (this.expression.contains("-") && this.expression.indexOf('-') != 0) {
            this.expression = evaluateSubtraction();
        }

        try {
            return Double.parseDouble(this.expression);
        } catch (NumberFormatException e) {
            throw new ArithmeticException("Invalid expression");
        }

    }

    public static double evaluate(String expression) throws Exception {

        CalculationModel cm = new CalculationModel(expression);
        return cm.getResult();
    }
}
