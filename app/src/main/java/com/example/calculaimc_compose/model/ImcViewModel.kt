package com.example.calculaimc_compose.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ImcViewModel: ViewModel() {

    var peso by mutableStateOf("")
        private set
    var altura by mutableStateOf("")
        private  set
    var resultado by mutableStateOf("0.0")
        private set

    fun onPesoChange(newValue: String) {
        peso = newValue
    }

    fun onAlturaChange(newValue: String) {
        altura = newValue
    }

    fun calcularIMC() {
        val alturaValor = altura.toDoubleOrNull()
        val pesoValor = peso.toDoubleOrNull()

        if (alturaValor != null && pesoValor != null) {
            val imc = pesoValor / (alturaValor * alturaValor)
            resultado = "%.2f".format(imc)
        } else {
            resultado = "0.0"
        }
    }

    fun limpar() {
        peso = ""
        altura = ""
        resultado = "0.0"
    }

}