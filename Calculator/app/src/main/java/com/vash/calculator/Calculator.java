package com.vash.calculator;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Calculator extends Activity {


    Double val_1 = (double) 0;
    String op = null;
    String val_2;
    ArrayList<String> bltin = new ArrayList<String>() {{
        add("sin");
        add("cos");
        add("tg");
        add("ctg");
        add("log");
        add("fact");
        add("sqrt");
    }};

    public double fact(double num) {
        return (num == 0) ? 1 : num * fact(num - 1);
    }

    private void exprFragmentCutter(int start, int leng, StringBuilder expr, MathFunctor f) {
        int exprEnd = bracketEnd(start + leng, expr.toString());
        String subexpression = expr.substring(start + leng + 1, exprEnd);
        Double result = Double.parseDouble(pretranslator(subexpression));
        Double value = f.func(result);
        if(value < 0){
            expr.replace(start,exprEnd + 1,"_"+Double.toString(Math.abs(value)));
        }else {
            expr.replace(start, exprEnd + 1, value.toString());
        }
    }

    String pretranslator(String expression) {
        StringBuilder exprBuilder = new StringBuilder(expression);
        for (int i = 0; i < exprBuilder.length() - 2; ++i) {
            String twoSymb = exprBuilder.substring(i, i + 2);
            String threeSymb = "";
            String fourSymb = "";
            if (i + 3 < exprBuilder.length()) {
                threeSymb = exprBuilder.substring(i, i + 3);
            }
            if (i + 4 < exprBuilder.length()) {
                fourSymb = exprBuilder.substring(i, i + 4);
            }
            if (bltin.indexOf(twoSymb) != -1) {
                exprFragmentCutter(i, 2, exprBuilder, new MathFunctor() {
                    @Override
                    public Double func(Double value) {
                        value = new BigDecimal(Math.tan(Math.toRadians(value)))
                                .setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue();
                        return value;
                    }
                });
            } else if (bltin.indexOf(threeSymb) != -1) {
                switch (threeSymb) {
                    case "sin":
                        exprFragmentCutter(i, 3, exprBuilder, new MathFunctor() {
                            @Override
                            public Double func(Double value) {
                                value = new BigDecimal(Math.sin(Math.toRadians(value)))
                                        .setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                                return value;
                            }
                        });
                        break;
                    case "cos":
                        exprFragmentCutter(i, 3, exprBuilder, new MathFunctor() {
                            @Override
                            public Double func(Double value) {
                                value = new BigDecimal(Math.cos(Math.toRadians(value)))
                                        .setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue();
                                return value;
                            }
                        });
                        break;
                    case "ctg":
                        exprFragmentCutter(i, 3, exprBuilder, new MathFunctor() {
                            @Override
                            public Double func(Double value) {
                                value = new BigDecimal(1/Math.tan(Math.toRadians(value)))
                                        .setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue();
                                return value;
                            }
                        });
                        break;
                    case "log":
                        exprFragmentCutter(i, 3, exprBuilder, new MathFunctor() {
                            @Override
                            public Double func(Double value) {
                                return Math.log(value);
                            }
                        });
                        break;
                }
            } else if (bltin.indexOf(fourSymb) != -1) {
                switch (fourSymb) {
                    case "sqrt":
                        exprFragmentCutter(i, 4, exprBuilder, new MathFunctor() {
                            @Override
                            public Double func(Double value) {
                                return Math.sqrt(value);
                            }
                        });
                        break;
                    case "fact":
                        exprFragmentCutter(i, 4, exprBuilder, new MathFunctor() {
                            @Override
                            public Double func(Double value) {
                                return fact(value);
                            }
                        });
                        break;
                }
            }
        }
        try {
            String translated = TranslationInScr.inPolishNotation(exprBuilder.toString());
            System.out.println(translated);
            Double result = PolishNotationExecuter.exectute(translated);
            return result.toString();
        } catch (Exception e) {
            System.err.println("Something goes wrong!");
            return "Error";
        }
    }

    int bracketEnd(int startBracket, String expression) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        for (int i = startBracket + 1; i < expression.length(); ++i) {
            if (expression.charAt(i) == '(') {
                stack.push(1);
            }
            if (expression.charAt(i) == ')') {
                stack.pop();
            }
            if (stack.isEmpty()) return i;
        }
        return -1;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.land);
        final TextView view = (TextView) findViewById(R.id.textView1);
        final TextView view1 = (TextView) findViewById(R.id.textView2);
        final TextView view2 = (TextView) findViewById(R.id.textView3);
        Button b_0 = (Button) findViewById(R.id.button1);
        Button b_1 = (Button) findViewById(R.id.button5);
        Button b_2 = (Button) findViewById(R.id.button6);
        Button b_3 = (Button) findViewById(R.id.button7);
        Button b_4 = (Button) findViewById(R.id.button9);
        Button b_5 = (Button) findViewById(R.id.button10);
        Button b_6 = (Button) findViewById(R.id.button11);
        Button b_7 = (Button) findViewById(R.id.button13);
        Button b_8 = (Button) findViewById(R.id.button14);
        Button b_9 = (Button) findViewById(R.id.button15);
        Button b_division = (Button) findViewById(R.id.button16);
        Button b_multiply = (Button) findViewById(R.id.button12);
        Button b_add = (Button) findViewById(R.id.button8);
        Button b_subtract = (Button) findViewById(R.id.button4);
        Button b_entry = (Button) findViewById(R.id.button3);
        Button b_point = (Button) findViewById(R.id.button2);
        Button b_ac = (Button) findViewById(R.id.button17);
        Button b_power = (Button) findViewById(R.id.button18);
        Button b_sin = (Button) findViewById(R.id.button19);
        Button b_cos = (Button) findViewById(R.id.button20);
        Button b_tg = (Button) findViewById(R.id.button21);
        Button b_ctg = (Button) findViewById(R.id.button22);
        final Button b_pi = (Button) findViewById(R.id.button23);
        Button b_e = (Button) findViewById(R.id.button24);
        Button b_log = (Button) findViewById(R.id.button25);
        Button b_sqrt = (Button) findViewById(R.id.button26);
        Button b_fact = (Button) findViewById(R.id.button27);
        Button b_op = (Button) findViewById(R.id.button28);
        Button b_open_bkt = (Button) findViewById(R.id.button29);
        Button b_close_bkt = (Button) findViewById(R.id.button30);
        Button b_back = (Button) findViewById(R.id.button31);


        b_0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "0";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "1";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "2";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "3";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "4";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "5";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "6";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "7";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "8";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_9.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "9";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    new_val = pre_val.concat(buttn);
                }

                view.setText(new_val);
            }
        });

        b_point.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = ".";
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0")) {
                    new_val = buttn;
                } else {
                    if (!pre_val.isEmpty() && Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn);
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_pi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                double buttn = Math.PI;
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        if (op != null) {
                            buttn = Math.toDegrees(Math.PI);
                        }
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_e.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Double buttn = Math.E;
                String pre_val = view.getText().toString();
                String new_val;

                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_division.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String string = (String) view.getText();
                if (!string.isEmpty() && (Character.isDigit(string.charAt(string.length() - 1))
                        || string.charAt(string.length() - 1) == ')')) {
                    view.setText(string + "/");
                } else
                    view.setText(string);
            }
        });

        b_multiply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String string = (String) view.getText();
                if (!string.isEmpty() && (Character.isDigit(string.charAt(string.length() - 1))
                        || string.charAt(string.length() - 1) == ')')) {
                    view.setText(string + "*");
                } else
                    view.setText(string);
            }
        });

        b_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String string = (String) view.getText();
                if (string.isEmpty() || Character.isDigit(string.charAt(string.length() - 1))
                        || string.charAt(string.length() - 1) == ')'
                        || string.charAt(string.length()-1) == '(') {
                    view.setText(string + "+");
                } else
                    view.setText(string);
            }
        });

        b_subtract.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String string = (String) view.getText();
                if (string.equals("0")){
                    view.setText("-");
                } else
                if (string.isEmpty() || Character.isDigit(string.charAt(string.length() - 1))
                        || string.charAt(string.length() - 1) == ')'
                        || string.charAt(string.length()-1) == '(') {
                    view.setText(string + "-");
                } else
                    view.setText(string);
            }
        });

        b_power.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String string = (String) view.getText();
                if (!string.isEmpty() && (Character.isDigit(string.charAt(string.length() - 1))
                        || string.charAt(string.length() - 1) == ')')) {
                    view.setText(string + "^");
                } else
                    view.setText(string);
            }
        });

        b_sin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "sin(";
                String pre_val = view.getText().toString();
                String new_val;
                op = "sin";
                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_cos.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "cos(";
                String pre_val = view.getText().toString();
                String new_val;
                op = "cos";
                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_tg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "tg(";
                String pre_val = view.getText().toString();
                String new_val;
                op = "tg";
                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_ctg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "ctg(";
                String pre_val = view.getText().toString();
                String new_val;
                op = "ctg";
                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_log.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "log(";
                String pre_val = view.getText().toString();
                String new_val;
                op = null;
                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_sqrt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "sqrt(";
                String pre_val = view.getText().toString();
                String new_val;
                op = null;
                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_fact.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String buttn = "fact(";
                String pre_val = view.getText().toString();
                String new_val;
                op = null;
                if (pre_val.equals("0") || pre_val.isEmpty()) {
                    new_val = (buttn + "");
                } else {
                    if (!Character.isDigit(pre_val.charAt(pre_val.length() - 1))) {
                        new_val = pre_val.concat(buttn + "");
                    } else
                        new_val = pre_val;
                }
                view.setText(new_val);
            }
        });

        b_op.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vash) {
                val_2 = TranslationInScr.inPolishNotation(view.getText().toString());
                view1.setText(val_2);
            }
        });

        b_open_bkt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = (String) view.getText();
                String new_string;
                String buttn = "(";
                if (!string.isEmpty() && (Character.isDigit(string.charAt(string.length() - 1))
                        || string.charAt(string.length() - 1) == ')') && !string.equals("0")) {
                    string = string.concat("*");
                }
                if (string.equals("0")) {
                    new_string = buttn;
                } else {
                    new_string = string.concat(buttn);
                }
                view.setText(new_string + "");
            }
        });

        b_close_bkt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = (String) view.getText();
                String new_string;
                String buttn = ")";
                if (string.charAt(string.length() - 1) == ')') {
                    new_string = string.concat(buttn);
                } else if (!string.isEmpty() && !Character.isDigit(string.charAt(string.length() - 1))) {
                    new_string = string;
                } else if (string.equals("0")) {
                    new_string = buttn;
                } else {
                    new_string = string.concat(buttn);
                }
                view.setText(new_string);
                op = null;
            }
        });


        b_entry.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String expression = view.getText().toString();
                if (isBracketsCorrect(expression)) {
                    val_2 = pretranslator(expression);
                    view1.setText(view.getText().toString());
                    try {
                        view.setText("0");
                        view2.setText("=" + val_2);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Error!", Toast.LENGTH_SHORT);
                        toast.show();
                        view1.setText("");
                        view2.setText("");
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        b_ac.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                view.setText("0");
                view1.setText("");
                view2.setText("");
                val_1 = (double) 0;
                op = null;
            }
        });

        b_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = (String) view.getText();
                if (!string.isEmpty()) {
                    string = string.substring(0, string.length() - 1);
                    view.setText(string);
                }
            }
        });

    }


    boolean isBracketsCorrect(String expression) {
        Stack<Integer> brackets = new Stack<>();
        for (int i = 0; i < expression.length(); ++i) {
            try {
                if (expression.charAt(i) == '(') {
                    brackets.push(1);
                } else if (expression.charAt(i) == ')') {
                    brackets.pop();
                }
            } catch (EmptyStackException e) {
                return false;
            }
        }
        return brackets.isEmpty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.vash.calculator.R.menu.activity_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:

            return true;
            case R.id.menu_settings_two:
            return  true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
}