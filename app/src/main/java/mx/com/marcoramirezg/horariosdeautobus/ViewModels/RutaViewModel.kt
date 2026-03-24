package mx.com.marcoramirezg.horariosdeautobus.ViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.com.marcoramirezg.horariosdeautobus.Data.Models.Ruta

class RutaViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas: StateFlow<List<Ruta>> = _rutas

    fun fetchRutas(lineaId: String) {
        db.collection("lineas")
            .document(lineaId)
            .collection("rutas")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                if (snapshot != null) {
                    val listaRutas = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Ruta::class.java)?.copy(id = doc.id)
                    }
                    _rutas.value = listaRutas
                }
            }
    }
}