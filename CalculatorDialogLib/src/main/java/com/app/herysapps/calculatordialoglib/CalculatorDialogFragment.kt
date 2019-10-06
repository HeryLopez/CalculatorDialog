package com.app.herysapps.calculatordialoglib

import android.widget.*

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.core.content.ContextCompat

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.ArrayList

/**
 * A calculator dialog for Android..
 *
 *
 * Created by Hery Lopez on 15/08/2017.
 *
 *
 * Github:
 *
 *
 * Author: [https://github.com/HeryLopez](https://github.com/HeryLopez/)
 * <br></br>Project:  [https://github.com/HeryLopez/CalculatorDialog](https://github.com/HeryLopez/CalculatorDialog)
 */
class CalculatorDialogFragment : DialogFragment(), View.OnClickListener, View.OnLongClickListener {

    private var mListener: ICalculatorDialogClick? = null


    private var mView: View? = null
    private var mTextViewValue: TextView? = null
    private var mTextViewOperation: TextView? = null
    private var horizontalScrollView: HorizontalScrollView? = null

    private val mStringList = ArrayList<Character>()
    private var mName: String? = ""
    private var mDecor: String? = ""
    private var mNumberColor: Int = 0
    private var mOperationColor: Int = 0
    private var mNumberBackgroundColor: Int = 0
    private var mOperatorBackgroundColor: Int = 0
    private var mDialogButtonsColor: Int = 0

    private var mLimitNumbers = 0
    private var mLimitNegativeNumbers: Boolean = false
    private var mErrorDiv0: String? = null
    private var mErrorLimitNumber: String? = null
    private var mErrorNegativeValue: String? = null

    // private
    private val mSeparator: String


    private val operation: String
        get() {
            var result = ""
            var type: Type
            var operation: Operation?

            for (i in mStringList.indices) {

                type = mStringList[i].type

                if (type === Type.NUMBER) {
                    result += getNumberWithSeparation(mStringList[i].value!!)
                }

                if (type === Type.OPERATOR) {
                    operation = mStringList[i].operation

                    if (operation === Operation.SUM) {
                        result = "$result + "
                    } else if (operation === Operation.SUBTRACTION) {
                        result = "$result - "
                    } else if (operation === Operation.MULTIPLICATION) {
                        result = "$result × "
                    } else if (operation === Operation.DIVISION) {
                        result = "$result ÷ "
                    }
                }
            }

            result = result.replace(".", mSeparator)

            return result
        }


    private val total: String?
        get() {

            var result: String? = ""

            if (mStringList.isEmpty()) {
                return result
            }

            var type01: Type
            var type02: Type
            var operation: Operation?
            val mStringListAux = ArrayList<Character>()

            val itemsCount: Int
            if (mStringList.size % 2 == 0) {
                itemsCount = mStringList.size - 1
            } else {
                itemsCount = mStringList.size
            }

            for (i in 0 until itemsCount) {
                val copy = Character(mStringList[i].value, mStringList[i].operation, mStringList[i].type)
                mStringListAux.add(copy)
            }

            var index = 0
            var number01: Double
            var number02: Double
            var numberAux: Double

            var iteration = 1
            while (mStringListAux.size > 1) {

                type01 = mStringListAux[index].type

                if (type01 === Type.NUMBER) {
                    type02 = mStringListAux[index + 1].type

                    if (type02 === Type.OPERATOR) {
                        operation = mStringListAux[index + 1].operation

                        if (operation === Operation.SUM) {
                            if (iteration == 1) {
                                index = index + 2
                            }
                            if (iteration == 2) {
                                number01 = java.lang.Double.parseDouble(mStringListAux[index].value!!)
                                number02 = java.lang.Double.parseDouble(mStringListAux[index + 2].value!!)
                                numberAux = number01 + number02

                                val `object` = mStringListAux[index]
                                `object`.value = numberAux.toString()
                                mStringListAux[index] = `object`
                                mStringListAux.removeAt(index + 2)
                                mStringListAux.removeAt(index + 1)
                            }
                        } else if (operation === Operation.SUBTRACTION) {
                            if (iteration == 1) {
                                index = index + 2
                            }
                            if (iteration == 2) {
                                number01 = java.lang.Double.parseDouble(mStringListAux[index].value!!)
                                number02 = java.lang.Double.parseDouble(mStringListAux[index + 2].value!!)
                                numberAux = number01 - number02

                                val `object` = mStringListAux[index]
                                `object`.value = numberAux.toString()
                                mStringListAux[index] = `object`
                                mStringListAux.removeAt(index + 2)
                                mStringListAux.removeAt(index + 1)
                            }
                        } else if (operation === Operation.MULTIPLICATION) {
                            if (iteration == 1) {
                                number01 = java.lang.Double.parseDouble(mStringListAux[index].value!!)
                                number02 = java.lang.Double.parseDouble(mStringListAux[index + 2].value!!)
                                numberAux = number01 * number02

                                val `object` = mStringListAux[index]
                                `object`.value = numberAux.toString()
                                mStringListAux[index] = `object`
                                mStringListAux.removeAt(index + 2)
                                mStringListAux.removeAt(index + 1)
                            }

                        } else if (operation === Operation.DIVISION) {
                            if (iteration == 1) {
                                number01 = java.lang.Double.parseDouble(mStringListAux[index].value!!)
                                number02 = java.lang.Double.parseDouble(mStringListAux[index + 2].value!!)
                                numberAux = number01 / number02

                                val `object` = mStringListAux[index]
                                `object`.value = numberAux.toString()
                                mStringListAux[index] = `object`
                                mStringListAux.removeAt(index + 2)
                                mStringListAux.removeAt(index + 1)
                            }

                        }
                    }
                }

                if (index >= mStringListAux.size - 1) {
                    iteration = 2
                    index = 0
                }
            }

            result = mStringListAux[0].value

            mStringListAux.clear()
            return result
        }

    private val totalInString: String
        get() {
            val value = total
            val bd = BigDecimal(if (value != "") value else "0")
            return bd.toPlainString()
        }

    private val totalInDouble: Double
        get() {
            val total = total
            var result = 0.0
            if (total != "") {
                result = java.lang.Double.parseDouble(total!!)
            }
            return result
        }

    private val totalToShow: String
        get() {

            val result: String?
            var total = total

            if (IsInfinity(total!!)) {
                result = mErrorDiv0
            } else if (IsInvalidLimit(total)) {
                result = mErrorLimitNumber
            } else if (IsNegativeNumber(total)) {
                result = mErrorNegativeValue
            } else {
                val bd = BigDecimal(if (total != "") total else "0")
                total = bd.toPlainString()

                result = getNumberWithSeparation(total!!)
            }

            return result!!.replace(".", mSeparator)
        }

    init {
        val format = DecimalFormat.getInstance() as DecimalFormat
        val symbols = format.decimalFormatSymbols
        val sep = symbols.decimalSeparator

        val deco = symbols.currencySymbol

        mSeparator = sep.toString()
        mDecor = deco

        mNumberColor = R.color.numberColor
        mOperationColor = R.color.operatorColor
        mNumberBackgroundColor = R.color.numberBackgroundColor
        mOperatorBackgroundColor = R.color.operatorBackgroundColor
        mDialogButtonsColor = R.color.dialogButtons

        mLimitNumbers = 0
        mLimitNegativeNumbers = false

        mErrorDiv0 = "Division by 0 impossible"
        mErrorLimitNumber = "Number limit exceeded"
        mErrorNegativeValue = "Negative numbers disabled"
    }

    /**
     * Set an identifier that allows identifier the instance in the notification of listeners.
     * (For multiples dialogs in screen)
     *
     * @param name identifier of calculator dialog
     */
    fun setName(name: String) {
        this.mName = name
    }

    /**
     * Show the symbol for decoration of the screen
     *
     * @param decor Symbol
     */
    fun setDecor(decor: String) {
        mDecor = decor
    }

    /**
     * Set the color for the numbers
     *
     * @param numberColor color resource
     */
    fun setNumberColor(numberColor: Int) {
        this.mNumberColor = numberColor
    }

    /**
     * Set the color for the operators
     *
     * @param operationColor color resource
     */
    fun setOperationColor(operationColor: Int) {
        this.mOperationColor = operationColor
    }

    /**
     * Set the background color for the numbers
     *
     * @param numberBackgroundColor color resource
     */
    fun setNumberBackgroundColor(numberBackgroundColor: Int) {
        this.mNumberBackgroundColor = numberBackgroundColor
    }

    /**
     * Set the background color for the operators
     *
     * @param operatorBackgroundColor color resource
     */
    fun setOperatorBackgroundColor(operatorBackgroundColor: Int) {
        this.mOperatorBackgroundColor = operatorBackgroundColor
    }

    /**
     * Set the color for the dialog buttons
     *
     * @param dialogButtonsColor color resource
     */
    fun setDialogButtonsColor(dialogButtonsColor: Int) {
        this.mDialogButtonsColor = dialogButtonsColor
    }

    /**
     * Set the limit for the length of the numbers. The value must be greater than or equal to zero.
     *
     * @param limit limit
     */
    fun limitNumbers(limit: Int) {

        if (limit < 0) {
            throw RuntimeException("The limit must be greater than or equal to zero.")
        }
        this.mLimitNumbers = limit
    }

    fun negativeNumberActivated(b: Boolean) {
        mLimitNegativeNumbers = b
    }

    /**
     * Message to show in the division by zero error.
     *
     * @param message Text of error
     */
    fun setErrorDiv0(message: String) {
        this.mErrorDiv0 = message
    }

    /**
     * Message to show when the limit of number is exceeded
     *
     * @param message Text of error
     */
    fun setErrorLimit(message: String) {
        this.mErrorLimitNumber = message
    }

    /**
     * Message to show when there are negative numbers and they are disabled
     *
     * @param message Text of error
     */
    fun setErrorNegativeValue(message: String) {
        this.mErrorNegativeValue = message
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(NAME, mName)
        outState.putString(DECOR, mDecor)
        outState.putInt(NUMBER_COLOR, mNumberColor)
        outState.putInt(OPERATION_COLOR, mOperationColor)
        outState.putInt(NUMBER_BACK_COLOR, mNumberBackgroundColor)
        outState.putInt(OPERATION_BACK_COLOR, mOperatorBackgroundColor)
        outState.putInt(DIALOG_BUTTONS_COLOR, mDialogButtonsColor)
        outState.putInt(LIMIT_NUMBER, mLimitNumbers)
        outState.putBoolean(NEGATIVE_NUMBERS, mLimitNegativeNumbers)
        outState.putString(ERROR_DIV_0, mErrorDiv0)
        outState.putString(ERROR_LIMIT, mErrorLimitNumber)
        outState.putString(ERROR_NEGATIVE_NUMBERS, mErrorNegativeValue)

        val tmp = totalInString
        outState.putString(VALUE, tmp)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Get information the instance if there is.
        if (savedInstanceState != null) {
            mName = savedInstanceState.getString(NAME)
            mDecor = savedInstanceState.getString(DECOR)
            mNumberColor = savedInstanceState.getInt(NUMBER_COLOR)
            mOperationColor = savedInstanceState.getInt(OPERATION_COLOR)
            mNumberBackgroundColor = savedInstanceState.getInt(NUMBER_BACK_COLOR)
            mOperatorBackgroundColor = savedInstanceState.getInt(OPERATION_BACK_COLOR)
            mDialogButtonsColor = savedInstanceState.getInt(DIALOG_BUTTONS_COLOR)
            mLimitNumbers = savedInstanceState.getInt(LIMIT_NUMBER)
            mLimitNegativeNumbers = savedInstanceState.getBoolean(NEGATIVE_NUMBERS)
            mErrorDiv0 = savedInstanceState.getString(ERROR_DIV_0)
            mErrorLimitNumber = savedInstanceState.getString(ERROR_LIMIT)
            mErrorNegativeValue = savedInstanceState.getString(ERROR_NEGATIVE_NUMBERS)

            val value = savedInstanceState.getString(VALUE)
            setDoubleInList(value)
        }

        // Build the AlertDialog
        val builderCurrency = AlertDialog.Builder(activity)
        builderCurrency.setTitle("")

        val inflater = activity!!.layoutInflater
        mView = inflater.inflate(R.layout.activity_calculator_dialog_adapter, null)

        InitializeInterface()

        builderCurrency.setView(mView)

        val alertCurrency = builderCurrency.create()

        alertCurrency.setCanceledOnTouchOutside(true)
        alertCurrency.setCancelable(true)

        return alertCurrency
    }

    private fun InitializeInterface() {
        mTextViewOperation = mView!!.findViewById(R.id.textViewValueOperation) as TextView
        mTextViewValue = mView!!.findViewById(R.id.textViewValue) as TextView

        (mView!!.findViewById(R.id.textViewSymbol) as TextView).text = mDecor

        mTextViewOperation!!.text = operation
        mTextViewValue!!.text = totalToShow

        mTextViewOperation!!.movementMethod = ScrollingMovementMethod()
        horizontalScrollView = mView!!.findViewById(R.id.horizontalScroll) as HorizontalScrollView


        mView!!.findViewById<Button>(R.id.button09).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button08).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button07).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button06).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button05).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button04).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button03).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button02).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button01).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.button00).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.buttonEqual).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.buttonPoint).setOnClickListener(this)

        mView!!.findViewById<ImageButton>(R.id.imageButtonDel).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.buttonDivision).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.buttonMultiplication).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.buttonSubtraction).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.buttonSum).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.buttonCancel).setOnClickListener(this)
        mView!!.findViewById<Button>(R.id.buttonOk).setOnClickListener(this)

        mView!!.findViewById<ImageButton>(R.id.imageButtonDel).setOnLongClickListener(this)

        // -----------------------------------------------------------------------------------------
        // Colors
        // -----------------------------------------------------------------------------------------
        mView!!.findViewById<LinearLayout>(R.id.numbers).setBackgroundResource(mNumberBackgroundColor)

        mView!!.findViewById<LinearLayout>(R.id.operators).setBackgroundResource(mOperatorBackgroundColor)

        val dialogButtonsColor = ContextCompat.getColor(context!!, mDialogButtonsColor)
        (mView!!.findViewById(R.id.buttonCancel) as Button).setTextColor(dialogButtonsColor)
        (mView!!.findViewById(R.id.buttonOk) as Button).setTextColor(dialogButtonsColor)

        val numberColor = ContextCompat.getColor(context!!, mNumberColor)
        (mView!!.findViewById(R.id.button09) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button08) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button07) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button06) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button05) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button04) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button03) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button02) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button01) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.button00) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.buttonEqual) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.buttonPoint) as Button).setTextColor(numberColor)
        (mView!!.findViewById(R.id.buttonPoint) as Button).text = mSeparator


        val operationColor = ContextCompat.getColor(context!!, mOperationColor)


        (mView!!.findViewById(R.id.imageButtonDel) as ImageButton).setColorFilter(operationColor)


        (mView!!.findViewById(R.id.buttonDivision) as Button).setTextColor(operationColor)
        (mView!!.findViewById(R.id.buttonMultiplication) as Button).setTextColor(operationColor)
        (mView!!.findViewById(R.id.buttonSubtraction) as Button).setTextColor(operationColor)
        (mView!!.findViewById(R.id.buttonSum) as Button).setTextColor(operationColor)

    }

    /**
     * Show the dialog et set the initial value.
     *
     * @param fragmentManager see [DialogFragment.show].
     * @param tag             see [DialogFragment.show].
     * @param value           initial value
     */
    fun showDialog(fragmentManager: FragmentManager, tag: String, value: Double) {

        if (mName == "") {
            throw RuntimeException("CalculatorDialog instance must initialize the Name property.")
        }

        // set value in list
        mStringList.clear()
        setDoubleInList("" + value)

        // Show dialog
        show(fragmentManager, tag)
    }

    override fun onClick(v: View) {

        val str0 = "0"
        val str1 = "1"
        val str2 = "2"
        val str3 = "3"
        val str4 = "4"
        val str5 = "5"
        val str6 = "6"
        val str7 = "7"
        val str8 = "8"
        val str9 = "9"
        val tV = total

        if (v.id == R.id.buttonCancel) {
            this.dismiss()
            return
        }

        if (!IsInfinity(tV!!) && !IsInvalidLimit(tV) && !IsNegativeNumber(tV)) {
            if (v.id == R.id.buttonOk) {
                mListener!!.onCalculatorDialogResponse(mName, totalInDouble, totalToShow)
                this.dismiss()
                return
            }

            if (v.id == R.id.buttonEqual) {
                val tmp = totalInString
                mStringList.clear()
                setDoubleInList(tmp)
                mTextViewOperation!!.text = operation
                mTextViewValue!!.text = ""
                return
            }
        }
        if (v.id == R.id.imageButtonDel) {
            deleteNumber()
        }


        if (v.id == R.id.buttonDivision) {
            addOperator(Operation.DIVISION)
        }
        if (v.id == R.id.buttonMultiplication) {
            addOperator(Operation.MULTIPLICATION)
        }
        if (v.id == R.id.buttonSubtraction) {
            addOperator(Operation.SUBTRACTION)
        }
        if (v.id == R.id.buttonSum) {
            addOperator(Operation.SUM)
        }


        if (v.id == R.id.buttonPoint) {
            updateNumber(".", true)
        }
        if (v.id == R.id.button09) {
            updateNumber(str9, false)
        }
        if (v.id == R.id.button08) {
            updateNumber(str8, false)
        }
        if (v.id == R.id.button07) {
            updateNumber(str7, false)
        }
        if (v.id == R.id.button06) {
            updateNumber(str6, false)
        }
        if (v.id == R.id.button05) {
            updateNumber(str5, false)
        }
        if (v.id == R.id.button04) {
            updateNumber(str4, false)
        }
        if (v.id == R.id.button03) {
            updateNumber(str3, false)
        }
        if (v.id == R.id.button02) {
            updateNumber(str2, false)
        }
        if (v.id == R.id.button01) {
            updateNumber(str1, false)
        }
        if (v.id == R.id.button00) {
            updateNumber(str0, false)
        }

        mTextViewOperation!!.text = operation
        mTextViewValue!!.text = totalToShow

        fullScroll()
    }

    private fun fullScroll() {
        horizontalScrollView!!.postDelayed({
            //replace this line to scroll up or down
            horizontalScrollView!!.fullScroll(View.FOCUS_RIGHT)
        }, 100L)
    }

    // Operators
    private fun addOperator(operation: Operation) {

        if (mStringList.size > 0) {
            val tmp = mStringList[mStringList.size - 1]
            if (tmp.type === Type.NUMBER) {
                mStringList.add(Character(null, operation, Type.OPERATOR))
            }
        }
    }

    // Delete
    private fun deleteNumber() {
        if (mStringList.size > 0) {
            val character = mStringList[mStringList.size - 1]
            val type = character.type

            if (type === Type.NUMBER) {

                val value = character.value

                if (value!!.length <= 1) {
                    mStringList.removeAt(mStringList.size - 1)
                } else {
                    character.value = value.substring(0, value.length - 1)
                    mStringList[mStringList.size - 1] = character
                }
            }

            if (type === Type.OPERATOR) {
                mStringList.removeAt(mStringList.size - 1)
            }
        }
    }

    // Numbers
    private fun updateNumber(s: String, isPoint: Boolean) {

        if (mStringList.size <= 0) {
            addNumber(s)
        } else {
            val tmp = mStringList[mStringList.size - 1]
            if (tmp.type === Type.NUMBER) {
                if (isPoint) {
                    val b = tmp.value!!.contains(s)
                    if (b) {
                        return
                    }
                }

                tmp.value = tmp.value!! + s
                mStringList[mStringList.size - 1] = tmp
            } else {
                if (!isPoint) {
                    addNumber(s)
                }
            }
        }


    }

    private fun addNumber(s: String) {
        mStringList.add(Character(s, null, Type.NUMBER))
    }

    private fun IsInfinity(total: String): Boolean {

        if (total == "")
            return false

        return java.lang.Double.parseDouble(total) == java.lang.Double.POSITIVE_INFINITY || java.lang.Double.parseDouble(total) == java.lang.Double.POSITIVE_INFINITY
    }

    private fun IsInvalidLimit(total: String): Boolean {

        return mLimitNumbers > 0 && total.length > mLimitNumbers
    }

    private fun IsNegativeNumber(total: String): Boolean {

        if (!mLimitNegativeNumbers)
            return false

        if (total == "")
            return false

        return java.lang.Double.parseDouble(total) < 0
    }

    private fun getNumberWithSeparation(number: String): String {

        val radixLoc = number.indexOf('.')
        val partInt: String
        val partDec: String
        if (radixLoc == -1) {
            partInt = number
            partDec = "0"
        } else {
            partInt = number.substring(0, radixLoc)
            partDec = number.substring(radixLoc + 1, number.length)
        }

        var newStr = ""
        var blockIndicator = 0
        for (i in partInt.length - 1 downTo 0) {

            val character = partInt.substring(i, i + 1)
            newStr = character + newStr

            if (blockIndicator == 2) {
                blockIndicator = 0
                newStr = " $newStr"
            } else {
                blockIndicator++
            }
        }

        val result: String
        result = if (partDec == "0") {
            newStr
        } else {
            "$newStr.$partDec"
        }

        return result
    }


    private fun setDoubleInList(strNumber: String?) {
        val bd = BigDecimal(strNumber!!)

        val strNum = bd.toPlainString()
        val radixLoc = strNum.indexOf('.')
        val partInt: String
        val partDec: String
        if (radixLoc == -1) {
            partInt = strNum
            partDec = "0"
        } else {
            partInt = strNum.substring(0, radixLoc)
            partDec = strNum.substring(radixLoc + 1, strNum.length)
        }

        for (i in 0 until partInt.length) {
            val character = partInt.substring(i, i + 1)
            updateNumber(character, false)
        }

        if (partDec != "0") {

            updateNumber(".", true)

            for (i in 0 until partDec.length) {
                val character = partDec.substring(i, i + 1)
                updateNumber(character, false)
            }
        }
    }

    fun getNumberWithFormat(num: Double): String {

        val number = num.toString()

        val radixLoc = number.indexOf('.')
        val partInt: String
        val partDec: String
        if (radixLoc == -1) {
            partInt = number
            partDec = "0"
        } else {
            partInt = number.substring(0, radixLoc)
            partDec = number.substring(radixLoc + 1, number.length)
        }

        var newStr = ""
        var blockIndicator = 0
        for (i in partInt.length - 1 downTo 0) {

            val character = partInt.substring(i, i + 1)
            newStr = character + newStr

            if (blockIndicator == 2) {
                blockIndicator = 0
                newStr = " $newStr"
            } else {
                blockIndicator++
            }
        }

        var result: String
        if (partDec == "0") {
            result = newStr
        } else {
            result = "$newStr.$partDec"
        }


        result = result.replace(".", mSeparator)

        return result
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ICalculatorDialogClick) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement CalculatorDialog.onCalculatorDialogResponse")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onLongClick(v: View): Boolean {

        mStringList.clear()

        mTextViewOperation!!.text = operation
        mTextViewValue!!.text = totalToShow

        return false
    }


    /**
     * Interface for pattern observer
     */
    interface ICalculatorDialogClick {
        fun onCalculatorDialogResponse(colorSelectorName: String?, value: Double, valueStr: String)
    }

    companion object {

        private val NAME = "NAME"
        private val DECOR = "DECOR"
        private val NUMBER_COLOR = "NUMBER_COLOR"
        private val OPERATION_COLOR = "OPERATION_COLOR"
        private val NUMBER_BACK_COLOR = "NUMBER_BACK_COLOR"
        private val OPERATION_BACK_COLOR = "OPERATION_BACK_COLOR"
        private val DIALOG_BUTTONS_COLOR = "DIALOG_BUTTONS_COLOR"
        private val LIMIT_NUMBER = "LIMIT_NUMBER"
        private val NEGATIVE_NUMBERS = "NEGATIVE_NUMBERS"
        private val ERROR_DIV_0 = "ERROR_DIV_0"
        private val ERROR_LIMIT = "ERROR_LIMIT"
        private val ERROR_NEGATIVE_NUMBERS = "ERROR_NEGATIVE_NUMBERS"

        private val VALUE = "VALUE"
    }
}
