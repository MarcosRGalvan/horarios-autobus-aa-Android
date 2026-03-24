package mx.com.marcoramirezg.horariosdeautobus.Data.Models

data class Ruta(
    val id: String = "",
    val nombre: String = "",
    val destino: String = "",
    val origen: String = "",
    val activa: Boolean = true,
    val precio: Double = 0.0,
    val tipo: String = ""
) {
}