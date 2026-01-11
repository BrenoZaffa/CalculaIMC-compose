package com.example.calculaimc_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculaimc_compose.ui.theme.CalculaIMCcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculaIMCcomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculaIMCScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculaIMCScreen(modifier: Modifier = Modifier) {
    var peso by rememberSaveable() { mutableStateOf("") }
    var altura by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf("0.0") }
    var focusRequester by remember { mutableStateOf(FocusRequester()) }

    val calcularIMC = {
        val alturaValor = altura.toDoubleOrNull()
        val pesoValor = peso.toDoubleOrNull()

        if (alturaValor != null && pesoValor != null) {
            val imc = pesoValor / (alturaValor * alturaValor)
            resultado = "%.2f".format(imc)
        } else {
            resultado = "0.0"
        }
    }

    val limpar: () -> Unit = {
        peso = ""
        altura = ""
        resultado = "0.0"
        focusRequester.requestFocus()
    }

    Column (
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        OutlinedTextField(
            value = peso,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() || it == '.' }) {
                    peso = newValue
                }
            },
            label = { Text("Peso em Kg") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )

        OutlinedTextField(
            value = altura,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() || it == '.' }) {
                    altura = newValue
                }
            },
            label = { Text("Altura em metros") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        if(resultado.toDouble() > 0){
            PanelResultado(resultado = resultado)
        }
        PanelButtons(
            onCalcularClick = calcularIMC,
            onLimparClick = limpar
        )
    }
}

@Composable
fun PanelResultado(resultado: String, modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resultado:",
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = resultado,
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun PanelButtons(
    onCalcularClick: () -> Unit,
    onLimparClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { onCalcularClick() },
            modifier = Modifier
                .weight(1f)
        ) {
            Text("Calcular")
        }
        Button(
            onClick = { onLimparClick() },
            modifier = Modifier
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Limpar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PanelButtonsPreview() {
    CalculaIMCcomposeTheme {
        PanelButtons(onCalcularClick = {}, onLimparClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PanelResultadoPreview() {
    CalculaIMCcomposeTheme {
        PanelResultado(resultado = "22.22")
    }
}

@Preview(showBackground = true)
@Composable
fun CalculaIMCScreenPreview() {
    CalculaIMCcomposeTheme {
        CalculaIMCScreen()
    }
}