package mx.com.marcoramirezg.horariosdeautobus.ViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.com.marcoramirezg.horariosdeautobus.Data.Models.Horario

class HorarioViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _horarios = MutableStateFlow<List<Horario>>(emptyList())
    val horarios: StateFlow<List<Horario>> = _horarios
    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando

    fun fetchHorarios(lineaId: String, rutaId: String) {
        _cargando.value =  true

        db.collection("lineas")
            .document(lineaId)
            .collection("rutas")
            .document(rutaId)
            .collection("horarios")
            .whereEqualTo("activa", true)
            .orderBy("salida")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val items = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Horario::class.java)?.copy(id = doc.id)
                    }
                    _horarios.value = items
                }
                _cargando.value = false
            }
    }
}