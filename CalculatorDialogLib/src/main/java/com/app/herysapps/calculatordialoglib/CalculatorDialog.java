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

    private ICalculatorDialogClick mListener;

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


    private View mView;
    private TextView mTextViewValue, mTextViewOperation;
    private HorizontalScrollView horizontalScrollView;

    private List<Character> mStringList = new ArrayList<>();
    private String mName = "";
    private String mDecor = "";
    private int mNumberColor;
    private int mOperationColor;
    private int mNumberBackgroundColor;
    private int mOperatorBackgroundColor;
    private int mDialogButtonsColor;

    private int mLimitNumbers = 0;
    private boolean mLimitNegativeNumbers;
    private String mErrorDiv0, mErrorLimitNumber, mErrorNegativeValue;

    // private
    private String mSeparator;

    public CalculatorDialog() {
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        char sep = symbols.getDecimalSeparator();

        String deco = symbols.getCurrencySymbol();

        mSeparator = String.valueOf(sep);
        mDecor = deco;

        mNumberColor = R.color.numberColor;
        mOperationColor = R.color.operatorColor;
        mNumberBackgroundColor = R.color.numberBackgroundColor;
        mOperatorBackgroundColor = R.color.operatorBackgroundColor;
        mDialogButtonsColor = R.color.dialogButtons;

        mLimitNumbers = 0;
        mLimitNegativeNumbers = false;

        mErrorDiv0 = "Division by 0 impossible";
        mErrorLimitNumber = "Number limit exceeded";
        mErrorNegativeValue = "Negative numbers disabled";
    }

    /**
     * Set an identifier that allows identifier the instance in the notification of listeners.
     * (For multiples dialogs in screen)
     *
     * @param name identifier of calculator dialog
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Show the symbol for decoration of the screen
     *
     * @param decor Symbol
     */
    public void setDecor(String decor) {
        mDecor = decor;
    }

    /**
     * Set the color for the numbers
     *
     * @param numberColor color resource
     */
    public void setNumberColor(int numberColor) {
        this.mNumberColor = numberColor;
    }

    /**
     * Set the color for the operators
     *
     * @param operationColor color resource
     */
    public void setOperationColor(int operationColor) {
        this.mOperationColor = operationColor;
    }

    /**
     * Set the background color for the numbers
     *
     * @param numberBackgroundColor color resource
     */
    public void setNumberBackgroundColor(int numberBackgroundColor) {
        this.mNumberBackgroundColor = numberBackgroundColor;
    }

    /**
     * Set the background color for the operators
     *
     * @param operatorBackgroundColor color resource
     */
    public void setOperatorBackgroundColor(int operatorBackgroundColor) {
        this.mOperatorBackgroundColor = operatorBackgroundColor;
    }

    /**
     * Set the color for the dialog buttons
     *
     * @param dialogButtonsColor color resource
     */
    public void setDialogButtonsColor(int dialogButtonsColor) {
        this.mDialogButtonsColor = dialogButtonsColor;
    }

    /**
     * Set the limit for the length of the numbers. The value must be greater than or equal to zero.
     *
     * @param limit limit
     */
    public void limitNumbers(int limit) {

        if (limit < 0) {
            throw new RuntimeException("The limit must be greater than or equal to zero.");
        }
        this.mLimitNumbers = limit;
    }

    public void negativeNumberActivated(boolean b) {
        mLimitNegativeNumbers = b;
    }

    /**
     * Message to show in the division by zero error.
     *
     * @param message Text of error
     */
    public void setErrorDiv0(String message) {
        this.mErrorDiv0 = message;
    }

    /**
     * Message to show when the limit of number is exceeded
     *
     * @param message Text of error
     */
    public void setErrorLimit(String message) {
        this.mErrorLimitNumber = message;
    }

    /**
     * Message to show when there are negative numbers and they are disabled
     *
     * @param message Text of error
     */
    public void setErrorNegativeValue(String message) {
        this.mErrorNegativeValue = message;
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

        String tmp = getTotalInString();
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
            setDoubleInList(value);
        }

        // Build the AlertDialog
        AlertDialog.Builder builderCurrency = new AlertDialog.Builder(getActivity());
        builderCurrency.setTitle("");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.activity_calculator_dialog_adapter, null);

        InitializeInterface();

        builderCurrency.setView(mView);

        AlertDialog alertCurrency = builderCurrency.create();

        alertCurrency.setCanceledOnTouchOutside(true);
        alertCurrency.setCancelable(true);

        return alertCurrency;
    }

    private void InitializeInterface() {
        mTextViewOperation = (TextView) mView.findViewById(R.id.textViewValueOperation);
        mTextViewValue = (TextView) mView.findViewById(R.id.textViewValue);

        ((TextView) mView.findViewById(R.id.textViewSymbol)).setText(mDecor);

        mTextViewOperation.setText(getOperation());
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

        if (mName.equals("")) {
            throw new RuntimeException("CalculatorDialog instance must initialize the Name property.");
        }

        // set value in list
        mStringList.clear();
        setDoubleInList("" + value);

        // Show dialog
        show(fragmentManager, tag);
    }

    @Override
    public void onClick(View v) {

        String str0 = "0", str1 = "1", str2 = "2", str3 = "3", str4 = "4", str5 = "5", str6 = "6", str7 = "7", str8 = "8", str9 = "9";
        String tV = getTotal();

        if (v.getId() == R.id.buttonCancel) {
            this.dismiss();
            return;
        }

        if (!IsInfinity(tV) && !IsInvalidLimit(tV) && !IsNegativeNumber(tV)) {
            if (v.getId() == R.id.buttonOk) {
                mListener.onCalculatorDialogResponse(mName, getTotalInDouble(), getTotalToShow());
                this.dismiss();
                return;
            }

            if (v.getId() == R.id.buttonEqual) {
                String tmp = getTotalInString();
                mStringList.clear();
                setDoubleInList(tmp);
                mTextViewOperation.setText(getOperation());
                mTextViewValue.setText("");
                return;
            }
        }
        if (v.getId() == R.id.imageButtonDel) {
            deleteNumber();
        }


        if (v.getId() == R.id.buttonDivision) {
            addOperator(Character.Operation.DIVISION);
        }
        if (v.getId() == R.id.buttonMultiplication) {
            addOperator(Character.Operation.MULTIPLICATION);
        }
        if (v.getId() == R.id.buttonSubtraction) {
            addOperator(Character.Operation.SUBTRACTION);
        }
        if (v.getId() == R.id.buttonSum) {
            addOperator(Character.Operation.SUM);
        }


        if (v.getId() == R.id.buttonPoint) {
            updateNumber(".", true);
        }
        if (v.getId() == R.id.button09) {
            updateNumber(str9, false);
        }
        if (v.getId() == R.id.button08) {
            updateNumber(str8, false);
        }
        if (v.getId() == R.id.button07) {
            updateNumber(str7, false);
        }
        if (v.getId() == R.id.button06) {
            updateNumber(str6, false);
        }
        if (v.getId() == R.id.button05) {
            updateNumber(str5, false);
        }
        if (v.getId() == R.id.button04) {
            updateNumber(str4, false);
        }
        if (v.getId() == R.id.button03) {
            updateNumber(str3, false);
        }
        if (v.getId() == R.id.button02) {
            updateNumber(str2, false);
        }
        if (v.getId() == R.id.button01) {
            updateNumber(str1, false);
        }
        if (v.getId() == R.id.button00) {
            updateNumber(str0, false);
        }

        mTextViewOperation.setText(getOperation());
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

    // Operators
    private void addOperator(Character.Operation operation) {

        if (mStringList.size() > 0) {
            Character tmp = mStringList.get(mStringList.size() - 1);
            if (tmp.getType() == Character.Type.NUMBER) {
                mStringList.add(new Character(null, operation, Character.Type.OPERATOR));
            }
        }
    }

    // Delete
    private void deleteNumber() {
        if (mStringList.size() > 0) {
            Character character = mStringList.get(mStringList.size() - 1);
            Character.Type type = character.getType();

            if (type == Character.Type.NUMBER) {

                String value = character.getValue();

                if (value.length() <= 1) {
                    mStringList.remove(mStringList.size() - 1);
                } else {
                    character.setValue(value.substring(0, value.length() - 1));
                    mStringList.set(mStringList.size() - 1, character);
                }
            }

            if (type == Character.Type.OPERATOR) {
                mStringList.remove(mStringList.size() - 1);
            }
        }
    }

    // Numbers
    private void updateNumber(String s, boolean isPoint) {

        if (mStringList.size() <= 0) {
            addNumber(s);
        } else {
            Character tmp = mStringList.get(mStringList.size() - 1);
            if (tmp.getType() == Character.Type.NUMBER) {
                if (isPoint) {
                    boolean b = tmp.getValue().contains(s);
                    if (b) {
                        return;
                    }
                }

                tmp.setValue(tmp.getValue() + s);
                mStringList.set(mStringList.size() - 1, tmp);
            } else {
                if (!isPoint) {
                    addNumber(s);
                }
            }
        }


    }

    private void addNumber(String s) {
        mStringList.add(new Character(s, null, Character.Type.NUMBER));
    }


    private String getOperation() {
        String result = "";
        Character.Type type;
        Character.Operation operation;

        for (int i = 0; i < mStringList.size(); i++) {

            type = mStringList.get(i).getType();

            if (type == Character.Type.NUMBER) {
                result = result + getNumberWithSeparation(mStringList.get(i).getValue());
            }

            if (type == Character.Type.OPERATOR) {
                operation = mStringList.get(i).getOperation();

                if (operation == Character.Operation.SUM) {
                    result = result + " + ";
                } else if (operation == Character.Operation.SUBTRACTION) {
                    result = result + " - ";
                } else if (operation == Character.Operation.MULTIPLICATION) {
                    result = result + " × ";
                } else if (operation == Character.Operation.DIVISION) {
                    result = result + " ÷ ";
                }
            }
        }

        result = result.replace(".", mSeparator);

        return result;
    }


    private String getTotal() {

        String result = "";

        if (mStringList.isEmpty()) {
            return result;
        }

        Character.Type type01, type02;
        Character.Operation operation;
        List<Character> mStringListAux = new ArrayList<>();

        int itemsCount;
        if (mStringList.size() % 2 == 0) {
            itemsCount = mStringList.size() - 1;
        } else {
            itemsCount = mStringList.size();
        }

        for (int i = 0; i < itemsCount; i++) {
            Character copy = new Character(mStringList.get(i).getValue(), mStringList.get(i).getOperation(), mStringList.get(i).getType());
            mStringListAux.add(copy);
        }

        int index = 0;
        double number01, number02, numberAux;

        int iteration = 1;
        while (mStringListAux.size() > 1) {

            type01 = mStringListAux.get(index).getType();

            if (type01 == Character.Type.NUMBER) {
                type02 = mStringListAux.get(index + 1).getType();

                if (type02 == Character.Type.OPERATOR) {
                    operation = mStringListAux.get(index + 1).getOperation();

                    if (operation == Character.Operation.SUM) {
                        if (iteration == 1) {
                            index = index + 2;
                        }
                        if (iteration == 2) {
                            number01 = Double.parseDouble(mStringListAux.get(index).getValue());
                            number02 = Double.parseDouble(mStringListAux.get(index + 2).getValue());
                            numberAux = number01 + number02;

                            Character object = mStringListAux.get(index);
                            object.setValue(String.valueOf(numberAux));
                            mStringListAux.set(index, object);
                            mStringListAux.remove(index + 2);
                            mStringListAux.remove(index + 1);
                        }
                    } else if (operation == Character.Operation.SUBTRACTION) {
                        if (iteration == 1) {
                            index = index + 2;
                        }
                        if (iteration == 2) {
                            number01 = Double.parseDouble(mStringListAux.get(index).getValue());
                            number02 = Double.parseDouble(mStringListAux.get(index + 2).getValue());
                            numberAux = number01 - number02;

                            Character object = mStringListAux.get(index);
                            object.setValue(String.valueOf(numberAux));
                            mStringListAux.set(index, object);
                            mStringListAux.remove(index + 2);
                            mStringListAux.remove(index + 1);
                        }
                    } else if (operation == Character.Operation.MULTIPLICATION) {
                        if (iteration == 1) {
                            number01 = Double.parseDouble(mStringListAux.get(index).getValue());
                            number02 = Double.parseDouble(mStringListAux.get(index + 2).getValue());
                            numberAux = number01 * number02;

                            Character object = mStringListAux.get(index);
                            object.setValue(String.valueOf(numberAux));
                            mStringListAux.set(index, object);
                            mStringListAux.remove(index + 2);
                            mStringListAux.remove(index + 1);
                        }

                    } else if (operation == Character.Operation.DIVISION) {
                        if (iteration == 1) {
                            number01 = Double.parseDouble(mStringListAux.get(index).getValue());
                            number02 = Double.parseDouble(mStringListAux.get(index + 2).getValue());
                            numberAux = number01 / number02;

                            Character object = mStringListAux.get(index);
                            object.setValue(String.valueOf(numberAux));
                            mStringListAux.set(index, object);
                            mStringListAux.remove(index + 2);
                            mStringListAux.remove(index + 1);
                        }

                    }
                }
            }

            if (index >= mStringListAux.size() - 1) {
                iteration = 2;
                index = 0;
            }
        }

        result = mStringListAux.get(0).getValue();

        mStringListAux.clear();
        return result;
    }

    private String getTotalInString() {
        String value = getTotal();
        BigDecimal bd = new BigDecimal(!value.equals("") ? value : "0");
        return bd.toPlainString();
    }

    private double getTotalInDouble() {
        String total = getTotal();
        double result = 0;
        if (!total.equals("")) {
            result = Double.parseDouble(total);
        }
        return result;
    }

    private String getTotalToShow() {

        String result;
        String total = getTotal();

        if (IsInfinity(total)) {
            result = mErrorDiv0;
        } else if (IsInvalidLimit(total)) {
            result = mErrorLimitNumber;
        } else if (IsNegativeNumber(total)) {
            result = mErrorNegativeValue;
        } else {
            BigDecimal bd = new BigDecimal(!total.equals("") ? total : "0");
            total = bd.toPlainString();

            result = getNumberWithSeparation(total);
        }

        return result.replace(".", mSeparator);
    }

    private boolean IsInfinity(String total) {

        if (total.equals(""))
            return false;

        if (Double.parseDouble(total) == Double.POSITIVE_INFINITY || Double.parseDouble(total) == Double.POSITIVE_INFINITY) {
            return true;
        } else {
            return false;
        }
    }

    private boolean IsInvalidLimit(String total) {

        if (mLimitNumbers > 0 && total.length() > mLimitNumbers) {
            return true;
        } else {
            return false;
        }
    }

    private boolean IsNegativeNumber(String total) {

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

    private String getNumberWithSeparation(String number) {

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

        return result;
    }


    private void setDoubleInList(String strNumber) {
        BigDecimal bd = new BigDecimal(strNumber);

        String strNum = bd.toPlainString();
        int radixLoc = strNum.indexOf('.');
        String partInt, partDec;
        if (radixLoc == -1) {
            partInt = strNum;
            partDec = "0";
        } else {
            partInt = strNum.substring(0, radixLoc);
            partDec = strNum.substring(radixLoc + 1, strNum.length());
        }

        for (int i = 0; i < partInt.length(); i++) {
            String character = partInt.substring(i, i + 1);
            updateNumber(character, false);
        }

        if (!partDec.equals("0")) {

            updateNumber(".", true);

            for (int i = 0; i < partDec.length(); i++) {
                String character = partDec.substring(i, i + 1);
                updateNumber(character, false);
            }
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
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ICalculatorDialogClick) {
            mListener = (ICalculatorDialogClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CalculatorDialog.onCalculatorDialogResponse");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onLongClick(View v) {

        mStringList.clear();

        mTextViewOperation.setText(getOperation());
        mTextViewValue.setText(getTotalToShow());

        return false;
    }


    /**
     * Interface for pattern observer
     */
    public interface ICalculatorDialogClick {
        void onCalculatorDialogResponse(String colorSelectorName, double value, String valueStr);
    }
}
