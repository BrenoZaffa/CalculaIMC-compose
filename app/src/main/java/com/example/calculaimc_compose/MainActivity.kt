package com.example.calculaimc_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculaimc_compose.model.ImcViewModel
import com.example.calculaimc_compose.ui.theme.CalculaIMCcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculaIMCcomposeTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home") {
                        composable(
                            "home",
                            exitTransition = {
                                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
                            },
                            popEnterTransition = {
                                slideInHorizontally(initialOffsetX = { -it }) + fadeIn()
                            }
                        ) {
                            CalculaIMCScreen(
                                modifier = Modifier.padding(innerPadding),
                                onNavigateToDeveloper = {
                                    navController.navigate("developer")
                                }
                            )
                        }
                        composable(
                            "developer",
                            enterTransition = {
                                slideInHorizontally(initialOffsetX = { it }) + fadeIn()
                            },
                            popExitTransition = {
                                slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                            }
                        ) {
                            DeveloperScreen(
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculaIMCScreen(
    modifier: Modifier = Modifier,
    viewModel: ImcViewModel = viewModel(),
    onNavigateToDeveloper: () -> Unit
) {
    var focusRequester by remember { mutableStateOf(FocusRequester()) }

    Column (
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        OutlinedTextField(
            value = viewModel.peso,
            onValueChange = { viewModel.onPesoChange(it) },
            label = { Text("Peso em Kg") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )

        OutlinedTextField(
            value = viewModel.altura,
            onValueChange = { viewModel.onAlturaChange(it) },
            label = { Text("Altura em metros") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        if(viewModel.resultado.toDouble() > 0){
            PanelResultado(resultado = viewModel.resultado)
        }
        PanelButtons(
            onCalcularClick = { viewModel.calcularIMC() },
            onLimparClick = {
                viewModel.limpar()
                focusRequester.requestFocus()
            }
        )
        Button(
            onClick = {
                onNavigateToDeveloper()
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Sobre o Desenvolvedor")
        }
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

@Composable
fun DeveloperScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Desenvolvido por:")
        Text(
            text = "brenozaffa01@gmail.com",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeveloperScreenPreview(modifier: Modifier = Modifier) {
    CalculaIMCcomposeTheme {
        DeveloperScreen()
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
        CalculaIMCScreen(
            onNavigateToDeveloper = {}
        )
    }
}