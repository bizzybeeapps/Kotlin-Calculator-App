package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput: String = ""
    private var firstNumber: Double? = null
    private var secondNumber: Double? = null
    private var operator: String? = null
    private var isNewInput: Boolean = false
    private var memoryValue: Double = 0.0  // Stores memory value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        // Set number button click listeners
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onNumberClick(it as Button) }
        }

        // Operator buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick("×") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick("÷") }

        // Functional buttons
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClear() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { onClearEntry() }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { onCalculate() }
        findViewById<Button>(R.id.btnDot).setOnClickListener { onDecimalClick() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { onToggleSign() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { onBackspace() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { onPercent() }
        findViewById<Button>(R.id.btnSquare).setOnClickListener { onSquare() }
        findViewById<Button>(R.id.btnSqrt).setOnClickListener { onSquareRoot() }
        findViewById<Button>(R.id.btnInverse).setOnClickListener { onInverse() }

        // Memory buttons
        findViewById<Button>(R.id.btnMC).setOnClickListener { memoryClear() }
        findViewById<Button>(R.id.btnMR).setOnClickListener { memoryRecall() }
        findViewById<Button>(R.id.btnMPlus).setOnClickListener { memoryAdd() }
        findViewById<Button>(R.id.btnMMinus).setOnClickListener { memorySubtract() }
    }

    // Handle number button clicks
    private fun onNumberClick(button: Button) {
        if (isNewInput) {
            currentInput = ""
            isNewInput = false
        }
        currentInput += button.text.toString()
        display.text = currentInput
    }

    // Handle operator clicks (+, -, ×, ÷)
    private fun onOperatorClick(op: String) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toDouble()
            operator = op
            isNewInput = true
        }
    }

    // Handle "=" button click
    private fun onCalculate() {
        if (operator != null && currentInput.isNotEmpty()) {
            secondNumber = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstNumber!! + secondNumber!!
                "-" -> firstNumber!! - secondNumber!!
                "×" -> firstNumber!! * secondNumber!!
                "÷" -> if (secondNumber!! != 0.0) firstNumber!! / secondNumber!! else "Error"
                else -> ""
            }
            display.text = result.toString()
            currentInput = result.toString()
            isNewInput = true
        }
    }

    // Handle "C" (Clear All)
    private fun onClear() {
        currentInput = ""
        firstNumber = null
        secondNumber = null
        operator = null
        display.text = "0"
    }

    // Handle "CE" (Clear Entry)
    private fun onClearEntry() {
        currentInput = ""
        display.text = "0"
    }

    // Handle "." (decimal point)
    private fun onDecimalClick() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            display.text = currentInput
        }
    }

    // Handle "+/-" (toggle sign)
    private fun onToggleSign() {
        if (currentInput.isNotEmpty() && currentInput != "0") {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            display.text = currentInput
        }
    }

    // Handle "%" (percent calculation)
    private fun onPercent() {
        if (currentInput.isNotEmpty()) {
            currentInput = (currentInput.toDouble() / 100).toString()
            display.text = currentInput
        }
    }

    // Handle "x²" (square)
    private fun onSquare() {
        if (currentInput.isNotEmpty()) {
            val number = currentInput.toDouble()
            currentInput = (number * number).toString()
            display.text = currentInput
        }
    }

    // Handle "²√x" (square root)
    private fun onSquareRoot() {
        if (currentInput.isNotEmpty()) {
            val number = currentInput.toDouble()
            currentInput = kotlin.math.sqrt(number).toString()
            display.text = currentInput
        }
    }

    // Handle "1/x" (inverse)
    private fun onInverse() {
        if (currentInput.isNotEmpty() && currentInput != "0") {
            val number = currentInput.toDouble()
            currentInput = (1 / number).toString()
            display.text = currentInput
        }
    }

    // Handle "⌫" (backspace)
    private fun onBackspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            display.text = if (currentInput.isEmpty()) "0" else currentInput
        }
    }

    // Handle "MC" (Memory Clear)
    private fun memoryClear() {
        memoryValue = 0.0
    }

    // Handle "MR" (Memory Recall)
    private fun memoryRecall() {
        currentInput = memoryValue.toString()
        display.text = currentInput
    }

    // Handle "M+" (Memory Add)
    private fun memoryAdd() {
        if (currentInput.isNotEmpty()) {
            memoryValue += currentInput.toDouble()
        }
    }

    // Handle "M-" (Memory Subtract)
    private fun memorySubtract() {
        if (currentInput.isNotEmpty()) {
            memoryValue -= currentInput.toDouble()
        }
    }
}