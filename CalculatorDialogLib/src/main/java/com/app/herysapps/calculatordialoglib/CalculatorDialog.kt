package com.app.herysapps.calculatordialoglib

import android.app.Dialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.*
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * Github: [https://github.com/HeryLopez/CalculatorDialog](https://github.com/HeryLopez/CalculatorDialog)
 */
class CalculatorDialog : DialogFragment(), View.OnClickListener, View.OnLongClickListener {

    lateinit var mListener: OnDialogResultListener
    lateinit var tagDialog: String

    var mDecor: String? = null
    var mNumberColor: Int = R.color.numberColor
    var mOperationColor: Int = R.color.operatorColor
    var mNumberBackgroundColor: Int = R.color.numberBackgroundColor
    var mOperatorBackgroundColor: Int = R.color.operatorBackgroundColor
    var mOkBackgroundColor: Int = R.color.okBackgroundColor
    var mLimitNumbers: Int = 0
    var mLimitNegativeNumbers: Boolean = false
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
        outState.putInt(OK_BACK_COLOR, mOkBackgroundColor)
        outState.putInt(LIMIT_NUMBER, mLimitNumbers)
        outState.putBoolean(NEGATIVE_NUMBERS, mLimitNegativeNumbers)
        outState.putString(ERROR_DIV_0, mErrorDiv0)
        outState.putString(ERROR_LIMIT, mErrorLimitNumber)
        outState.putString(ERROR_NEGATIVE_NUMBERS, mErrorNegativeValue)

        val tmp = calculator.totalInString
        outState.putString(VALUE, tmp)
    }

    override fun onResume() {
        super.onResume()
        dialog?.let {
            val window = it.window
            window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
            window?.setGravity(Gravity.CENTER)
        }
    }

    private fun initVariablesFromSavedInstanceState(savedInstanceState: Bundle?) {
        // Get information the instance if there is.
        if (savedInstanceState != null) {
            tagDialog = savedInstanceState.getString(NAME)
            mDecor = savedInstanceState.getString(DECOR)
            mNumberColor = savedInstanceState.getInt(NUMBER_COLOR)
            mOperationColor = savedInstanceState.getInt(OPERATION_COLOR)
            mNumberBackgroundColor = savedInstanceState.getInt(NUMBER_BACK_COLOR)
            mOperatorBackgroundColor = savedInstanceState.getInt(OPERATION_BACK_COLOR)
            mOkBackgroundColor = savedInstanceState.getInt(OK_BACK_COLOR)
            mLimitNumbers = savedInstanceState.getInt(LIMIT_NUMBER)
            mLimitNegativeNumbers = savedInstanceState.getBoolean(NEGATIVE_NUMBERS)
            mErrorDiv0 = savedInstanceState.getString(ERROR_DIV_0)
            mErrorLimitNumber = savedInstanceState.getString(ERROR_LIMIT)
            mErrorNegativeValue = savedInstanceState.getString(ERROR_NEGATIVE_NUMBERS)

            val value = savedInstanceState.getString(VALUE)
            calculator.setDoubleInList(value)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initVariablesFromSavedInstanceState(savedInstanceState)

        val root = inflater.inflate(R.layout.activity_calculator_fullscreen_adapter, container, false)

        root.apply {
            setupResultComponents(this)
            setupNumberPad(this)
            setupOperatorPad(this)
        }

        dialog?.let {
            it.window?.requestFeature(Window.FEATURE_NO_TITLE)
            it.setCanceledOnTouchOutside(true)
        }

        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        return dialog
    }

    private fun setupResultComponents(view: View) {
        mTextViewOperation = view.findViewById(R.id.textViewValueOperation) as TextView
        mTextViewValue = view.findViewById(R.id.textViewValue) as TextView

        view.findViewById<TextView>(R.id.textViewSymbol).text = mDecor

        mTextViewOperation.text = calculator.operation
        mTextViewValue.text = getTotalToShow()

        mTextViewOperation.movementMethod = ScrollingMovementMethod()
        horizontalScrollView = view.findViewById(R.id.horizontalScroll) as HorizontalScrollView
    }

    private fun setupNumberPad(view: View) {
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

        val numberColor = ContextCompat.getColor(context!!, mNumberColor)

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


        view.findViewById<View>(R.id.numbers).setBackgroundResource(mNumberBackgroundColor)
    }

    private fun setupOperatorPad(view: View) {
        view.findViewById<ImageView>(R.id.imageButtonDel).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonDivision).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonMultiplication).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonSubtraction).setOnClickListener(this)
        view.findViewById<TextView>(R.id.buttonSum).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.imageButtonOk).setOnClickListener(this)

        view.findViewById<ImageView>(R.id.imageButtonDel).setOnLongClickListener {
            calculator.deleteAll()
            updateUI()
            true
        }

        val operationColor = ContextCompat.getColor(context!!, mOperationColor)

        (view.findViewById(R.id.imageButtonDel) as ImageView).setColorFilter(operationColor)
        (view.findViewById(R.id.buttonDivision) as TextView).setTextColor(operationColor)
        (view.findViewById(R.id.buttonMultiplication) as TextView).setTextColor(operationColor)
        (view.findViewById(R.id.buttonSubtraction) as TextView).setTextColor(operationColor)
        (view.findViewById(R.id.buttonSum) as TextView).setTextColor(operationColor)
        (view.findViewById(R.id.imageButtonOk) as ImageView).setColorFilter(operationColor)

        view.findViewById<View>(R.id.operators).setBackgroundResource(mOperatorBackgroundColor)
        view.findViewById<View>(R.id.imageButtonOkContainer).setBackgroundResource(mOkBackgroundColor)
    }

    fun setInitialValue(value: Double) {
        calculator.clearOperations()
        calculator.setDoubleInList("" + value)
    }

    fun setDialogId(tagDialog: String) {
        this.tagDialog = tagDialog
    }

    override fun onClick(v: View) {
        val total = calculator.total
        val thereIsNotError = !isInfinity(total) && !isInvalidLimit(total) && !isNegativeNumber(total)

        when (v.id) {
            R.id.imageButtonOk -> if (thereIsNotError) {
                mListener.onDialogResult(tagDialog, calculator.totalInDouble, getTotalToShow())
                this.dismiss()
                activity?.supportFragmentManager?.popBackStack()
                return
            }
            R.id.buttonEqual -> if (thereIsNotError) {
                val tmp = calculator.totalInString
                calculator.clearOperations()
                calculator.setDoubleInList(tmp)
                mTextViewOperation.text = calculator.operation
                mTextViewValue.text = ""
                return
            }
            R.id.imageButtonDel -> calculator.deleteNumber()
            R.id.buttonDivision -> calculator.addOperator(Operation.DIVISION)
            R.id.buttonMultiplication -> calculator.addOperator(Operation.MULTIPLICATION)
            R.id.buttonSubtraction -> calculator.addOperator(Operation.SUBTRACTION)
            R.id.buttonSum -> calculator.addOperator(Operation.SUM)
            R.id.buttonPoint -> calculator.updateNumber(".", true)
            R.id.button09 -> calculator.updateNumber("9", false)
            R.id.button08 -> calculator.updateNumber("8", false)
            R.id.button07 -> calculator.updateNumber("7", false)
            R.id.button06 -> calculator.updateNumber("6", false)
            R.id.button05 -> calculator.updateNumber("5", false)
            R.id.button04 -> calculator.updateNumber("4", false)
            R.id.button03 -> calculator.updateNumber("3", false)
            R.id.button02 -> calculator.updateNumber("2", false)
            R.id.button01 -> calculator.updateNumber("1", false)
            R.id.button00 -> calculator.updateNumber("0", false)
        }

        updateUI()
    }

    private fun updateUI() {
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

    private fun getTotalToShow(): String {
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
        private const val OK_BACK_COLOR = "OK_BACK_COLOR"
        private const val LIMIT_NUMBER = "LIMIT_NUMBER"
        private const val NEGATIVE_NUMBERS = "NEGATIVE_NUMBERS"
        private const val ERROR_DIV_0 = "ERROR_DIV_0"
        private const val ERROR_LIMIT = "ERROR_LIMIT"
        private const val ERROR_NEGATIVE_NUMBERS = "ERROR_NEGATIVE_NUMBERS"
        private const val VALUE = "VALUE"


        @JvmStatic
        fun newInstance(): CalculatorDialog = CalculatorDialog()

    }
}
