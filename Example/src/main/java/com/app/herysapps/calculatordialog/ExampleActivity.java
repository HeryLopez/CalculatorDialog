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

    CalculatorDialog cal01, cal02;

    private String DIALOG_01 = "calculatorDialog01";
    private String DIALOG_02 = "calculatorDialog02";

    private double v1, v2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        cal01 = new CalculatorDialog();
        cal01.setName(DIALOG_01);
        cal01.limitNumbers(20);
        cal01.negativeNumberActivated(true);
        cal01.setErrorDiv0(getString(R.string.div_0_error));
        cal01.setErrorLimit(getString(R.string.limit_number_error));
        cal01.setErrorNegativeValue(getString(R.string.limit_negative_number));

        v1 = 1234567.89; //3.1415
        String strWithFormat = cal01.getNumberWithFormat(v1);
        ((TextView)findViewById(R.id.textView1)).setText(strWithFormat);


        (findViewById(R.id.buttonSelector01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal01.showDialog(getSupportFragmentManager(), "dialog", v1);
            }
        });

        cal02 = new CalculatorDialog();
        cal02.setName(DIALOG_02);
        cal02.setDecor("$");
        cal02.setNumberColor(R.color.color01);
        cal02.setOperationColor(R.color.color01);
        cal02.setNumberBackgroundColor(R.color.color02);
        cal02.setOperatorBackgroundColor(R.color.color02);
        cal02.setDialogButtonsColor(R.color.color01);

        (findViewById(R.id.buttonSelector02)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal02.showDialog(getSupportFragmentManager(), "dialog", v2);
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
