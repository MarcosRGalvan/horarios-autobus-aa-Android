package mx.com.marcoramirezg.horariosdeautobus.Views

import android.icu.text.StringSearch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.com.marcoramirezg.horariosdeautobus.Data.Models.Ruta
import mx.com.marcoramirezg.horariosdeautobus.ViewModels.RutaViewModel
import mx.com.marcoramirezg.horariosdeautobus.ui.theme.HorariosDeAutobusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutasView(
    lineaId: String,
    titulo: String,
    viewModel: RutaViewModel = viewModel(),
    onBack: () -> Unit,
    onRutaClick: (String, String) -> Unit
) {
    val rutas by viewModel.rutas.collectAsState()
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(lineaId) {
        viewModel.fetchRutas(lineaId)
    }

    val rutasFiltradas = remember(rutas, searchText) {
        rutas.filter { it.activa }.filter {
            it.nombre.contains(searchText, ignoreCase = true) ||
                    it.origen.contains(searchText, ignoreCase = true) ||
                    it.destino.contains(searchText, ignoreCase = true)
        }
    }

    RutasContent(
        titulo = titulo,
        rutas = rutasFiltradas,
        searchText = searchText,
        onSearchChange = { searchText = it },
        onBack = onBack,
        onRutaClick = onRutaClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutasContent(
    titulo: String,
    rutas: List<Ruta>,
    searchText: String,
    onSearchChange: (String) -> Unit,
    onBack: () -> Unit,
    onRutaClick: (String, String) -> Unit
) {
    val fondoGradiente = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF14AACF).copy(alpha = 0.9f),
            MaterialTheme.colorScheme.background
        ),
        startY = 0f,
        endY = 1500f
    )

    Box(modifier = Modifier.fillMaxSize().background(fondoGradiente)) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text(titulo) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Regresar",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("Buscar ruta, origen o destino...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                        focusedBorderColor = Color(0xFF14AACF),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (rutas.isEmpty() && searchText.isNotEmpty()) {
                        item {
                            Text(
                                "No se encontraron resultados",
                                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    items(rutas) { ruta ->
                        RutaCard(ruta = ruta, onRutaClick = onRutaClick)
                    }
                }
            }
        }
    }
}


@Composable
fun RutaCard(ruta: Ruta, onRutaClick: (String, String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRutaClick(ruta.id, ruta.nombre) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = ruta.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "De: ${ruta.origen} • A: ${ruta.destino}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_9")
@Composable
fun RutasPreview() {

    val mockRutas = listOf(
        Ruta(id = "1", nombre = "Apaseo - Salto de Peña", origen = "Apaseo el Alto", destino = "Salto de Peña"),
        Ruta(id = "2", nombre = "Salto de Peña - Apaseo", origen = "Salto de Peña", destino = "Apaseo el Alto"),
        //Ruta(id = "3", nombre = "Directo Centro", origen = "09:15 AM", destino = "Mercado")
    )

    /*
    HorariosDeAutobusTheme {
        RutasContent (
            titulo = "Rojos",
            rutas = mockRutas,
            onBack = { },
            onRutaClick = { }
        )
    } */
}