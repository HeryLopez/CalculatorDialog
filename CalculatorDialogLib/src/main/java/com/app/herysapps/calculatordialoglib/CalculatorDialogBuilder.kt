package com.app.herysapps.calculatordialoglib

class CalculatorDialogBuilder {
    private var onDialogResultListener: CalculatorDialog.OnDialogResultListener? = null
    private var decor: String? = null
    private var numberColor: Int? = null
    private var operationColor: Int? = null
    private var numberBackgroundColor: Int? = null
    private var operatorBackgroundColor: Int? = null
    private var okBackgroundColor: Int? = null
    private var limitNumbers: Int? = null
    private var limitNegativeNumbers: Boolean? = null
    private var errorDiv0: String? = null
    private var errorLimitNumber: String? = null
    private var errorNegativeValue: String? = null

    fun setOnResultListener(onDialogResultListener: CalculatorDialog.OnDialogResultListener) {
        this.onDialogResultListener = onDialogResultListener
    }

    /**
     * Show the symbol for decoration of the screen
     *
     * @param decor Symbol
     */
    fun setDecor(decor: String) {
        this.decor = decor
    }

    /**
     * Set the color for the numbers
     *
     * @param numberColor color resource
     */
    fun setNumberColor(numberColor: Int) {
        this.numberColor = numberColor
    }

    /**
     * Set the color for the operators
     *
     * @param operationColor color resource
     */
    fun setOperationColor(operationColor: Int) {
        this.operationColor = operationColor
    }

    /**
     * Set the background color for the numbers
     *
     * @param numberBackgroundColor color resource
     */
    fun setNumberBackgroundColor(numberBackgroundColor: Int) {
        this.numberBackgroundColor = numberBackgroundColor
    }

    /**
     * Set the background color for the operators
     *
     * @param operatorBackgroundColor color resource
     */
    fun setOperatorBackgroundColor(operatorBackgroundColor: Int) {
        this.operatorBackgroundColor = operatorBackgroundColor
    }

    /**
     * Set the background color for the ok button
     *
     * @param okBackgroundColor color resource
     */
    fun setOkBackgroundColor(okBackgroundColor: Int) {
        this.okBackgroundColor = okBackgroundColor
    }

    /**
     * Set the limit for the length of the numbers. The value must be greater than or equal to zero.
     *
     * @param limitNegativeNumbers limit
     */
    fun limitNumbers(limitNumbers: Int) {
        this.limitNumbers = limitNumbers
    }

    /**
     * Enable/disable limit for numbers length
     *
     * @param limitNegativeNumbers there is a limit for numbers length
     */
    fun negativeNumberActivated(limitNegativeNumbers: Boolean) {
        this.limitNegativeNumbers = limitNegativeNumbers
    }

    /**
     * Message to show in the division by zero error.
     *
     * @param errorDiv0 Text of error
     */
    fun setErrorDiv0(errorDiv0: String) {
        this.errorDiv0 = errorDiv0
    }

    /**
     * Message to show when the limit of number is exceeded
     *
     * @param errorLimitNumber Text of error
     */
    fun setErrorLimit(errorLimitNumber: String) {
        this.errorLimitNumber = errorLimitNumber
    }

    /**
     * Message to show when there are negative numbers and they are disabled
     *
     * @param errorNegativeValue Text of error
     */
    fun setErrorNegativeValue(errorNegativeValue: String) {
        this.errorNegativeValue = errorNegativeValue
    }

    fun build(): CalculatorDialog {
        if (onDialogResultListener == null) {
            throw RuntimeException("CalculatorDialog.OnDialogResultListener must be implemented")
        }

        if (limitNumbers != null && limitNumbers!! < 0) {
            throw RuntimeException("The limit must be greater than or equal to zero.")
        }

        val dialog = CalculatorDialog()
        dialog.mListener = onDialogResultListener!!

        if (decor != null) dialog.mDecor = decor
        if (numberColor != null) dialog.mNumberColor = numberColor!!
        if (operationColor != null) dialog.mOperationColor = operationColor!!
        if (numberBackgroundColor != null) dialog.mNumberBackgroundColor = numberBackgroundColor!!
        if (operatorBackgroundColor != null) dialog.mOperatorBackgroundColor = operatorBackgroundColor!!
        if (okBackgroundColor != null) dialog.mOkBackgroundColor = okBackgroundColor!!
        if (limitNumbers != null) dialog.mLimitNumbers = limitNumbers!!
        if (limitNegativeNumbers != null) dialog.mLimitNegativeNumbers = limitNegativeNumbers!!
        if (errorDiv0 != null) dialog.mErrorDiv0 = errorDiv0!!
        if (errorLimitNumber != null) dialog.mErrorLimitNumber = errorLimitNumber!!
        if (errorNegativeValue != null) dialog.mErrorNegativeValue = errorNegativeValue!!

        return dialog
    }
}