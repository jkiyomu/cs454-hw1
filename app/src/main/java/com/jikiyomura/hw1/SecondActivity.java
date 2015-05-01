package com.jikiyomura.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jeffrey on 4/27/2015.
 */
public class SecondActivity extends ActionBarActivity {
    Button sin, cos, tan, perc, sqrt, pow, imaginary, ln, log, e, factorial, left, right, del;
    String equation = "";
    EditText edit;
    double first, second;
    String operands = "empty";
    int position = 0;
    boolean easy, error = false;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced);

        equation = getIntent().getStringExtra("equation");
        position = getIntent().getIntExtra("position", 0);
        first = getIntent().getDoubleExtra("first", 0.0);
        second = getIntent().getDoubleExtra("second", 0.0);
        operands = getIntent().getStringExtra("operands");
        easy = getIntent().getBooleanExtra("easy", true);

        edit = (EditText) findViewById(R.id.editText2);
        edit.setText(equation);
        edit.setSelection(position);
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

        del = (Button) findViewById(R.id.del2);
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

        sin = (Button) findViewById(R.id.sin);
        sin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //edit.getText().insert(edit.getSelectionStart(), "sin(");
                equation = edit.getText().toString();

                if(equation.trim().length() > 0){
                    double temp = Double.parseDouble(equation);

                    Log.i("double", ""+temp);
                    if(operands != "empty"){
                        second = Math.sin(temp);
                        first = calculate(first, operands, second);
                        if (!error){
                            if(first % 1 == 0){
                                edit.setText(""+(int) first);
                            }else {
                                edit.setText("" + first);
                            }
                        }else{
                            first = 0.0;
                            second = 0.0;
                            operands = "";
                            edit.setText("ERROR");
                        }
                    }else{
                        first = Math.sin(temp);
                    }
                }else {
                    if (operands.length() > 0) {

                    } else {
                        first = Math.sin(first);
                        if (first % 1 == 0) {
                            edit.setText("" + (int) Math.floor(first));
                        } else {
                            edit.setText("" + first);
                        }
                    }
                }
                //edit.setText(equation);
            }
        });

        cos = (Button) findViewById(R.id.cos);
        cos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "cos(");
                //edit.setText(equation);
            }
        });

        tan = (Button) findViewById(R.id.tan);
        tan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "tan(");
                //edit.setText(equation);
            }
        });

        perc = (Button) findViewById(R.id.perc);
        perc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "%");
                //edit.setText(equation);
            }
        });

        sqrt = (Button) findViewById(R.id.sqrt);
        sqrt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "&#8730;(");
                //edit.setText(equation);
            }
        });

        pow = (Button) findViewById(R.id.pow);
        pow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "^");
                //edit.setText(equation);
            }
        });

        imaginary = (Button) findViewById(R.id.i);
        imaginary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "");
                //edit.setText(equation);
            }
        });

        e = (Button) findViewById(R.id.e);
        e.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "e");
                //edit.setText(equation);
            }
        });

        ln = (Button) findViewById(R.id.ln);
        ln.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "ln(");
                //edit.setText(equation);
            }
        });

        log = (Button) findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "log(");
                //edit.setText(equation);
            }
        });

        factorial = (Button) findViewById(R.id.exc);
        factorial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "!");
                //edit.setText(equation);
            }
        });

        left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), "(");
                //edit.setText(equation);
            }
        });

        right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edit.getText().insert(edit.getSelectionStart(), ")");
                //edit.setText(equation);
            }
        });
    }

    public Double calculate(double first, String op, double second){
        double total = 0.0;
        switch(op){
            case "+":
                total = first + second;
                break;
            case "-":
                total = first - second;
                break;
            case "*":
                total = first * second;
                break;
            case "/":
                if(second == 0.0){
                    error = true;
                }else {
                    total = first / second;
                }
                break;
        }
        return total;
    }

    public void showErrorToast(){
        Toast.makeText(SecondActivity.this, "Unable to place command here.", Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        this.menu = menu;
        MenuItem mi = menu.findItem(R.id.advanced);
        mi.setTitle("Basic");
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
            EditText edit = (EditText) findViewById(R.id.editText2);
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            equation = edit.getText().toString();
            intent.putExtra("equation", equation);
            intent.putExtra("position", position);
            intent.putExtra("first", first);
            intent.putExtra("second", second);
            intent.putExtra("operands", operands);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}