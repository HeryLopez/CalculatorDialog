package com.app.herysapps.calculatordialoglib;

/**
 * Created by Hery on 10/08/2017.
 */

public class Character {

    public enum Operation {DIVISION, MULTIPLICATION, SUBTRACTION, SUM};
    public enum Type {NUMBER, OPERATOR };

    private String mValue;
    private Character.Operation mOperation;
    private Character.Type mType;

    public Character(String value, Character.Operation operation, Character.Type type){
        mValue = value;
        mOperation = operation;
        mType = type;
    }

    public void setValue(String value){
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }


    public Character.Type getType() {
        return mType;
    }

    public Character.Operation getOperation(){
        return mOperation;
    }
}
