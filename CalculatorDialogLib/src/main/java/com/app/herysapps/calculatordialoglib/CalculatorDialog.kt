package com.app.herysapps.calculatordialoglib

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.core.content.ContextCompat
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * Github: [https://github.com/HeryLopez/CalculatorDialog](https://github.com/HeryLopez/CalculatorDialog)
 */
class CalculatorDialog : DialogFragment(), View.OnClickListener, View.OnLongClickListener {

    lateinit var mListener: OnDialogResultListener
    lateinit var tagDialog: String

    var mDecor: String? = null
    var mNumberColor : Int = R.color.numberColor
    var mOperationColor : Int = R.color.operatorColor
    var mNumberBackgroundColor : Int = R.color.numberBackgroundColor
    var mOperatorBackgroundColor : Int = R.color.operatorBackgroundColor
    var mDialogButtonsColor : Int = R.color.dialogButtons
    var mLimitNumbers : Int = 0
    var mLimitNegativeNumbers : Boolean = false
    var mErrorDiv0: String = "Division by 0 impossible"
    var mErrorLimitNumber: String = "Number limit exceeded"
    var mErrorNegativeValue: String = "Negative numbers disabled"

    private lateinit var mTextViewValue: TextView
    private lateinit var mTextViewOperation: TextView
    private var horizontalScrollView: HorizontalScrollView? = null

    private val mSeparator: String
    private val calculator: Calculator

    init {
        val format = DecimalFormat.getInstance() as DecimalFormat
        val symbols = format.decimalFormatSymbols

        // Separator
        val sep = symbols.decimalSeparator
        mSeparator = sep.toString()

        // Decor
        mDecor = symbols.currencySymbol

        // Calculator
        calculator = Calculator(mSeparator)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(NAME, tagDialog)
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

        val tmp = calculator.totalInString
        outState.putString(VALUE, tmp)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Get information the instance if there is.
        if (savedInstanceState != null) {
            tagDialog = savedInstanceState.getString(NAME)
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
            calculator.setDoubleInList(value)
        }

        // Build the AlertDialog
        val builderCurrency = AlertDialog.Builder(activity)
        builderCurrency.setTitle("")


        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.activity_calculator_dialog_adapter, null)

        initializeInterface(view)

        builderCurrency.setView(view)

        val alertCurrency = builderCurrency.create()

        alertCurrency.setCanceledOnTouchOutside(true)
        alertCurrency.setCancelable(true)

        return alertCurrency
    }

    private fun initializeInterface(view: View) {
        mTextViewOperation = view.findViewById(R.id.textViewValueOperation) as TextView
        mTextViewValue = view.findViewById(R.id.textViewValue) as TextView

        view.findViewById<TextView>(R.id.textViewSymbol).text = mDecor

        mTextViewOperation.text = calculator.operation
        mTextViewValue.text = getTotalToShow()

        mTextViewOperation.movementMethod = ScrollingMovementMethod()
        horizontalScrollView = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView

        view.findViewById<TextView>(R.id.button00).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button01).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button02).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button03).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button04).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button05).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button06).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button07).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button08).setOnClickListener(this)
        view.findViewById<TextView>(R.id.button09).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonEqual).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonPoint).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.imageButtonDel).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonDivision).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonMultiplication).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonSubtraction).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonSum).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonCancel).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonOk).setOnClickListener(this)

        view.findViewById<ImageView>(R.id.imageButtonDel).setOnLongClickListener {
            calculator.deleteAll()
            updateUI()
            true
        }

        // -----------------------------------------------------------------------------------------
        // Colors
        // -----------------------------------------------------------------------------------------
        val dialogButtonsColor = ContextCompat.getColor(context!!, mDialogButtonsColor)
        val numberColor = ContextCompat.getColor(context!!, mNumberColor)
        val operationColor = ContextCompat.getColor(context!!, mOperationColor)


        view.findViewById<LinearLayout>(R.id.numbers).setBackgroundResource(mNumberBackgroundColor)
        view.findViewById<LinearLayout>(R.id.operators).setBackgroundResource(mOperatorBackgroundColor)

        view.findViewById<TextView>(R.id.buttonCancel).setTextColor(dialogButtonsColor)
        view.findViewById<TextView>(R.id.buttonOk).setTextColor(dialogButtonsColor)

        (view.findViewById(R.id.button09) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button08) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button07) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button06) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button05) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button04) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button03) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button02) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button01) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.button00) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.buttonEqual) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.buttonPoint) as TextView).setTextColor(numberColor)
        (view.findViewById(R.id.buttonPoint) as TextView).text = mSeparator

        (view.findViewById(R.id.imageButtonDel) as ImageView).setColorFilter(operationColor)
        (view.findViewById(R.id.buttonDivision) as TextView).setTextColor(operationColor)
        (view.findViewById(R.id.buttonMultiplication) as TextView).setTextColor(operationColor)
        (view.findViewById(R.id.buttonSubtraction) as TextView).setTextColor(operationColor)
        (view.findViewById(R.id.buttonSum) as TextView).setTextColor(operationColor)
    }

    /**
     * Show the dialog et set the initial value.
     *
     * @param fragmentManager see [DialogFragment.show].
     * @param tagDialog             see [DialogFragment.show].
     * @param value           initial value
     */
    fun showDialog(fragmentManager: FragmentManager, tagDialog: String, value: Double) {
        this.tagDialog = tagDialog
        calculator.clearOperations()
        calculator.setDoubleInList("" + value)

        // Show dialog
        show(fragmentManager, tagDialog)
    }

    override fun onClick(v: View) {
        val total = calculator.total
        val thereIsNotError = !isInfinity(total) && !isInvalidLimit(total) && !isNegativeNumber(total)

        when {
            v.id == R.id.buttonCancel -> {
                this.dismiss()
                return
            }
            v.id == R.id.buttonOk -> if (thereIsNotError) {
                mListener.onDialogResult(tagDialog, calculator.totalInDouble, getTotalToShow())
                this.dismiss()
                return
            }
            v.id == R.id.buttonEqual -> if (thereIsNotError) {
                val tmp = calculator.totalInString
                calculator.clearOperations()
                calculator.setDoubleInList(tmp)
                mTextViewOperation.text = calculator.operation
                mTextViewValue.text = ""
                return
            }
            v.id == R.id.imageButtonDel -> calculator.deleteNumber()
            v.id == R.id.buttonDivision -> calculator.addOperator(Operation.DIVISION)
            v.id == R.id.buttonMultiplication -> calculator.addOperator(Operation.MULTIPLICATION)
            v.id == R.id.buttonSubtraction -> calculator.addOperator(Operation.SUBTRACTION)
            v.id == R.id.buttonSum -> calculator.addOperator(Operation.SUM)
            v.id == R.id.buttonPoint -> calculator.updateNumber(".", true)
            v.id == R.id.button09 -> calculator.updateNumber("9", false)
            v.id == R.id.button08 -> calculator.updateNumber("8", false)
            v.id == R.id.button07 -> calculator.updateNumber("7", false)
            v.id == R.id.button06 -> calculator.updateNumber("6", false)
            v.id == R.id.button05 -> calculator.updateNumber("5", false)
            v.id == R.id.button04 -> calculator.updateNumber("4", false)
            v.id == R.id.button03 -> calculator.updateNumber("3", false)
            v.id == R.id.button02 -> calculator.updateNumber("2", false)
            v.id == R.id.button01 -> calculator.updateNumber("1", false)
            v.id == R.id.button00 -> calculator.updateNumber("0", false)
        }

        updateUI()
    }

    private fun updateUI(){
        mTextViewOperation.text = calculator.operation
        mTextViewValue.text = getTotalToShow()

        fullScroll()
    }

    private fun fullScroll() {
        horizontalScrollView!!.postDelayed({
            //replace this line to scroll up or down
            horizontalScrollView!!.fullScroll(View.FOCUS_RIGHT)
        }, 100L)
    }

    private fun getTotalToShow() : String{
        val result: String
        var total = calculator.total
        when {
            isInfinity(total) -> result = mErrorDiv0
            isInvalidLimit(total) -> result = mErrorLimitNumber
            isNegativeNumber(total) -> result = mErrorNegativeValue
            else -> {
                val bd = BigDecimal(if (total != "") total else "0")
                total = bd.toPlainString()
                result = calculator.getNumberWithSeparation(total)
            }
        }

        return result.replace(".", mSeparator)
    }


    private fun isInfinity(total: String): Boolean {

        if (total == "")
            return false

        return java.lang.Double.parseDouble(total) == java.lang.Double.POSITIVE_INFINITY || java.lang.Double.parseDouble(total) == java.lang.Double.POSITIVE_INFINITY
    }

    private fun isInvalidLimit(total: String): Boolean {

        return mLimitNumbers > 0 && total.length > mLimitNumbers
    }

    private fun isNegativeNumber(total: String): Boolean {

        if (!mLimitNegativeNumbers)
            return false

        if (total == "")
            return false

        return java.lang.Double.parseDouble(total) < 0
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
        result = if (partDec == "0") {
            newStr
        } else {
            "$newStr.$partDec"
        }


        result = result.replace(".", mSeparator)

        return result
    }

    override fun onLongClick(v: View): Boolean {
        calculator.clearOperations()
        mTextViewOperation.text = calculator.operation
        mTextViewValue.text = getTotalToShow()
        return false
    }


    /**
     * Interface for pattern observer
     */
    interface OnDialogResultListener {
        fun onDialogResult(tagDialog: String, value: Double, valueStr: String)
    }

    companion object {

        private const val NAME = "NAME"
        private const val DECOR = "DECOR"
        private const val NUMBER_COLOR = "NUMBER_COLOR"
        private const val OPERATION_COLOR = "OPERATION_COLOR"
        private const val NUMBER_BACK_COLOR = "NUMBER_BACK_COLOR"
        private const val OPERATION_BACK_COLOR = "OPERATION_BACK_COLOR"
        private const val DIALOG_BUTTONS_COLOR = "DIALOG_BUTTONS_COLOR"
        private const val LIMIT_NUMBER = "LIMIT_NUMBER"
        private const val NEGATIVE_NUMBERS = "NEGATIVE_NUMBERS"
        private const val ERROR_DIV_0 = "ERROR_DIV_0"
        private const val ERROR_LIMIT = "ERROR_LIMIT"
        private const val ERROR_NEGATIVE_NUMBERS = "ERROR_NEGATIVE_NUMBERS"
        private const val VALUE = "VALUE"
    }
}
