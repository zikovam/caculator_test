package com.testapp.zikovam.caculator_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    Button btnPlus, btnMinus, btnMulti, btnDiv, btnOpenBracket, btnCloseBracket, btnEqual, btnClear;
    Button btnPercent, btnDel;
    TextView tvLCD;

    int flagAction = 0;
    StringBuilder mathStr = new StringBuilder();
    //double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnZero = (Button) findViewById(R.id.btnZero);

        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMulti = (Button) findViewById(R.id.btnMulti);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        btnPercent = (Button) findViewById(R.id.btnPercent);
        btnDel = (Button) findViewById(R.id.btnDel);

        btnOpenBracket = (Button) findViewById(R.id.btnOpenBracket);
        btnCloseBracket = (Button) findViewById(R.id.btnCloseBracket);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnEqual = (Button) findViewById(R.id.btnEqual);

        tvLCD = (TextView) findViewById(R.id.tvLCD);

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnZero.setOnClickListener(this);

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMulti.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

        btnPercent.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        btnOpenBracket.setOnClickListener(this);
        btnCloseBracket.setOnClickListener(this);

        btnClear.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
    }

    private void checkThreeNum () {
//        int index = mathStr.length()-1;
//        while (flagAction >= 4) {
//            index-=3;
//            mathStr.insert(index,',');
//            flagAction-=3;
//        }
        flagAction = 0;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnOne:
                mathStr.append("1");
                flagAction++;
                break;
            case R.id.btnTwo:
                mathStr.append("2");
                flagAction++;
                break;
            case R.id.btnThree:
                mathStr.append("3");
                flagAction++;
                break;
            case R.id.btnFour:
                mathStr.append("4");
                flagAction++;
                break;
            case R.id.btnFive:
                mathStr.append("5");
                flagAction++;
                break;
            case R.id.btnSix:
                mathStr.append("6");
                flagAction++;
                break;
            case R.id.btnSeven:
                mathStr.append("7");
                flagAction++;
                break;
            case R.id.btnEight:
                mathStr.append("8");
                flagAction++;
                break;
            case R.id.btnNine:
                mathStr.append("9");
                flagAction++;
                break;
            case R.id.btnZero:
                mathStr.append("0");
                flagAction++;
                break;
            case R.id.btnPlus:
                mathStr.append("+");
                checkThreeNum();
                break;
            case R.id.btnMinus:
                mathStr.append("-");
                checkThreeNum();
                break;
            case R.id.btnMulti:
                mathStr.append("*");
                checkThreeNum();
                break;
            case R.id.btnDiv:
                mathStr.append("/");
                checkThreeNum();
                break;
            case R.id.btnOpenBracket:
                mathStr.append("(");
                checkThreeNum();
                break;
            case R.id.btnCloseBracket:
                mathStr.append(")");
                checkThreeNum();
                break;
            case R.id.btnDel:
                if (mathStr.length()!=0)
                mathStr.delete(mathStr.length()-1,mathStr.length());
                break;
            case R.id.btnClear:
                mathStr.delete(0,mathStr.length());
                flagAction = 0;
                break;
            case R.id.btnPercent:
                //проценты. работают так: если вводится символ процента, то мы считаем всё до него и последней цифры и знака
                //а после уже к этому результату присовокупляем проценты
                if (mathStr.length()==flagAction){
                    int num = Integer.parseInt(mathStr.toString());
                    mathStr.delete(0, mathStr.length());
                    mathStr.append(String.valueOf((double)num/100));
                }
                else if ((mathStr.length()>=3)&&(flagAction!=0)) {
                    ExpressionParser parser1 = new ExpressionParser();
                    List<String> expression1 = parser1.parse(mathStr.substring(0, mathStr.length() - (flagAction + 1)));

                    char action = mathStr.charAt(mathStr.length() - (flagAction + 1));
                    String strPercent = mathStr.substring(mathStr.length() - (flagAction), mathStr.length());
                    int numPercent = Integer.parseInt(strPercent);

                    if (parser1.flag) {
                        for (String x : expression1) System.out.print(x + " ");
                        CalculatePoland result = new CalculatePoland();
                        mathStr.delete(0, mathStr.length());
                        double equalization = result.calc(expression1);
                        double percentEqual = equalization / 100 * numPercent;

                        List<String> percents = new ArrayList<>();
                        percents.add(String.valueOf(equalization));
                        percents.add(String.valueOf(percentEqual));
                        percents.add(String.valueOf(action));

                        mathStr.append(result.calc(percents));
                    } else {
                        Toast.makeText(MainActivity.this, "Некорректное выражение.", Toast.LENGTH_LONG).show();
                        parser1.flag = true;
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Некорректное выражение.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnEqual:
                    ExpressionParser parser = new ExpressionParser();
                    List<String> expression = parser.parse(mathStr.toString());

                    if (parser.flag) {
                        for (String x : expression) System.out.print(x + " ");
                        CalculatePoland result = new CalculatePoland();
                        mathStr.delete(0, mathStr.length());
                        mathStr.append(result.calc(expression));
                    } else {
                        Toast.makeText(MainActivity.this, "Некорректное выражение.", Toast.LENGTH_LONG).show();
                        parser.flag = true;
                    }
                break;
        }
        tvLCD.setText(mathStr.toString());

    }
}
