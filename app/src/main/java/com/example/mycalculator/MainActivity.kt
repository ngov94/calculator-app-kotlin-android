package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import  kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var calcString = ""
    var calcList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClear.setOnClickListener {
            calcString = ""
            calcList.clear()
            calcText.setText("0")
            fullCalcText.setText(fullCalc())
        }

        btnPlusMinus.setOnClickListener {
            if(!calcString.contains("-")){
                calcString = "-$calcString"
            }else{
                calcString = calcString.removePrefix("-")
            }
            calcText.setText("$calcString")

        }

        btnEquals.setOnClickListener {
            if(calcList.size == 0){
                return@setOnClickListener
            }
            var newCalcList = mutableListOf<String>()

            calcList.add(calcString)
            fullCalcText.setText(fullCalc())

            var c = 0
            var calc = 0.0
            while(c <= calcList.lastIndex){

                var itemCalc = calcList[c]
                if (itemCalc.equals("×") || itemCalc.equals("÷") ||  itemCalc.equals("%")){
                    calc = calculateOnOP(itemCalc, newCalcList.removeAt(newCalcList.lastIndex).toDouble(),calcList[++c].toDouble())
                    newCalcList.add(""+calc)
                    c++
                    continue
                }
                newCalcList.add(calcList[c])
                c++
            }
            if (newCalcList.size > 1){
                calc = newCalcList[0].toDouble()
                for(c in 1..newCalcList.lastIndex){
                    if(newCalcList[c].equals("+")){
                        calc += newCalcList[c+1].toDouble()
                    }
                    else if(newCalcList[c].equals("-")){
                        calc -= newCalcList[c+1].toDouble()
                    }
                }
                calcText.setText(""+calc)
            }
            else if(newCalcList.size == 1){
                calcText.setText(""+newCalcList[0])
            }

            calcString = ""
            calcList.clear()
        }

    }

    fun onDigit(view:View){
        calcString += (view as Button).text
        calcText.setText("$calcString")
    }

    fun onOperator(view: View){


        calcList.add(calcString)

        if(calcList.isNotEmpty() && calcString.isNullOrBlank()){
            calcList.removeAt(calcList.lastIndex)
            calcList.removeAt(calcList.lastIndex)

        }
        calcString = (view as Button).text as String
        calcList.add(calcString)
        calcText.setText("$calcString")
        calcString = ""
        fullCalcText.setText(fullCalc())

    }

    private fun fullCalc():String{
        var full = ""
        for (c in calcList){
            full+=c
        }
        return full
    }

    private fun calculateOnOP(op:String, leftExp:Double, rightExp:Double): Double{
        var result = when(op) {
            "×" -> leftExp*rightExp
            "÷" -> leftExp/rightExp
            "%" -> leftExp%rightExp
            "+" -> leftExp+rightExp
            else -> leftExp-rightExp
        }

        return result
    }


}