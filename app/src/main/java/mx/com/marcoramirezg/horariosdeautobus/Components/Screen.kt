package mx.com.marcoramirezg.horariosdeautobus.Components

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Rutas : Screen("rutas/{lineaId}/{titulo}") {
        fun createRoute(lineaId: String, titulo: String) = "rutas/$lineaId/$titulo"
    }
}