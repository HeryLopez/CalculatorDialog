package com.app.herysapps.calculatordialoglib

class CalculatorDialogBuilder {
    private var onDialogResultListener: CalculatorDialog.OnDialogResultListener? = null
    private var name: String? = null
    private var decor: String? = null
    private var numberColor: Int? = null
    private var operationColor: Int? = null
    private var numberBackgroundColor: Int? = null
    private var operatorBackgroundColor: Int? = null
    private var dialogButtonsColor: Int? = null
    private var limitNumbers: Int? = null
    private var limitNegativeNumbers: Boolean? = null
    private var errorDiv0: String? = null
    private var errorLimitNumber: String? = null
    private var errorNegativeValue: String? = null

    fun setOnResultListener(onDialogResultListener: CalculatorDialog.OnDialogResultListener){
        this.onDialogResultListener = onDialogResultListener
    }

    /**
     * Set an identifier that allows identifier the instance.
     * (For multiples dialogs in screen)
     *
     * override onCalculatorDialogResponse(String name, double value, String valueStr) {
     *      if (name.equals(DIALOG_01)) {
     *      }
     *
     *      if (name.equals(DIALOG_02)) {
     *      }
     * }
     *
     * @param name calculator dialog id
     */
    fun setName(name: String): CalculatorDialogBuilder {
        this.name = name
        return this
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
     * Set the color for the dialog buttons
     *
     * @param dialogButtonsColor color resource
     */
    fun setDialogButtonsColor(dialogButtonsColor: Int) {
        this.dialogButtonsColor = dialogButtonsColor
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

        if (name == "") {
            throw RuntimeException("CalculatorDialog instance must initialize the Name property (setName).")
        }

        if (limitNumbers != null && limitNumbers!! < 0) {
            throw RuntimeException("The limit must be greater than or equal to zero.")
        }

        val dialog = CalculatorDialog()
        dialog.setParameters(onDialogResultListener, name, decor, numberColor, operationColor, numberBackgroundColor, operatorBackgroundColor, dialogButtonsColor, limitNumbers, limitNegativeNumbers, errorDiv0, errorLimitNumber, errorNegativeValue)
        return dialog
    }
}