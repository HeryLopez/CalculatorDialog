package com.app.herysapps.calculatordialoglib;

class Character {

    enum Operation {DIVISION, MULTIPLICATION, SUBTRACTION, SUM}
    enum Type {NUMBER, OPERATOR }

    private String mValue;
    private Character.Operation mOperation;
    private Character.Type mType;

    Character(String value, Character.Operation operation, Character.Type type){
        mValue = value;
        mOperation = operation;
        mType = type;
    }

    void setValue(String value){
        mValue = value;
    }

    String getValue() {
        return mValue;
    }

    Character.Type getType() {
        return mType;
    }

    Character.Operation getOperation(){
        return mOperation;
    }
}
