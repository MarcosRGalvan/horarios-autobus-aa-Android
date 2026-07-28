package mx.com.marcoramirezg.horariosdeautobus.Components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun BannerAdView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                //adUnitId = "ca-app-pub-6438585787241160/6836882377" // FIXME ID DE ANUNCIOS REAL (descomentar para mostrar anuncios reales)
                adUnitId = "ca-app-pub-3940256099942544/9214589741" // ID de anuncios de prueba
                adListener = object : AdListener() {
                    override fun onAdFailedToLoad(error: LoadAdError) {
                        Log.e("BannerAd", "Error al cargar: ${error.code} - ${error.message}")
                    }

                    override fun onAdLoaded() {
                        Log.e("BannerAd", "Anuncio cargado correctamente")
                    }
                }
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}