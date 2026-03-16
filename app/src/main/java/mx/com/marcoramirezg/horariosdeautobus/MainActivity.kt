package mx.com.marcoramirezg.horariosdeautobus

import android.os.Bundle
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.com.marcoramirezg.horariosdeautobus.Components.Screen
import mx.com.marcoramirezg.horariosdeautobus.Views.HomeView
import mx.com.marcoramirezg.horariosdeautobus.Views.RutasView
import mx.com.marcoramirezg.horariosdeautobus.ui.theme.HorariosDeAutobusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HorariosDeAutobusTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Home.route) {

                    composable(Screen.Home.route) {
                        HomeView(onNavigateToRutas = { id, titulo ->
                            navController.navigate(Screen.Rutas.createRoute(id, titulo))
                        })
                    }

                    composable(Screen.Rutas.route) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("lineaId") ?: ""
                        val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
                        RutasView(lineaId = id, titulo = titulo, onBack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}