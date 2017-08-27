package com.app.herysapps.calculatordialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.herysapps.calculatordialoglib.CalculatorDialog;

/**
 * Examples of use.
 * <p>
 * Created by Hery Lopez on 10/08/2017.
 * <p>
 * Github:
 * Author: https://github.com/HeryLopez
 * Project: https://github.com/HeryLopez/CalculatorDialog
 */
public class ExampleActivity extends AppCompatActivity implements CalculatorDialog.ICalculatorDialogClick {

    CalculatorDialog calculatorDialog01, calculatorDialog02;

    private String DIALOG_01 = "calculatorDialog01";
    private String DIALOG_02 = "calculatorDialog02";

    private double v1, v2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        calculatorDialog01 = new CalculatorDialog();
        calculatorDialog01.setName(DIALOG_01);
        calculatorDialog01.limitNumbers(20);
        calculatorDialog01.negativeNumberActivated(true);
        calculatorDialog01.setErrorDiv0(getString(R.string.div_0_error));
        calculatorDialog01.setErrorLimit(getString(R.string.limit_number_error));
        calculatorDialog01.setErrorNegativeValue(getString(R.string.limit_negative_number));

        v1 = 1234567.89; //3.1415
        String strWithFormat = calculatorDialog01.getNumberWithFormat(v1);
        ((TextView)findViewById(R.id.textView1)).setText(strWithFormat);


        (findViewById(R.id.buttonSelector01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatorDialog01.showDialog(getSupportFragmentManager(), "dialog", v1);
            }
        });

        calculatorDialog02 = new CalculatorDialog();
        calculatorDialog02.setName(DIALOG_02);
        calculatorDialog02.setDecor("$");
        calculatorDialog02.setNumberColor(R.color.color01);
        calculatorDialog02.setOperationColor(R.color.color01);
        calculatorDialog02.setNumberBackgroundColor(R.color.color02);
        calculatorDialog02.setOperatorBackgroundColor(R.color.color02);
        calculatorDialog02.setDialogButtonsColor(R.color.color01);

        (findViewById(R.id.buttonSelector02)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatorDialog02.showDialog(getSupportFragmentManager(), "dialog", v2);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCalculatorDialogResponse(String name, double value, String valueStr) {

        if (name.equals(DIALOG_01)) {
            ((TextView)findViewById(R.id.textView1)).setText(valueStr);
            v1 = value;
        }

        if (name.equals(DIALOG_02)) {
            ((TextView)findViewById(R.id.textView2)).setText(valueStr);
            v2 = value;
        }
    }

}
