package com.app.herysapps.calculatordialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.app.herysapps.calculatordialoglib.CalculatorDialog
import com.app.herysapps.calculatordialoglib.CalculatorDialog.OnDialogResultListener
import com.app.herysapps.calculatordialoglib.CalculatorDialogBuilder
import kotlinx.android.synthetic.main.activity_example.*

/**
 * Created by Hery Lopez on 10/08/2017.
 *
 * Github:
 * Author: https://github.com/HeryLopez
 * Project: https://github.com/HeryLopez/CalculatorDialog
 */
class ExampleActivity : AppCompatActivity(), OnDialogResultListener {

    var dialogA: CalculatorDialog? = null
    var dialogB: CalculatorDialog? = null
    private val ID_DIALOG_EXAMPLE_1 = "calculatorDialog01"
    private val ID_DIALOG_EXAMPLE_2 = "calculatorDialog02"
    private val ID_DIALOG_EXAMPLE_3 = "calculatorDialog03"
    private var v1 = 0.0
    private var v2 = 0.0
    private var v3 = 0.0

    private var isFullscreenModelActive = false

    private fun buildDialog() {
        val builderDialog1 = CalculatorDialogBuilder()
        builderDialog1.setOnResultListener(this)
        builderDialog1.limitNumbers(20)
        builderDialog1.negativeNumberActivated(true)
        builderDialog1.setErrorDiv0("Division par 0 impossible")
        builderDialog1.setErrorLimit("Limite de nombre dépassée")
        builderDialog1.setErrorNegativeValue("Numéros négatifs désactivés")
        builderDialog1.setOkBackgroundColor(R.color.colorPrimaryDark)
        dialogA = builderDialog1.build()


        val builderDialog2 = CalculatorDialogBuilder()
        builderDialog2.setOnResultListener(this)
        builderDialog2.setDecor("$")
        builderDialog2.setNumberColor(R.color.color01)
        builderDialog2.setOperationColor(R.color.color01)
        builderDialog2.setNumberBackgroundColor(R.color.color02)
        builderDialog2.setOperatorBackgroundColor(R.color.color02)
        builderDialog2.setOkBackgroundColor(R.color.colorAccent)
        dialogB = builderDialog2.build()
    }

    private fun openFullscreenDialog(dialog: CalculatorDialog, name: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(android.R.id.content, dialog).addToBackStack(null).commit()
    }

    private fun example1() {
        v1 = 1234567.89 //3.1415
        val strWithFormat = dialogA!!.getNumberWithFormat(v1)
        textView1.text = strWithFormat
        buttonSelector01.setOnClickListener {
            dialogA!!.setInitialValue(v1)
            dialogA!!.setDialogId(ID_DIALOG_EXAMPLE_1)
            if (isFullscreenModelActive) {
                openFullscreenDialog(dialogA!!, ID_DIALOG_EXAMPLE_1)
            } else {
                dialogA!!.show(supportFragmentManager, ID_DIALOG_EXAMPLE_1)
            }
        }
    }

    private fun example2() {
        buttonSelector02.setOnClickListener {
            dialogA!!.setInitialValue(v2)
            dialogA!!.setDialogId(ID_DIALOG_EXAMPLE_2)
            if (isFullscreenModelActive) {
                openFullscreenDialog(dialogA!!, ID_DIALOG_EXAMPLE_2)
            } else {
                dialogA!!.show(supportFragmentManager, ID_DIALOG_EXAMPLE_2)
            }
        }
    }

    private fun example3() {
        v3 = 0.0 //3.1415
        val strWithFormat = dialogA!!.getNumberWithFormat(v1)
        textView3.text = strWithFormat
        buttonSelector03.setOnClickListener {
            dialogB!!.setInitialValue(v3)
            dialogB!!.setDialogId(ID_DIALOG_EXAMPLE_3)
            if (isFullscreenModelActive) {
                openFullscreenDialog(dialogB!!, ID_DIALOG_EXAMPLE_3)
            } else {
                dialogB!!.show(supportFragmentManager, ID_DIALOG_EXAMPLE_3)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        buildDialog()

        switchFullscreen.setOnCheckedChangeListener { _, isChecked ->
            isFullscreenModelActive = isChecked
        }

        example1()
        example2()
        example3()
    }


    override fun onDialogResult(tagDialog: String, value: Double, valueStr: String) {
        if (tagDialog == ID_DIALOG_EXAMPLE_1) {
            textView1.text = valueStr
            v1 = value
        }
        if (tagDialog == ID_DIALOG_EXAMPLE_2) {
            textView2.text = valueStr
            v2 = value
        }
        if (tagDialog == ID_DIALOG_EXAMPLE_3) {
            textView3.text = valueStr
            v3 = value
        }
    }
}