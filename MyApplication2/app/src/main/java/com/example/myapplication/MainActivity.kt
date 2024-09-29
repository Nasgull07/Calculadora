package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.*

class MainActivity : ComponentActivity() {
    private lateinit var display: TextView
    private var currentOperator: String? = null
    private var firstOperand: String = ""
    private var secondOperand: String = ""
    private var isOperatorSelected: Boolean = false
    private var Deegres: Boolean = true
    private var hasDecimal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Conectar el TextView para mostrar los resultados
        display = findViewById(R.id.calc)

        // Conectar los botones numéricos y operadores
        val number0: Button = findViewById(R.id.number0)
        val number1: Button = findViewById(R.id.number1)
        val number2: Button = findViewById(R.id.number2)
        val number3: Button = findViewById(R.id.number3)
        val number4: Button = findViewById(R.id.number4)
        val number5: Button = findViewById(R.id.number5)
        val number6: Button = findViewById(R.id.number6)
        val number7: Button = findViewById(R.id.number7)
        val number8: Button = findViewById(R.id.number8)
        val number9: Button = findViewById(R.id.number9)

        val sume: Button = findViewById(R.id.sume)
        val difference: Button = findViewById(R.id.difference)
        val multiply: Button = findViewById(R.id.multiply)
        val division: Button = findViewById(R.id.division)

        val cosinus: Button = findViewById(R.id.COS)
        val sinus: Button = findViewById(R.id.SIN)
        val tangent: Button = findViewById(R.id.TAN)

        val ac: Button = findViewById(R.id.delete)
        val equal: Button = findViewById(R.id.equal)
        val coma: Button = findViewById(R.id.coma)

        val units: Button = findViewById(R.id.units)
        // Configurar los eventos para los botones numéricos
        val numberButtons = listOf(number0, number1, number2, number3, number4, number5, number6, number7, number8, number9)
        for ((i, button) in numberButtons.withIndex()) {
            button.setOnClickListener { appendToDisplay(i.toString()) }
        }

        // Operaciones básicas
        sume.setOnClickListener { selectOperator("+") }
        difference.setOnClickListener { selectOperator("-") }
        multiply.setOnClickListener { selectOperator("*") }
        division.setOnClickListener { selectOperator("/") }
        coma.setOnClickListener { appendToDisplay(".")}
        // Operaciones trigonométricas
        sinus.setOnClickListener { applyTrigonometricFunction("sin") }
        cosinus.setOnClickListener { applyTrigonometricFunction("cos") }
        tangent.setOnClickListener { applyTrigonometricFunction("tan") }

        // Botón de igual
        equal.setOnClickListener { calculateResult() }

        // Botón AC (borrar)
        ac.setOnClickListener { clearDisplay() }
        units.setOnClickListener {
            units.text = changeunits(units.text.toString())
        }
    }

    // Método para agregar texto al display
    private fun changeunits(value: String): String {
        Deegres = !Deegres  // Cambiar el estado de la variable
        return if (Deegres) "Degrees" else "Radians"  // Retorna la nueva unidad
    }

    private fun appendToDisplay(value: String) {
        if (value == ".") {
            if (isOperatorSelected) {
                // Solo permitir un punto decimal en el segundo operando
                if (!secondOperand.contains(".")) {
                    secondOperand += value
                }
            } else {
                // Solo permitir un punto decimal en el primer operando
                if (!firstOperand.contains(".")) {
                    firstOperand += value
                }
            }
        } else {
            if (isOperatorSelected) {
                secondOperand += value
            } else {
                firstOperand += value
            }
        }
        display.text = firstOperand + (currentOperator ?: "") + secondOperand
    }

    // Método para seleccionar el operador
    private fun selectOperator(operator: String) {
        if (firstOperand.isNotEmpty()) {
            currentOperator = operator
            isOperatorSelected = true
            display.text = firstOperand + operator
        }
    }

    // Método para aplicar funciones trigonométricas
    private fun applyTrigonometricFunction(function: String) {
        if (firstOperand.isNotEmpty()) {
            val angle = if (Deegres) {
                Math.toRadians(firstOperand.toDouble())  // Convierte a radianes si está en grados
            } else {
                firstOperand.toDouble()  // Usa directamente el valor si está en radianes
            }
            val result = when (function) {
                "sin" -> sin(angle)
                "cos" -> cos(angle)
                "tan" -> tan(angle)
                else -> 0.0
            }
            display.text = result.toString()
            firstOperand = result.toString()
            secondOperand = ""
            currentOperator = null
            isOperatorSelected = false
        }
    }

    // Método para calcular el resultado
    private fun calculateResult() {
        if (firstOperand.isNotEmpty() && secondOperand.isNotEmpty() && currentOperator != null) {
            val result = when (currentOperator) {
                "+" -> firstOperand.toDouble() + secondOperand.toDouble()
                "-" -> firstOperand.toDouble() - secondOperand.toDouble()
                "*" -> firstOperand.toDouble() * secondOperand.toDouble()
                "/" -> firstOperand.toDouble() / secondOperand.toDouble()
                else -> 0.0
            }
            display.text = result.toString()
            firstOperand = result.toString()
            secondOperand = ""
            currentOperator = null
            isOperatorSelected = false
        }
    }

    // Método para borrar el contenido del display
    private fun clearDisplay() {
        display.text = ""
        firstOperand = ""
        secondOperand = ""
        currentOperator = null
        isOperatorSelected = false
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}