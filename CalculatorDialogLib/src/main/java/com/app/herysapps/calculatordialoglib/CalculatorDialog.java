package com.app.herysapps.calculatordialoglib;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * A calculator dialog for Android..
 * <p>
 * Created by Hery Lopez on 15/08/2017.
 * <p>
 * Github:
 * <p>
 * Author: <a href="https://github.com/HeryLopez/">https://github.com/HeryLopez</a>
 * <br/>Project:  <a href="https://github.com/HeryLopez/CalculatorDialog">https://github.com/HeryLopez/CalculatorDialog</a>
 */
public class CalculatorDialog extends DialogFragment implements View.OnClickListener, View.OnLongClickListener {

    private static final String NAME = "NAME";
    private static final String DECOR = "DECOR";
    private static final String NUMBER_COLOR = "NUMBER_COLOR";
    private static final String OPERATION_COLOR = "OPERATION_COLOR";
    private static final String NUMBER_BACK_COLOR = "NUMBER_BACK_COLOR";
    private static final String OPERATION_BACK_COLOR = "OPERATION_BACK_COLOR";
    private static final String DIALOG_BUTTONS_COLOR = "DIALOG_BUTTONS_COLOR";
    private static final String LIMIT_NUMBER = "LIMIT_NUMBER";
    private static final String NEGATIVE_NUMBERS = "NEGATIVE_NUMBERS";
    private static final String ERROR_DIV_0 = "ERROR_DIV_0";
    private static final String ERROR_LIMIT = "ERROR_LIMIT";
    private static final String ERROR_NEGATIVE_NUMBERS = "ERROR_NEGATIVE_NUMBERS";
    private static final String VALUE = "VALUE";

    private OnDialogResultListener mListener;

    private String mName;
    private String mDecor;
    private int mNumberColor = R.color.numberColor;
    private int mOperationColor = R.color.operatorColor;
    private int mNumberBackgroundColor = R.color.numberBackgroundColor;
    private int mOperatorBackgroundColor = R.color.operatorBackgroundColor;
    private int mDialogButtonsColor = R.color.dialogButtons;
    private int mLimitNumbers = 0;
    private boolean mLimitNegativeNumbers = false;
    private String mErrorDiv0 = "Division by 0 impossible";
    private String mErrorLimitNumber = "Number limit exceeded";
    private String mErrorNegativeValue = "Negative numbers disabled";

    private View mView;
    private TextView mTextViewValue, mTextViewOperation;
    private HorizontalScrollView horizontalScrollView;
    
    private String mSeparator;
    private Calculator calculator;

    protected CalculatorDialog() {
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();

        // Separator
        char sep = symbols.getDecimalSeparator();
        mSeparator = String.valueOf(sep);

        // Decor
        mDecor = symbols.getCurrencySymbol();

        // Calculator
        calculator = new Calculator(mSeparator);
    }

    public void setParameters(OnDialogResultListener listener, String name, String decor,
                              Integer numberColor, Integer operationColor,
                              Integer numberBackgroundColor, Integer operatorBackgroundColor,
                              Integer dialogButtonsColor, Integer limitNumbers,
                              Boolean limitNegativeNumbers, String errorDiv0,
                              String errorLimitNumber, String errorNegativeValue) {

        mListener = listener;
        mName = name;
     /*
        this.mDecor = decor;
        this.mNumberColor = numberColor;
        this.mOperationColor = operationColor;
        this.mNumberBackgroundColor = numberBackgroundColor;
        this.mOperatorBackgroundColor = operatorBackgroundColor;
        this.mDialogButtonsColor = dialogButtonsColor;
        this.mLimitNumbers = limitNumbers;
        this.mLimitNegativeNumbers = limitNegativeNumbers;
        this.mErrorDiv0 = errorDiv0;
        this.mErrorLimitNumber = errorLimitNumber;
        this.mErrorNegativeValue = errorNegativeValue;
        */

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(NAME, mName);
        outState.putString(DECOR, mDecor);
        outState.putInt(NUMBER_COLOR, mNumberColor);
        outState.putInt(OPERATION_COLOR, mOperationColor);
        outState.putInt(NUMBER_BACK_COLOR, mNumberBackgroundColor);
        outState.putInt(OPERATION_BACK_COLOR, mOperatorBackgroundColor);
        outState.putInt(DIALOG_BUTTONS_COLOR, mDialogButtonsColor);
        outState.putInt(LIMIT_NUMBER, mLimitNumbers);
        outState.putBoolean(NEGATIVE_NUMBERS, mLimitNegativeNumbers);
        outState.putString(ERROR_DIV_0, mErrorDiv0);
        outState.putString(ERROR_LIMIT, mErrorLimitNumber);
        outState.putString(ERROR_NEGATIVE_NUMBERS, mErrorNegativeValue);

        String tmp = calculator.getTotalInString();
        outState.putString(VALUE, tmp);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get information the instance if there is.
        if (savedInstanceState != null) {
            mName = savedInstanceState.getString(NAME);
            mDecor = savedInstanceState.getString(DECOR);
            mNumberColor = savedInstanceState.getInt(NUMBER_COLOR);
            mOperationColor = savedInstanceState.getInt(OPERATION_COLOR);
            mNumberBackgroundColor = savedInstanceState.getInt(NUMBER_BACK_COLOR);
            mOperatorBackgroundColor = savedInstanceState.getInt(OPERATION_BACK_COLOR);
            mDialogButtonsColor = savedInstanceState.getInt(DIALOG_BUTTONS_COLOR);
            mLimitNumbers = savedInstanceState.getInt(LIMIT_NUMBER);
            mLimitNegativeNumbers = savedInstanceState.getBoolean(NEGATIVE_NUMBERS);
            mErrorDiv0 = savedInstanceState.getString(ERROR_DIV_0);
            mErrorLimitNumber = savedInstanceState.getString(ERROR_LIMIT);
            mErrorNegativeValue = savedInstanceState.getString(ERROR_NEGATIVE_NUMBERS);

            String value = savedInstanceState.getString(VALUE);
            calculator.setDoubleInList(value);
        }

        // Build the AlertDialog
        AlertDialog.Builder builderCurrency = new AlertDialog.Builder(getActivity());
        builderCurrency.setTitle("");


        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.activity_calculator_dialog_adapter, null);

        initializeInterface();

        builderCurrency.setView(mView);

        AlertDialog alertCurrency = builderCurrency.create();

        alertCurrency.setCanceledOnTouchOutside(true);
        alertCurrency.setCancelable(true);

        return alertCurrency;
    }

    private void initializeInterface() {
        mTextViewOperation = (TextView) mView.findViewById(R.id.textViewValueOperation);
        mTextViewValue = (TextView) mView.findViewById(R.id.textViewValue);

        ((TextView) mView.findViewById(R.id.textViewSymbol)).setText(mDecor);

        mTextViewOperation.setText(calculator.getOperation());
        mTextViewValue.setText(getTotalToShow());

        mTextViewOperation.setMovementMethod(new ScrollingMovementMethod());
        horizontalScrollView = (HorizontalScrollView) mView.findViewById(R.id.horizontalScroll);


        mView.findViewById(R.id.button09).setOnClickListener(this);
        mView.findViewById(R.id.button08).setOnClickListener(this);
        mView.findViewById(R.id.button07).setOnClickListener(this);
        mView.findViewById(R.id.button06).setOnClickListener(this);
        mView.findViewById(R.id.button05).setOnClickListener(this);
        mView.findViewById(R.id.button04).setOnClickListener(this);
        mView.findViewById(R.id.button03).setOnClickListener(this);
        mView.findViewById(R.id.button02).setOnClickListener(this);
        mView.findViewById(R.id.button01).setOnClickListener(this);
        mView.findViewById(R.id.button00).setOnClickListener(this);
        mView.findViewById(R.id.buttonEqual).setOnClickListener(this);
        mView.findViewById(R.id.buttonPoint).setOnClickListener(this);

        mView.findViewById(R.id.imageButtonDel).setOnClickListener(this);
        mView.findViewById(R.id.buttonDivision).setOnClickListener(this);
        mView.findViewById(R.id.buttonMultiplication).setOnClickListener(this);
        mView.findViewById(R.id.buttonSubtraction).setOnClickListener(this);
        mView.findViewById(R.id.buttonSum).setOnClickListener(this);
        mView.findViewById(R.id.buttonCancel).setOnClickListener(this);
        mView.findViewById(R.id.buttonOk).setOnClickListener(this);

        mView.findViewById(R.id.imageButtonDel).setOnLongClickListener(this);

        // -----------------------------------------------------------------------------------------
        // Colors
        // -----------------------------------------------------------------------------------------
        mView.findViewById(R.id.numbers).setBackgroundResource(mNumberBackgroundColor);

        mView.findViewById(R.id.operators).setBackgroundResource(mOperatorBackgroundColor);

        int dialogButtonsColor = ContextCompat.getColor(getContext(), mDialogButtonsColor);
        ((Button) mView.findViewById(R.id.buttonCancel)).setTextColor(dialogButtonsColor);
        ((Button) mView.findViewById(R.id.buttonOk)).setTextColor(dialogButtonsColor);

        int numberColor = ContextCompat.getColor(getContext(), mNumberColor);
        ((Button) mView.findViewById(R.id.button09)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button08)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button07)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button06)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button05)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button04)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button03)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button02)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button01)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.button00)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.buttonEqual)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.buttonPoint)).setTextColor(numberColor);
        ((Button) mView.findViewById(R.id.buttonPoint)).setText(mSeparator);


        int operationColor = ContextCompat.getColor(getContext(), mOperationColor);


        ((ImageButton) mView.findViewById(R.id.imageButtonDel)).setColorFilter(operationColor);


        ((Button) mView.findViewById(R.id.buttonDivision)).setTextColor(operationColor);
        ((Button) mView.findViewById(R.id.buttonMultiplication)).setTextColor(operationColor);
        ((Button) mView.findViewById(R.id.buttonSubtraction)).setTextColor(operationColor);
        ((Button) mView.findViewById(R.id.buttonSum)).setTextColor(operationColor);

    }

    /**
     * Show the dialog et set the initial value.
     *
     * @param fragmentManager see {@link DialogFragment#show(FragmentManager, String) DialogFragment.show}.
     * @param tag             see {@link DialogFragment#show(FragmentManager, String) DialogFragment.show}.
     * @param value           initial value
     */
    public void showDialog(FragmentManager fragmentManager, String tag, double value) {
        // set value in list
        calculator.clearOperations();
        calculator.setDoubleInList("" + value);

        // Show dialog
        show(fragmentManager, tag);
    }

    @Override
    public void onClick(View v) {
        String str0 = "0", str1 = "1", str2 = "2", str3 = "3", str4 = "4", str5 = "5", str6 = "6", str7 = "7", str8 = "8", str9 = "9";
        String tV = calculator.getTotal();

        boolean thereIsNotError = !isInfinity(tV) && !isInvalidLimit(tV) && !isNegativeNumber(tV);

        if (v.getId() == R.id.buttonCancel) {
            this.dismiss();
            return;
        } else if (v.getId() == R.id.buttonOk) {
            if (thereIsNotError) {
                mListener.onDialogResult(mName, calculator.getTotalInDouble(), getTotalToShow());
                this.dismiss();
                return;
            }
        } else if (v.getId() == R.id.buttonEqual) {
            if (thereIsNotError) {
                String tmp = calculator.getTotalInString();
                calculator.clearOperations();
                calculator.setDoubleInList(tmp);
                mTextViewOperation.setText(calculator.getOperation());
                mTextViewValue.setText("");
                return;
            }
        } else if (v.getId() == R.id.imageButtonDel) {
            calculator.deleteNumber();
        } else if (v.getId() == R.id.buttonDivision) {
            calculator.addOperator(Operation.DIVISION);
        } else if (v.getId() == R.id.buttonMultiplication) {
            calculator.addOperator(Operation.MULTIPLICATION);
        } else if (v.getId() == R.id.buttonSubtraction) {
            calculator.addOperator(Operation.SUBTRACTION);
        } else if (v.getId() == R.id.buttonSum) {
            calculator.addOperator(Operation.SUM);
        } else if (v.getId() == R.id.buttonPoint) {
            calculator.updateNumber(".", true);
        } else if (v.getId() == R.id.button09) {
            calculator.updateNumber(str9, false);
        } else if (v.getId() == R.id.button08) {
            calculator.updateNumber(str8, false);
        } else if (v.getId() == R.id.button07) {
            calculator.updateNumber(str7, false);
        } else if (v.getId() == R.id.button06) {
            calculator.updateNumber(str6, false);
        } else if (v.getId() == R.id.button05) {
            calculator.updateNumber(str5, false);
        } else if (v.getId() == R.id.button04) {
            calculator.updateNumber(str4, false);
        } else if (v.getId() == R.id.button03) {
            calculator.updateNumber(str3, false);
        } else if (v.getId() == R.id.button02) {
            calculator.updateNumber(str2, false);
        } else if (v.getId() == R.id.button01) {
            calculator.updateNumber(str1, false);
        } else if (v.getId() == R.id.button00) {
            calculator.updateNumber(str0, false);
        }

        mTextViewOperation.setText(calculator.getOperation());
        mTextViewValue.setText(getTotalToShow());

        fullScroll();
    }

    private void fullScroll() {
        horizontalScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //replace this line to scroll up or down
                horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
            }
        }, 100L);
    }


    private String getTotalToShow() {

        String result;
        String total = calculator.getTotal();

        if (isInfinity(total)) {
            result = mErrorDiv0;
        } else if (isInvalidLimit(total)) {
            result = mErrorLimitNumber;
        } else if (isNegativeNumber(total)) {
            result = mErrorNegativeValue;
        } else {
            BigDecimal bd = new BigDecimal(!total.equals("") ? total : "0");
            total = bd.toPlainString();

            result = calculator.getNumberWithSeparation(total);
        }

        return result.replace(".", mSeparator);
    }

    private boolean isInfinity(String total) {

        if (total.equals(""))
            return false;

        if (Double.parseDouble(total) == Double.POSITIVE_INFINITY || Double.parseDouble(total) == Double.POSITIVE_INFINITY) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isInvalidLimit(String total) {

        if (mLimitNumbers > 0 && total.length() > mLimitNumbers) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNegativeNumber(String total) {

        if (!mLimitNegativeNumbers)
            return false;

        if (total.equals(""))
            return false;

        if (Double.parseDouble(total) < 0) {
            return true;
        } else {
            return false;
        }
    }


    public String getNumberWithFormat(double num) {

        String number = String.valueOf(num);

        int radixLoc = number.indexOf('.');
        String partInt, partDec;
        if (radixLoc == -1) {
            partInt = number;
            partDec = "0";
        } else {
            partInt = number.substring(0, radixLoc);
            partDec = number.substring(radixLoc + 1, number.length());
        }

        String newStr = "";
        int blockIndicator = 0;
        for (int i = partInt.length() - 1; i >= 0; i--) {

            String character = partInt.substring(i, i + 1);
            newStr = character + newStr;

            if (blockIndicator == 2) {
                blockIndicator = 0;
                newStr = " " + newStr;
            } else {
                blockIndicator++;
            }
        }

        String result;
        if (partDec.equals("0")) {
            result = newStr;
        } else {
            result = newStr + "." + partDec;
        }


        result = result.replace(".", mSeparator);

        return result;
    }

    @Override
    public boolean onLongClick(View v) {
        calculator.clearOperations();
        mTextViewOperation.setText(calculator.getOperation());
        mTextViewValue.setText(getTotalToShow());
        return false;
    }


    /**
     * Interface for pattern observer
     */
    public interface OnDialogResultListener {
        void onDialogResult(String name, double value, String valueStr);
    }
}
