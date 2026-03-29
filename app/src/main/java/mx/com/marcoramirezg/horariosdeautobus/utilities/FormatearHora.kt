package mx.com.marcoramirezg.horariosdeautobus.utilities

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatearHora(hora24: String): String {
    return try {
        val localTime = LocalTime.parse(hora24)
        val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())

        localTime.format(formatter)
    } catch (e: Exception) {
        hora24
    }
}