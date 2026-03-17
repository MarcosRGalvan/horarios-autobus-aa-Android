package mx.com.marcoramirezg.horariosdeautobus.Views

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.com.marcoramirezg.horariosdeautobus.Components.BusTile
import mx.com.marcoramirezg.horariosdeautobus.Data.Models.HomeItem
import mx.com.marcoramirezg.horariosdeautobus.R
import mx.com.marcoramirezg.horariosdeautobus.ViewModels.HomeViewModel
import mx.com.marcoramirezg.horariosdeautobus.ui.theme.HorariosDeAutobusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: HomeViewModel = viewModel(), onNavigateToRutas: (String, String) -> Unit) {

    val items by viewModel.items.collectAsState()

    var showAboutSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    HomeContent(
        items = items,
        onTileClick = onNavigateToRutas,
        onInfoClick = { showAboutSheet = true }
    )

    if (showAboutSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAboutSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            AboutAppView(onClose = { showAboutSheet = false })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    items: List<HomeItem>,
    onTileClick: (String, String) -> Unit,
    onInfoClick: () -> Unit
) {

    val calendar = Calendar.getInstance().time
    val formatter = SimpleDateFormat("EEEE, d 'de' MMMM", java.util.Locale("es", "MX"))
    val fechaFormateada = formatter.format(calendar).replaceFirstChar { it.uppercase() }


    val fondoGradiente = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF57C00).copy(alpha = 0.9f),
            MaterialTheme.colorScheme.background
        )
    )

    Box(modifier = Modifier.fillMaxSize().background(fondoGradiente)) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Hola, Bienvenido") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    actions = {
                        IconButton(onClick = onInfoClick) {
                            Icon(Icons.Default.Info, contentDescription = "Acerca de")
                        }
                    }
                )
            }
        ) { paddingValues ->

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = fechaFormateada,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                item(span = { GridItemSpan(2) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bus),
                            contentDescription = "Bus Logo",
                            modifier = Modifier.size(150.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Consulta los horarios de autobuses",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                items(items.filter { it.activo }) { item ->
                    BusTile(item = item) {
                        onTileClick(item.id, item.titulo)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_9")
@Composable
fun HomePreview() {
    val mockItems = listOf(
        HomeItem(id = "1", titulo = "Rojos", operador = "Coordinados", icono = "bus", activo = true),
        HomeItem(id = "2", titulo = "Verdes", operador = "Transportes", icono = "bus", activo = true),
        HomeItem(id = "3", titulo = "Azules", operador = "Sultana", icono = "bus", activo = true)
    )

    HorariosDeAutobusTheme {
        HomeContent(
            items = mockItems,
            onTileClick = { id, titulo ->
                println("Clic en $titulo con ID $id")
            },
            onInfoClick = {}
        )
    }
}