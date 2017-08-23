package com.app.herysapps.calculatordialoglib;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculatorDialog extends DialogFragment implements View.OnClickListener, View.OnLongClickListener {

    private ICalculatorDialogClick mListener;


    private View mView;
    private TextView mTextViewValue, mTextViewOperation;
    private HorizontalScrollView horizontalScrollView;

    private List<Character> mStringList = new ArrayList<>();
    private String mName = "";
    private String mSeparator = ".";
    private String mDecor = "";
    private int mNumberColor;
    private int mOperationColor;
    private int mNumberBackgroundColor;
    private int mOperatorBackgroundColor;
    private int mDialogButtonsColor;


    private double mValue;


    public CalculatorDialog() {
        mNumberColor = R.color.numberColor;
        mOperationColor = R.color.operatorColor;
        mNumberBackgroundColor = R.color.numberBackgroundColor;
        mOperatorBackgroundColor = R.color.operatorBackgroundColor;
        mDialogButtonsColor = R.color.dialogButtons;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setSeparator(String separator) {
        mSeparator = separator;
    }

    public void setDecor(String decor) {
        mDecor = decor;
    }

    public void setNumberColor(int numberColor) {
        this.mNumberColor = numberColor;
    }

    public void setOperationColor(int operationColor) {
        this.mOperationColor = operationColor;
    }

    public void setNumberBackgroundColor(int numberBackgroundColor) {
        this.mNumberBackgroundColor = numberBackgroundColor;
    }

    public void setOperatorBackgroundColor(int operatorBackgroundColor) {
        this.mOperatorBackgroundColor = operatorBackgroundColor;
    }

    public void setDialogButtonsColor(int dialogButtonsColor) {
        this.mDialogButtonsColor = dialogButtonsColor;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get information the instance if there is.
        if (savedInstanceState != null) {
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

    private void InitializeInterface(){
        mTextViewOperation = (TextView) mView.findViewById(R.id.textViewValueOperation);
        mTextViewValue = (TextView) mView.findViewById(R.id.textViewValue);

        ((TextView) mView.findViewById(R.id.textViewSymbol)).setText(mDecor);

        mTextViewOperation.setText(getOperation());
        mTextViewValue.setText(getTotal());

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
        ((LinearLayout)mView.findViewById(R.id.numbers)).setBackgroundResource(mNumberBackgroundColor);

        ((LinearLayout)mView.findViewById(R.id.operators)).setBackgroundResource(mOperatorBackgroundColor);

        int dialogButtonsColor = ContextCompat.getColor(getContext(), mDialogButtonsColor);
        ((Button)mView.findViewById(R.id.buttonCancel)).setTextColor(dialogButtonsColor);
        ((Button)mView.findViewById(R.id.buttonOk)).setTextColor(dialogButtonsColor);

        int numberColor = ContextCompat.getColor(getContext(), mNumberColor);
        ((Button)mView.findViewById(R.id.button09)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button08)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button07)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button06)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button05)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button04)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button03)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button02)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button01)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.button00)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.buttonEqual)).setTextColor(numberColor);
        ((Button)mView.findViewById(R.id.buttonPoint)).setTextColor(numberColor);

        int operationColor = ContextCompat.getColor(getContext(), mOperationColor);


        ((ImageButton)mView.findViewById(R.id.imageButtonDel)).setColorFilter(operationColor);


        ((Button)mView.findViewById(R.id.buttonDivision)).setTextColor(operationColor);
        ((Button)mView.findViewById(R.id.buttonMultiplication)).setTextColor(operationColor);
        ((Button)mView.findViewById(R.id.buttonSubtraction)).setTextColor(operationColor);
        ((Button)mView.findViewById(R.id.buttonSum)).setTextColor(operationColor);

    }

    public void showDialog(FragmentManager fragmentManager, String tag, double value) {

        mStringList.clear();

        if (value < 0) {
            // TODO Show error

        } else if (value == 0) {
            //mStringList.add(new Character("0", null, Character.Type.NUMBER));

        } else if (value > 0) {
            // set value in list
            setDoubleInList(value);
        }

        // Show dialog
        show(fragmentManager, tag);
    }

    @Override
    public void onClick(View v) {

        String str0 = "0", str1 = "1", str2 = "2", str3 = "3", str4 = "4", str5 = "5", str6 = "6", str7 = "7", str8 = "8", str9 = "9";

        if (v.getId() == R.id.buttonCancel) {
            this.dismiss();
            return;
        }
        if (v.getId() == R.id.buttonOk) {
            mListener.onCalculatorDialogResponse(mName, mTextViewValue.getText().toString());
            this.dismiss();
            return;
        }
        if (v.getId() == R.id.buttonEqual) {
            mStringList.clear();
            setDoubleInList(mValue);
            mTextViewOperation.setText(getOperation());
            mTextViewValue.setText("");
            return;
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
        mTextViewValue.setText(getTotal());

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

    private void addOperator(Character.Operation operation) {

        if (mStringList.size() > 0) {
            Character tmp = mStringList.get(mStringList.size() - 1);
            if (tmp.getType() == Character.Type.NUMBER) {
                mStringList.add(new Character(null, operation, Character.Type.OPERATOR));
            }
        }
    }

    private void addNumber(String s) {
        mStringList.add(new Character(s, null, Character.Type.NUMBER));
    }

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


    private String getOperation() {
        String result = "";
        Character.Type type;
        Character.Operation operation;

        for (int i = 0; i < mStringList.size(); i++) {

            type = mStringList.get(i).getType();

            if (type == Character.Type.NUMBER) {
                result = result + mStringList.get(i).getValue();
            }

            if (type == Character.Type.OPERATOR) {
                operation = mStringList.get(i).getOperation();

                if (operation == Character.Operation.SUM) {
                    result = result + " + ";
                } else if (operation == Character.Operation.SUBTRACTION) {
                    result = result + " - ";
                } else if (operation == Character.Operation.MULTIPLICATION) {
                    result = result + " * ";
                } else if (operation == Character.Operation.DIVISION) {
                    result = result + " / ";
                }
            }
        }

        result = result.replace(".", mSeparator);
        return result;
    }

    private String getTotal() {

        String result = "";

        if(mStringList.size() % 2 == 0){
            return String.valueOf(mValue);
        }

        Character.Type type01, type02;
        Character.Operation operation;
        List<Character> mStringListAux = new ArrayList<>();

        for(Character t : mStringList){
            Character copy = deepCopy(t);
            mStringListAux.add(copy);
        }

        int index = 0;
        double number01 = 0, number02 = 0, numberAux = 0;

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

            if (index >= mStringListAux.size()-1) {
                iteration = 2;
                index = 0;
            }
        }


        result = mStringListAux.get(0).getValue();
        mStringListAux.clear();
        mValue = Double.parseDouble(result);
        return result;

    }

    public Character deepCopy(Character input){
        Character copy = new Character(input.getValue(), input.getOperation(), input.getType());
        return copy;
    }

    private void setDoubleInList(double number) {
        String strNumber = "" + number;

        BigDecimal bd = new BigDecimal(strNumber);
        BigDecimal bdInt = new BigDecimal(bd.intValue());
        BigDecimal bdDec = bd.subtract(bdInt);

        for (int i = 0; i < bdInt.toString().length(); i++) {
            String character = bdInt.toString().substring(i, i + 1);
            updateNumber(character, false);
        }

        // Ignore two first characters (0.)
        if (bdDec.doubleValue() > 0) {

            updateNumber(".", true);

            for (int i = 2; i < bdDec.toString().length(); i++) {
                String character = bdDec.toString().substring(i, i + 1);
                updateNumber(character, false);
            }
        }
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
        mTextViewValue.setText(getTotal());

        return false;
    }


    public interface ICalculatorDialogClick {
        void onCalculatorDialogResponse(String colorSelectorName, String value);
    }
}
