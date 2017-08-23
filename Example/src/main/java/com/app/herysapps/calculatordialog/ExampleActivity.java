package com.app.herysapps.calculatordialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.app.herysapps.calculatordialoglib.CalculatorDialog;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Examples of use.
 * <p>
 * Created by Hery Lopez on 10/08/2017.
 * <p>
 * Github:
 * Author: https://github.com/HeryLopez
 * Project: https://github.com/HeryLopez/ColorSelector
 */
public class ExampleActivity extends AppCompatActivity implements CalculatorDialog.ICalculatorDialogClick {

    CalculatorDialog calculatorDialog01, calculatorDialog02;

    private String DIALOG_01 = "calculatorDialog01";
    private String DIALOG_02 = "calculatorDialog02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        char sep = symbols.getDecimalSeparator();


        calculatorDialog01 = new CalculatorDialog();
        calculatorDialog01.setName(DIALOG_01);
        calculatorDialog01.setSeparator(String.valueOf(sep));
        calculatorDialog01.setDecor("£");

        (findViewById(R.id.buttonSelector01)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ((TextView)findViewById(R.id.textView1)).getText().toString();
                double v1 = 0;

                if(!s.equals(""))
                    v1 = Double.parseDouble(s);

                calculatorDialog01.showDialog(getSupportFragmentManager(), "dialog", v1);
            }
        });

        calculatorDialog02 = new CalculatorDialog();
        calculatorDialog02.setName(DIALOG_02);
        calculatorDialog02.setSeparator(";");
        calculatorDialog02.setDecor("$");
        calculatorDialog01.setNumberColor(R.color.color01);
        calculatorDialog01.setOperationColor(R.color.color01);
        calculatorDialog01.setNumberBackgroundColor(R.color.color02);
        calculatorDialog01.setOperatorBackgroundColor(R.color.color02);
        calculatorDialog01.setDialogButtonsColor(R.color.color01);


        (findViewById(R.id.buttonSelector02)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ((TextView)findViewById(R.id.textView2)).getText().toString();
                double v1 = 0;

                if(!s.equals(""))
                    v1 = Double.parseDouble(s);

                calculatorDialog02.showDialog(getSupportFragmentManager(), "dialog", v1);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCalculatorDialogResponse(String name, String value) {

        if (name.equals(DIALOG_01)) {
            ((TextView)findViewById(R.id.textView1)).setText(value);
        }

        if (name.equals(DIALOG_02)) {
            ((TextView)findViewById(R.id.textView2)).setText(value);
        }
    }

}
