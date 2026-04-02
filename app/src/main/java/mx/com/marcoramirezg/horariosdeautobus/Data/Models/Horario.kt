package mx.com.marcoramirezg.horariosdeautobus.Data.Models

data class Horario(
    val id: String = "",
    val activa: Boolean = true,
    val salida: String = "",
    val llegada: String = "",
    val turno: String = "",
    val dias: List<String> = emptyList()
) {
    val diasAbreviados: String
        get() {
            if (dias.isEmpty()) return "Sin días"
            if (dias.size >= 7) return "Todos los días"

            return dias.joinToString(", ") { dia ->
                dia.take(3).replaceFirstChar { it.uppercase() }
            }
        }
}