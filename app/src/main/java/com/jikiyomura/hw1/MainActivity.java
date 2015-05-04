package com.jikiyomura.hw1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button zero;
    Button add;
    Button sub;
    Button divide;
    Button mult;
    Button eq;
    Button dot;
    Button del;
    Button clear;
    EditText edit;
    String equation = "";
    double first = 0.0, second = 0.0;
    String operands = "empty";
    Menu menu;
    private static final String TAG = "MyActivity";
    int position;
    double total = 0.0;
    boolean error = false;
    boolean easy = true;

    String regex = "(\\bsin\\b)\\(|(\\bcos\\b)\\(|(\\btan\\b)\\(|(\\bsqrt\\b)\\(|(\\be\\b)\\(|(\\bln\\b)\\(|(\\blog\\b)\\(|([\\()*^\\-+/!]+)";
    String regex2 = "(?=\\bsin\\b\\()|(?<=\\bsin\\b\\()|(\\bcos\\b\\()|(\\btan\\b\\()|(\\bsqrt\\b\\()|(\\be\\b\\()|(\\bln\\b\\()|(\\blog\\b\\()|(?=[\\()*^\\-+/!]+)|(?<=[\\()*^\\-+/!]+)";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(intent.getStringExtra("equation") != null) equation = intent.getStringExtra("equation");
        position = intent.getIntExtra("position", 0);
        first = intent.getDoubleExtra("first", 0.0);
        second = intent.getDoubleExtra("second", 0.0);
        if (intent.getStringExtra("operands") != null) {
            operands = intent.getStringExtra("operands");
        }

        edit = (EditText) findViewById(R.id.editText);
        edit.setText(equation);
        if (position > equation.length()){
            edit.setSelection(equation.length());
        }else{
            edit.setSelection(position);
        }

        View.OnTouchListener otl = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Layout layout = ((EditText) v).getLayout();
                        float x = event.getX() + edit.getScrollX();
                        int offset = layout.getOffsetForHorizontal(0, x);
                        if(offset>0)
                            if(x>layout.getLineMax(0))
                                edit.setSelection(offset);     // touch was at end of text
                            else
                                edit.setSelection(offset - 1);
                        break;
                }
                return true;
            }
        };
        edit.setOnTouchListener(otl);

        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = 0;
                edit.setSelection(position);
                edit.setText("");
            }
        });

        one = (Button) findViewById(R.id.one);
        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (operands != "empty"){
                    edit.setText("");
                }
                edit.getText().insert(edit.getSelectionStart(), "1");
                //edit.setText(equation);
            }
        });
        two = (Button) findViewById(R.id.two);
        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "2");
                //edit.setText(equation);
            }
        });
        three = (Button) findViewById(R.id.three);
        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "3");
                //edit.setText(equation);
            }
        });
        four = (Button) findViewById(R.id.four);
        four.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "4");
                //edit.setText(equation);
            }
        });
        five = (Button) findViewById(R.id.five);
        five.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "5");
                //edit.setText(equation);
            }
        });
        six = (Button) findViewById(R.id.six);
        six.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "6");
                //edit.setText(equation);
            }
        });
        seven = (Button) findViewById(R.id.seven);
        seven.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "7");
                //edit.setText(equation);
            }
        });
        eight = (Button) findViewById(R.id.eight);
        eight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "8");
                //edit.setText(equation);
            }
        });
        nine = (Button) findViewById(R.id.nine);
        nine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "9");
                //edit.setText(equation);
            }
        });
        zero = (Button) findViewById(R.id.zero);
        zero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "0");
                //edit.setText(equation);
            }
        });
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equation = edit.getText().toString();
                int start = edit.getSelectionStart();
                //Toast.makeText(MainActivity.this, ""+start, Toast.LENGTH_SHORT).show();
                int size = equation.length();
                Log.i(TAG, "Start: "+start + " size: "+size);


                if(easy){
                    if(operands != "empty" && !operands.isEmpty()) { // operands isnot empty
                        if(equation.trim().isEmpty()){
                            second = Double.parseDouble(equation.trim());
                            first = calculate(first, operands, second);
                            operands = "+";
                            if(first % 1 == 0){
                                String temp = ""+(int) first;
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }else{
                                String temp = String.valueOf(first);
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }
                        }else{
                            first += Double.parseDouble(equation);
                            if(first % 1 == 0){
                                String temp = ""+(int) first;
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }else{
                                String temp = String.valueOf(first);
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }
                        }

                    }else{
                        first = Double.parseDouble(equation);
                        operands = "+";
                        edit.setText("");
                    }
                }else {
                    if ((start < start) && ((int) equation.charAt(start - 1) >= 48 && (int) equation.charAt(size - 1) <= 57 && (int) equation.charAt(start) >= 48 && (int) equation.charAt(start) <= 57) ||
                            ((int) equation.charAt(start - 1) == 41)) {
                        edit.setSelection(start);
                        edit.getText().insert(edit.getSelectionStart(), "+");
                    } else if ((start == size) && ((int) equation.charAt(start - 1) >= 48 && (int) equation.charAt(size - 1) <= 57) || ((int) equation.charAt(start - 1) == 41)) {
                        edit.setSelection(start);
                        edit.getText().insert(edit.getSelectionStart(), "+");
                    } else {
                        showErrorToast();
                        //Toast.makeText(MainActivity.this, "Unable to place command here.", Toast.LENGTH_LONG).show();
                        //equation = equation.substring(0, size-1);
                        //equation += "+";
                    }
                }
                //edit.setText(equation);
            }
        });
        sub = (Button) findViewById(R.id.sub);
        sub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equation = edit.getText().toString();
                int start = edit.getSelectionStart();
                //Toast.makeText(MainActivity.this, ""+start, Toast.LENGTH_SHORT).show();
                int size = equation.length();

                if(easy){

                    if(operands != "empty" && !operands.isEmpty()) { // operands isnot empty
                        if(equation.trim().isEmpty()){
                            edit.getText().insert(edit.getSelectionStart(), "-");
                        }else{
                            first -= Double.parseDouble(equation);
                            if(first % 1 == 0){
                                String temp = ""+(int) first;
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }else{
                                String temp = String.valueOf(first);
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }
                        }

                    }else{
                        if(equation.isEmpty()){
                            edit.getText().insert(edit.getSelectionStart(), "-");
                        }else {
                            first = Double.parseDouble(equation);
                            operands = "-";
                            edit.setText("");
                        }
                    }
                }else {
                    if ((start < start) && ((int) equation.charAt(start - 1) >= 48 && (int) equation.charAt(size - 1) <= 57 && (int) equation.charAt(start) >= 48 && (int) equation.charAt(start) <= 57) ||
                            ((int) equation.charAt(start - 1) == 41)) {
                        edit.setSelection(start);
                        edit.getText().insert(edit.getSelectionStart(), "-");
                    } else if ((start == size) && ((int) equation.charAt(start - 1) >= 48 && (int) equation.charAt(size - 1) <= 57) || ((int) equation.charAt(start - 1) == 41)) {
                        edit.setSelection(start);
                        edit.getText().insert(edit.getSelectionStart(), "-");
                    } else {
                        showErrorToast();
                        //Toast.makeText(MainActivity.this, "Unable to place command here.", Toast.LENGTH_LONG).show();
                        //equation = equation.substring(0, size-1);
                        //equation += "+";
                    }
                }
                //edit.setText(equation);
            }
        });
        divide = (Button) findViewById(R.id.divide);
        divide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equation = edit.getText().toString();
                int start = edit.getSelectionStart();
                //Toast.makeText(MainActivity.this, ""+start, Toast.LENGTH_SHORT).show();
                int size = equation.length();
                if(easy){
                    if(operands != "empty" && !operands.isEmpty()) { // operands isnot empty
                        if(equation.trim().isEmpty()){
                            second = Double.parseDouble(equation.trim());
                            first = calculate(first, operands, second);
                            operands = "/";
                            if(first % 1 == 0){
                                String temp = ""+(int) first;
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }else{
                                String temp = String.valueOf(first);
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }
                        }else{
                            first /= Double.parseDouble(equation);
                            if(first % 1 == 0){
                                String temp = ""+(int) first;
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }else{
                                String temp = String.valueOf(first);
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }
                        }

                    }else{
                        first = Double.parseDouble(equation);
                        operands = "/";
                        edit.setText("");
                    }
                }else {
                    if ((start < start) && ((int) equation.charAt(start - 1) >= 48 && (int) equation.charAt(size - 1) <= 57 && (int) equation.charAt(start) >= 48 && (int) equation.charAt(start) <= 57) ||
                            ((int) equation.charAt(start - 1) == 41)) {
                        edit.setSelection(start);
                        edit.getText().insert(edit.getSelectionStart(), "/");
                    } else if ((start == size) && ((int) equation.charAt(start - 1) >= 48 && (int) equation.charAt(size - 1) <= 57) || ((int) equation.charAt(start - 1) == 41)) {
                        edit.setSelection(start);
                        edit.getText().insert(edit.getSelectionStart(), "/");
                    } else {
                        showErrorToast();
                        //Toast.makeText(MainActivity.this, "Unable to place command here.", Toast.LENGTH_LONG).show();
                        //equation = equation.substring(0, size-1);
                        //equation += "+";
                    }
                }
                //edit.setText(equation);
            }
        });
        mult = (Button) findViewById(R.id.mult);
        mult.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equation = edit.getText().toString();
                int start = edit.getSelectionStart();
                //Toast.makeText(MainActivity.this, ""+start, Toast.LENGTH_SHORT).show();
                int size = equation.length();
                if(easy){
                    if(operands != "empty" && !operands.isEmpty()) { // operands isnot empty
                        if(equation.trim().isEmpty()){
                            second = Double.parseDouble(equation.trim());
                            first = calculate(first, operands, second);
                            operands = "*";
                            if(first % 1 == 0){
                                String temp = ""+(int) first;
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }else{
                                String temp = String.valueOf(first);
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }
                        }else{
                            first *= Double.parseDouble(equation);
                            if(first % 1 == 0){
                                String temp = ""+(int) first;
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }else{
                                String temp = String.valueOf(first);
                                int end = temp.length()-1;
                                edit.setText(temp);
                                edit.setSelection(end);
                            }
                        }

                    }else{
                        first = Double.parseDouble(equation);
                        operands = "*";
                        edit.setText("");
                    }
                }else {
                    if ((start < start) && ((int) equation.charAt(start - 1) >= 48 && (int) equation.charAt(size - 1) <= 57 && (int) equation.charAt(start) >= 48 && (int) equation.charAt(start) <= 57) ||
                            ((int) equation.charAt(start - 1) == 41)) {
                        edit.setSelection(start);
                        edit.getText().insert(edit.getSelectionStart(), "*");
                    } else if ((start == size) && ((int) equation.charAt(start - 1) >= 48 && (int) equation.charAt(size - 1) <= 57) || ((int) equation.charAt(start - 1) == 41)) {
                        edit.setSelection(start);
                        edit.getText().insert(edit.getSelectionStart(), "*");
                    } else {
                        showErrorToast();
                        //Toast.makeText(MainActivity.this, "Unable to place command here.", Toast.LENGTH_LONG).show();
                        //equation = equation.substring(0, size-1);
                        //equation += "+";
                    }
                }
                //edit.setText(equation);
            }
        });
        dot = (Button) findViewById(R.id.dot);
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equation = edit.getText().toString();
                int start = edit.getSelectionStart();
                //Toast.makeText(MainActivity.this, ""+start, Toast.LENGTH_SHORT).show();
                int size = equation.length();
                if((start < start) && ((int) equation.charAt(start-1) >= 48 && (int) equation.charAt(size-1) <= 57 && (int) equation.charAt(start) >= 48 && (int) equation.charAt(start) <= 57) ||
                        ((int) equation.charAt(start-1) == 41)){
                    //edit.setSelection(start);
                    edit.getText().insert(edit.getSelectionStart(), ".");
                }else if ((start == size) && ((int) equation.charAt(start-1) >= 48 && (int) equation.charAt(size-1) <= 57) || ((int) equation.charAt(start-1) == 41)){
                    edit.setSelection(start);
                    edit.getText().insert(edit.getSelectionStart(), ".");
                }else{
                    showErrorToast();
                }
                //edit.setText(equation);
            }
        });
        eq = (Button) findViewById(R.id.eq);
        eq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equation = edit.getText().toString().trim();

                if(easy){
                    if(operands != "empty" && !operands.isEmpty()){
                        if(equation == null || equation.isEmpty()){

                        }else{
                            second = Double.parseDouble(equation.trim());
                            first = calculate(first, operands, second);
                            if(first % 1 == 0){
                                String temp = ""+((int)first);
                                edit.setText(temp);
                                int end = temp.length();
                                edit.setSelection(end);
                            }else{
                                String temp = String.valueOf(first);
                                edit.setText(temp);
                                int end = temp.length();
                                Toast.makeText(MainActivity.this, ""+end, Toast.LENGTH_LONG).show();
                                edit.setSelection(end);
                            }
                        }
                    }else{
                    }

                    operands = "empty";
                    first = 0.0;
                    second = 0.0;

                }else {
                    Toast.makeText(MainActivity.this, equation, Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, ""+isBalanced(equation), Toast.LENGTH_LONG).show();
                    //splitEquation(equation);
                    //edit.setText(equation);
                    Toast.makeText(MainActivity.this, solve(equation), Toast.LENGTH_LONG).show();
                    edit.setText(solve(equation));
                }
            }
        });
        del = (Button) findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                equation = edit.getText().toString();
                int size = equation.length();
                int start = edit.getSelectionStart();
                if (start != 0) {
                    String begin = equation.substring(0, start - 1);
                    String end = equation.substring(start, size);
                    edit.setText(begin + end);
                    edit.setSelection(start - 1);
                }
            }
        });


    }

    public double calculate(double first, String op, double second){
        this.total = 0.0;
        switch(op){
            case "+":
                this.total = first + second;
                break;
            case "-":
                this.total = first - second;
                break;
            case "*":
                this.total = first * second;
                break;
            case "/":
                this.total = first / second;
                break;
        }

        return this.total;
    }

    public void showErrorToast(){
        Toast.makeText(MainActivity.this, "Unable to place command here.", Toast.LENGTH_LONG).show();
    }

    public String[] splitEquation(String equation){
        String[] splitter;
        splitter = equation.split(regex2);

        Log.i(TAG, showSplit(splitter));
        return splitter;
    }

    public String showSplit(String[] splitter){
        String result = "";
        for(int i = 0; i < splitter.length; i++){
            result += "["+splitter[i]+"]";
        }
        return result;
    }

    public Boolean simplifyEquation (String equation){

        if(equation.contains("sin(")){
            int start = equation.indexOf("sin(")+4;
            int end = 0;
            int left = 1;
            for(int i = start; i < equation.length(); i++){
                if (equation.indexOf(i) == '('){
                    left++;
                }else if (equation.indexOf(i) == ')'){
                    left--;
                }
                if (left == 0){
                    end = i;
                }else if (left == -1 && i == equation.length()+1){
                    //equation += ")";
                }else{
                    error = true;
                    break;
                }
            }
            String eq = equation.substring(start, end);
            if (simplifyEquation(eq)){
                Log.i(TAG, eq);
            }
        }
        return true;
    }

    public String solve(String equation){
        int balance = isBalanced(equation);
        Double total = 0.0;
        if (balance > 0){
            for(int i = 0; i < balance; i++){

            }

        }else if (balance == 0){
            while(equation.contains("*") || equation.contains("/")) {

            }
            while(equation.contains("+") || equation.contains("-")){
                Log.i("Solve", equation);
                int add = equation.indexOf("+");
                int sub = equation.indexOf("-");
                int end = 0;
                if((add > 0 && add < sub && sub > 0) || (add > 0 && sub == -1)){
                    String first = equation.substring(0, add);
                    String second = "";
                    int newAdd = equation.substring(add+1).indexOf("+");
                    int newSub = equation.substring(add+1).indexOf("-");
                    if (newAdd == -1 && newSub == -1){
                        second = equation.substring(add+1);
                    }else if(newAdd != -1 && newSub != -1 && newAdd < newSub){
                        second = equation.substring(add+1, newAdd);
                        end = newAdd;
                    }else if(newAdd != -1 && newSub != -1 && newSub < newAdd){
                        second = equation.substring(add+1, newSub);
                        end = newSub;
                    }
                    Log.i("First", first.trim());
                    Double one = Double.parseDouble(first.trim());
                    Log.i("Second", second.trim());
                    Double two = Double.parseDouble(second.trim());
                    Log.i("End", ""+end);
                    if (end == 0){
                        total = one + two;
                        equation = ""+(one + two);
                    }else{
                        equation = (one + two) + equation.substring(end);
                    }
                }else if(add != -1 && sub != -1 && sub < add){
                    String first = equation.substring(0, add);
                    String second = "";
                    int newAdd = equation.substring(add+1).indexOf("+");
                    int newSub = equation.substring(add+1).indexOf("-");
                    if (newAdd == -1 && newSub == -1){
                        second = equation.substring(add+1);
                    }else if(newAdd != -1 && newSub != -1 && newAdd < newSub){
                        second = equation.substring(add+1, newAdd);
                    }else if(newAdd != -1 && newSub != -1 && newSub < newAdd){
                        second = equation.substring(add+1, newSub);
                    }

                    Double one = Double.parseDouble(first);
                    Double two = Double.parseDouble(second);
                    total = one + two;
                }
            }
        }

        return equation;
    }
    public int isBalanced(String equation){
        boolean balanced = false;
        int leftPer = 0;
        int left = 0;
        for (int i= 0; i < equation.length(); i++){
            if(equation.charAt(i) == '('){
                leftPer++;
                left++;
            }else if (equation.charAt(i) == ')') {
                if (leftPer > 0){
                    leftPer--;
                }else{
                    break;
                }
            }
        }
        if(leftPer == 0){
            balanced = true;
        }

        if (balanced){
            return left;
        }else{
            return -1;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        MenuItem mi = menu.findItem(R.id.advanced);
        mi.setTitle("Advanced");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.advanced) {
            EditText edit = (EditText) findViewById(R.id.editText);

            equation = edit.getText().toString();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("equation", equation);
            intent.putExtra("first", first);
            intent.putExtra("second", second);
            intent.putExtra("operands", operands);
            intent.putExtra("position", edit.getSelectionStart());
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
