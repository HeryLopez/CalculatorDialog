package com.app.herysapps.calculatordialoglib;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private List<Character> operations = new ArrayList<>();
    private String separator;

    public Calculator(String separator) {
        this.separator = separator;
    }

    public void clearOperations() {
        operations.clear();
    }

    public void addOperator(Operation operation) {
        if (operations.size() > 0) {
            Character tmp = operations.get(operations.size() - 1);
            if (tmp.getType() == Type.NUMBER) {
                operations.add(new Character(null, operation, Type.OPERATOR));
            }
        }
    }

    public void deleteAll(){
        operations.clear();
    }

    // Delete
    public void deleteNumber() {
        if (operations.size() > 0) {
            Character character = operations.get(operations.size() - 1);
            Type type = character.getType();

            if (type == Type.NUMBER) {
                String value = character.getValue();

                if (value.length() <= 1) {
                    operations.remove(operations.size() - 1);
                } else {
                    character.setValue(value.substring(0, value.length() - 1));
                    operations.set(operations.size() - 1, character);
                }
            }

            if (type == Type.OPERATOR) {
                operations.remove(operations.size() - 1);
            }
        }
    }

    private void addNumber(String s) {
        operations.add(new Character(s, null, Type.NUMBER));
    }

    // Numbers
    public void updateNumber(String s, boolean isPoint) {
        if (operations.size() <= 0) {
            addNumber(s);
        } else {
            Character tmp = operations.get(operations.size() - 1);
            if (tmp.getType() == Type.NUMBER) {
                if (isPoint) {
                    // If there is one point separator
                    if (tmp.getValue().contains(s)) {
                        return;
                    }
                }

                if(tmp.getValue().equals("0")){
                    tmp.setValue(s);
                } else {
                    tmp.setValue(tmp.getValue() + s);
                }
                operations.set(operations.size() - 1, tmp);
            } else {
                if (!isPoint) {
                    addNumber(s);
                }
            }
        }
    }

    public String getNumberWithSeparation(String number) {
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

    public String getOperation() {
        String result = "";
        Type type;
        Operation operation;

        for (int i = 0; i < operations.size(); i++) {

            type = operations.get(i).getType();

            if (type == Type.NUMBER) {
                result = result + getNumberWithSeparation(operations.get(i).getValue());
            }

            if (type == Type.OPERATOR) {
                operation = operations.get(i).getOperation();

                if (operation == Operation.SUM) {
                    result = result + " + ";
                } else if (operation == Operation.SUBTRACTION) {
                    result = result + " - ";
                } else if (operation == Operation.MULTIPLICATION) {
                    result = result + " × ";
                } else if (operation == Operation.DIVISION) {
                    result = result + " ÷ ";
                }
            }
        }

        result = result.replace(".", separator);

        return result;
    }

    public String getTotal() {

        String result = "";

        if (operations.isEmpty()) {
            return result;
        }

        Type type01, type02;
        Operation operation;
        List<Character> mStringListAux = new ArrayList<>();

        int itemsCount;
        if (operations.size() % 2 == 0) {
            itemsCount = operations.size() - 1;
        } else {
            itemsCount = operations.size();
        }

        for (int i = 0; i < itemsCount; i++) {
            Character copy = new Character(operations.get(i).getValue(), operations.get(i).getOperation(), operations.get(i).getType());
            mStringListAux.add(copy);
        }

        int index = 0;
        double number01, number02, numberAux;

        int iteration = 1;
        while (mStringListAux.size() > 1) {

            type01 = mStringListAux.get(index).getType();

            if (type01 == Type.NUMBER) {
                type02 = mStringListAux.get(index + 1).getType();

                if (type02 == Type.OPERATOR) {
                    operation = mStringListAux.get(index + 1).getOperation();

                    if (operation == Operation.SUM) {
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
                    } else if (operation == Operation.SUBTRACTION) {
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
                    } else if (operation == Operation.MULTIPLICATION) {
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

                    } else if (operation == Operation.DIVISION) {
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

    public String getTotalInString() {
        String value = getTotal();
        BigDecimal bd = new BigDecimal(!value.equals("") ? value : "0");
        return bd.toPlainString();
    }

    public double getTotalInDouble() {
        String total = getTotal();
        double result = 0;
        if (!total.equals("")) {
            result = Double.parseDouble(total);
        }
        return result;
    }

    public void setDoubleInList(String strNumber) {
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

}
