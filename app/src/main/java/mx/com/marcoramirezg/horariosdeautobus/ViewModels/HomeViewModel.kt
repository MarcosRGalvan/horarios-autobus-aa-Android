package mx.com.marcoramirezg.horariosdeautobus.ViewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.com.marcoramirezg.horariosdeautobus.Data.Models.HomeItem

class HomeViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _items = MutableStateFlow<List<HomeItem>>(emptyList())

    val items: StateFlow<List<HomeItem>> = _items.asStateFlow()

    init {
        fetchBusLines()
    }

    private fun fetchBusLines() {
        db.collection("lineas").addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("❌ Error en Firestore: ${error.message}")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val busLines = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(HomeItem::class.java)?.copy(id = doc.id)
                }

                _items.value = busLines
            }
        }
    }

    fun getItemsActivos(): List<HomeItem> {
        return _items.value.filter { it.activo }
    }
}