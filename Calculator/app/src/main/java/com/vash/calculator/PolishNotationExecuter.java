package com.vash.calculator;
import java.util.ArrayList;
import java.util.Arrays;

public class PolishNotationExecuter {
    public static double exectute(String expression)throws Exception{
        ArrayList<String> splited = new ArrayList<>(Arrays.asList(expression.split(" ")));

        for(int i=0;i<splited.size();i++){
            if(splited.get(i).length() == 1){
               char sym = splited.get(i).charAt(0);
               if(!Character.isDigit(sym)){
                   execOneOper(i,splited);
                   i=0;
               }
            }
        }
        return Double.parseDouble(splited.get(0));
    }


    private static void remover(int index,ArrayList<String> array){
        array.remove(index);
        array.remove(index - 1);
        array.remove(index - 2);
    }

    private static void execOneOper(int index,ArrayList<String> array) throws Exception{
        double first = 0;
        if(array.size() > 2 && index >= 2) {
            first = Double.parseDouble(array.get(index - 2));
        }
        double second = Double.parseDouble(array.get(index-1));
        switch (array.get(index)){
            case "+":
                remover(index, array);
                array.add(index - 2, Double.toString(first + second));
                break;
            case "-":
                remover(index, array);
                array.add(index-2,Double.toString(first-second));
                break;
            case "*":
                remover(index, array);
                array.add(index-2,Double.toString(first*second));
                break;
            case "/":
                remover(index, array);
                array.add(index-2,Double.toString(first/second));
                break;
            case "^":
                remover(index, array);
                array.add(index-2,Double.toString(Math.pow(first, second)));
                break;
            case "_":
                array.remove(index);
                array.remove(index - 1);
                array.add(index-1,Double.toString(-second));
                break;
            case "$":
                array.remove(index);
                array.remove(index - 1);
                array.add(index-1,Double.toString(second));
                break;
            default:
                throw new Exception();
        }
    }
}
