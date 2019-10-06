package com.app.herysapps.calculatordialog;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.herysapps.calculatordialoglib.CalculatorDialog;
import com.app.herysapps.calculatordialoglib.CalculatorDialogBuilder;

/**
 * Examples of use.
 * <p>
 * Created by Hery Lopez on 10/08/2017.
 * <p>
 * Github:
 * Author: https://github.com/HeryLopez
 * Project: https://github.com/HeryLopez/CalculatorDialog
 */
public class ExampleActivity extends AppCompatActivity implements CalculatorDialog.OnDialogResultListener {

    CalculatorDialog cal01, cal02;

    private String DIALOG_01 = "calculatorDialog01";
    private String DIALOG_02 = "calculatorDialog02";

    private double v1, v2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        CalculatorDialogBuilder builderDialog1 = new CalculatorDialogBuilder();
        builderDialog1.setOnResultListener(this);
        builderDialog1.limitNumbers(20);
        builderDialog1.negativeNumberActivated(true);
        builderDialog1.setErrorDiv0(getString(R.string.div_0_error));
        builderDialog1.setErrorLimit(getString(R.string.limit_number_error));
        builderDialog1.setErrorNegativeValue(getString(R.string.limit_negative_number));

        cal01 = builderDialog1.build();

        v1 = 1234567.89; //3.1415
        String strWithFormat = cal01.getNumberWithFormat(v1);
        ((TextView)findViewById(R.id.textView1)).setText(strWithFormat);


        (findViewById(R.id.buttonSelector01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal01.showDialog(getSupportFragmentManager(), DIALOG_01, v1);
            }
        });

        CalculatorDialogBuilder builderDialog2 = new CalculatorDialogBuilder();
        builderDialog2.setOnResultListener(this);
        builderDialog2.setDecor("$");
        builderDialog2.setNumberColor(R.color.color01);
        builderDialog2.setOperationColor(R.color.color01);
        builderDialog2.setNumberBackgroundColor(R.color.color02);
        builderDialog2.setOperatorBackgroundColor(R.color.color02);
        builderDialog2.setDialogButtonsColor(R.color.color01);

        cal02 = builderDialog2.build();

        (findViewById(R.id.buttonSelector02)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal02.showDialog(getSupportFragmentManager(), DIALOG_02, v2);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDialogResult(String tagDialog, double value, String valueStr) {

        if (tagDialog.equals(DIALOG_01)) {
            ((TextView)findViewById(R.id.textView1)).setText(valueStr);
            v1 = value;
        }

        if (tagDialog.equals(DIALOG_02)) {
            ((TextView)findViewById(R.id.textView2)).setText(valueStr);
            v2 = value;
        }
    }
}
