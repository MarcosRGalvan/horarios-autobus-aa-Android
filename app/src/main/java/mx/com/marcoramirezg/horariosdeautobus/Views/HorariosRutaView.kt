package mx.com.marcoramirezg.horariosdeautobus.Views

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.com.marcoramirezg.horariosdeautobus.Components.BannerAdView
import mx.com.marcoramirezg.horariosdeautobus.Data.Models.Horario
import mx.com.marcoramirezg.horariosdeautobus.ViewModels.HorarioViewModel
import mx.com.marcoramirezg.horariosdeautobus.ui.theme.HorariosDeAutobusTheme
import mx.com.marcoramirezg.horariosdeautobus.utilities.formatearHora

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorariosRutaView(
    lineaId: String,
    rutaId: String,
    tituloRuta: String,
    viewModel: HorarioViewModel = viewModel(),
    onBack: () -> Unit
) {
    val horarios by viewModel.horarios.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    LaunchedEffect(lineaId, rutaId) {
        viewModel.fetchHorarios(lineaId, rutaId)
    }

    HorariosRutaContent(
        titulo = tituloRuta,
        horarios = horarios,
        estaCargando = cargando,
        onBack = onBack
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorariosRutaContent(
    titulo: String,
    horarios: List<Horario>,
    estaCargando: Boolean,
    onBack: () -> Unit
) {
    val fondoGradiente = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF14AACF).copy(alpha = 0.9f),
            MaterialTheme.colorScheme.surface
        ),
        startY = 0f,
        endY = 1500f
    )

    Box(modifier = Modifier.fillMaxSize().background(fondoGradiente)) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(titulo, style = MaterialTheme.typography.titleLarge)
                            Text("Horarios Disponibles", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
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
            },
            bottomBar = { BannerAdView() }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding).background(fondoGradiente)) {
                when {
                    estaCargando -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color(0xFF14AACF))
                        }
                    }
                    horarios.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            Text("No hay horarios disponibles", color = Color.Gray)
                            Text("Prueba con otra ruta", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(horarios.filter { it.activa }) { horario ->
                                HorarioItemRow(horario)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HorarioItemRow(horario: Horario) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(1.0f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = Color(0xFF14AACF),
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Salida: ${formatearHora(horario.salida)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Llegada estimada: ${formatearHora(horario.llegada)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF14AACF)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color(0xFF14AACF)
                    )
                    Text(
                        text = horario.diasAbreviados,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF14AACF).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = horario.turno.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF14AACF),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_9")
@Composable
fun HorariosPreview() {
    val mock = listOf(
        Horario(salida = "07:00", llegada = "08:00", turno = "Mañana", dias = listOf("Lunes", "Martes", "Miércoles")),
        Horario(salida = "08:30", llegada = "08:00", turno = "Mañana", dias = listOf("Sábado", "Domingo")),
        Horario(salida = "01:15", llegada = "08:00", turno = "Tarde", dias = listOf("Lunes", "Domingo"))
    )
    HorariosDeAutobusTheme {
        HorariosRutaContent(titulo = "Apaseo - Celaya", horarios = mock, estaCargando = false, onBack = {})
    }
}