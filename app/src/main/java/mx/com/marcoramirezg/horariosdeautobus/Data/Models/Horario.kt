package mx.com.marcoramirezg.horariosdeautobus.Data.Models

data class Horario(
    val id: String = "",
    val activa: Boolean = true,
    val salida: String = "",
    val llegada: String = "",
    val turno: String = ""
)