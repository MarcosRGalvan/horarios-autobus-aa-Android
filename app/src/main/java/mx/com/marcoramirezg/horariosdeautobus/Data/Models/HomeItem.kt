package mx.com.marcoramirezg.horariosdeautobus.Data.Models

data class HomeItem(
    val id: String = "",
    val titulo: String = "",
    val operador: String = "",
    val icono: String = "bus",
    val activo: Boolean = true
)