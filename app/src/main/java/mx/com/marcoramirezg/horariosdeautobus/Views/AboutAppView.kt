package mx.com.marcoramirezg.horariosdeautobus.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.com.marcoramirezg.horariosdeautobus.R
import mx.com.marcoramirezg.horariosdeautobus.ui.theme.HorariosDeAutobusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppView(onClose: () -> Unit) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.bus),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Horarios de Autobús",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Versión 1.0.0 Beta",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Los horarios pueden variar dependiendo de eventos inesperados como trabajos de mantenimiento o cambios en las rutas. Espero que disfrutes de esta app!",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Desarrollado por Marco Ramírez",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            "© 2026 Marco Ramírez. Todos los derechos reservados.",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Thin
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = { uriHandler.openUri("mailto:tu_correo@ejemplo.com") }) {
            Text("Contacto", color = Color(0xFFF57C00))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C00)),
            //shape = RoundedCornerShape(12.dp)
        ) {
            Text("Cerrar", fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true, name = "Vista previa acerca de", device = "id:pixel_9")
@Composable
fun AboutAppPreview() {
    HorariosDeAutobusTheme {
        AboutAppView(onClose = { })
    }
}