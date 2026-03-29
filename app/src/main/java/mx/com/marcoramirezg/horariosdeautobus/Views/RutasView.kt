package mx.com.marcoramirezg.horariosdeautobus.Views

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    onRutaClick: (String) -> Unit
) {
    val rutas by viewModel.rutas.collectAsState()

    LaunchedEffect(lineaId) {
        viewModel.fetchRutas(lineaId)
    }

    RutasContent(
        titulo = titulo,
        rutas = rutas,
        onBack = onBack,
        onRutaClick = onRutaClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutasContent(
    titulo: String,
    rutas: List<Ruta>,
    onBack: () -> Unit,
    onRutaClick: (String) -> Unit
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
            LazyColumn(
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(rutas.filter { it.activa }) { ruta ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .clickable {
                                onRutaClick(ruta.id)
                            },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = ruta.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    //color = Color(0xFFF57C00)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "De: ${ruta.origen}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )

                                    Text(
                                        text = " • ",
                                        color = Color.Gray
                                    )

                                    Text(
                                        text = "A: ${ruta.destino}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = "$: ${ruta.precio}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
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

    HorariosDeAutobusTheme {
        RutasContent (
            titulo = "Rojos",
            rutas = mockRutas,
            onBack = { },
            onRutaClick = { }
        )
    }
}