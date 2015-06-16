package com.vash.calculator;
import java.util.Stack;
public class TranslationInScr {
    public static String inPolishNotation(String expression) {
        expression = "(" + expression;
        expression += ")";
        String rezNotation = "";
        Stack <Character>stack = new Stack<>() ;
        Stack <Character>outString = new Stack<>();
        boolean signFlag = true;
        for (int i = 0; i < expression.length(); i++) {
            if((expression.charAt(i) == '-' || expression.charAt(i) == '_') &&
                    (expression.charAt(i+1) == '-' || expression.charAt(i+1) == '_')){
                expression = expression.substring(0,i)+  '+'  + expression.substring(i+2,expression.length());
            }
            if ((expression.charAt(i) == '+') || (expression.charAt(i) == '-')
                    || (expression.charAt(i) == '/') || (expression.charAt(i) == '*')
                    || (expression.charAt(i) == '^') || (expression.charAt(i) == '_') || (expression.charAt(i) =='$')) {
                if (stack.size() == 0) {
                    if(signFlag && expression.charAt(i)=='-') {
                        stack.push('_');
                    }else if (signFlag && expression.charAt(i) == '+'){
                        stack.push('$');
                    }else{
                        stack.push(expression.charAt(i));
                    }
                } else if (priority(expression.charAt(i)) > priority(String
                        .valueOf(stack.peek()).charAt(0))) {
                    if(signFlag && expression.charAt(i)=='-') {
                        stack.push('_');
                    }else if (signFlag && expression.charAt(i) == '+'){
                        stack.push('$');
                    }else{
                        stack.push(expression.charAt(i));
                    }
                } else {
                    while ((stack.size() != 0)
                            && (priority(String.valueOf(stack.peek()).charAt(0)) >= priority(expression
                            .charAt(i)))) {
                        outString.push(stack.pop());
                        outString.push(' ');
                    }
                    if(signFlag && expression.charAt(i)=='-') {
                        stack.push('_');
                    }else if (signFlag && expression.charAt(i) == '+'){
                        stack.push('$');
                    }else{
                        stack.push(expression.charAt(i));
                    }
                }
                signFlag = true;
            } else if (expression.charAt(i) != '(' && expression.charAt(i) != ')') {
                    signFlag = false;
                    outString.push(expression.charAt(i));
                if (i < expression.length() - 1 && !Character.isDigit(expression.charAt(i + 1)) && expression.charAt(i + 1) != '.') {
                    outString.push(' ');
                }
            }
            if (expression.charAt(i) == ')') {
                signFlag=false;
                while (String.valueOf(stack.peek()).charAt(0) != '(') {
                    outString.push(stack.pop());
                        outString.push(' ');
                }
                stack.pop();
            }
            if (expression.charAt(i) == '(') {
                signFlag = true;
                stack.push('(');
            }
        }
        for (int j = 0; j < outString.size(); j++) {
                rezNotation = rezNotation + String.valueOf(outString.get(j));
            }
        for (int k = 0; k < rezNotation.length(); k++) {
            if (k>5 && ((rezNotation.charAt(k)=='t' && rezNotation.charAt(k-2)=='r' &&
                    rezNotation.charAt(k-4)=='q' && rezNotation.charAt(k-6)=='s')||
                    (rezNotation.charAt(k)=='t' && rezNotation.charAt(k-2)=='c' &&
                            rezNotation.charAt(k-4)=='a' && rezNotation.charAt(k-6)=='f')||
                    (rezNotation.charAt(k)=='n' && rezNotation.charAt(k-2)=='i' &&
                            rezNotation.charAt(k-4)=='s' && rezNotation.charAt(k-6)=='a')||
                    (rezNotation.charAt(k)=='s' && rezNotation.charAt(k-2)=='o' &&
                            rezNotation.charAt(k-4)=='c' && rezNotation.charAt(k-6)=='a')||
                    (rezNotation.charAt(k)=='g' && rezNotation.charAt(k-2)=='t' &&
                            rezNotation.charAt(k-4)=='c' && rezNotation.charAt(k-6)=='a'))){
                rezNotation = rezNotation.substring(0,k-5) + rezNotation.substring(k-4,k-3) +
                        rezNotation.substring(k-2,k-1) +
                        rezNotation.substring(k, rezNotation.length());
            } else if (k>3 && ((rezNotation.charAt(k)=='n' && rezNotation.charAt(k-2)=='i' &&
                    rezNotation.charAt(k-4)=='s')||(rezNotation.charAt(k)=='s' &&
                    rezNotation.charAt(k-2)=='o' && rezNotation.charAt(k-4)=='c') ||
                    (rezNotation.charAt(k)=='g' && rezNotation.charAt(k-2)=='t' &&
                    rezNotation.charAt(k-4)=='c') || (rezNotation.charAt(k)=='g' &&
                    rezNotation.charAt(k-2)=='o' && rezNotation.charAt(k-4)=='l') ||
                    (rezNotation.charAt(k)=='g' && rezNotation.charAt(k-2)=='t'
                            && rezNotation.charAt(k-4)=='a'))){
                rezNotation = rezNotation.substring(0,k-3) + rezNotation.substring(k-2,k-1)
                        + rezNotation.substring(k, rezNotation.length());
            } else if (k>1 && rezNotation.charAt(k)=='g' && rezNotation.charAt(k-2)=='t') {
                rezNotation = rezNotation.substring(0,k-1) +
                        rezNotation.substring(k, rezNotation.length());
            }
        }
        return rezNotation;
    }

    private static int priority(final char operator) {
        switch (operator) {
            case '_':
                return 5;
            case '$':
                return 5;
            case '^':
                return 4;
            case '*':
                return 3;
            case '/':
                return 3;
            case '-':
                return 2;
            case '+':
                return 2;
            case '(':
                return 1;
        }
        return 0;
    }
}