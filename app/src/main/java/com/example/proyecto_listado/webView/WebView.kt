package com.example.proyecto_listado.webView

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComponent(
    url: String
) {
    // Estado de carga
    val loadingState = remember { mutableStateOf(true) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    with(settings) {
                        javaScriptEnabled = true
                        useWideViewPort = true
                        loadWithOverviewMode = true
                        domStorageEnabled = true
                        allowFileAccess = true
                        setSupportZoom(true)
                        builtInZoomControls = true
                        displayZoomControls = false
                        cacheMode = WebSettings.LOAD_DEFAULT
                        mediaPlaybackRequiresUserGesture = false
                    }

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    var resolutionAdjusted = false

                    // Mostrar CircularProgressIndicator al empezar carga
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            loadingState.value = true
                        }

                        // Ocultar CircularProgressIndicator al terminar
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            loadingState.value = false
                        }

                        override fun onLoadResource(view: WebView?, url: String?) {
                            super.onLoadResource(view, url)

                            // Establecer el meta viewport para 4K
                            if (!resolutionAdjusted) {
                                resolutionAdjusted = true
                                view?.evaluateJavascript(
                                    """
                                    (function() {
                                        var meta = document.createElement('meta');
                                        meta.name = "viewport";
                                        meta.content = "width=3840, height=2160, initial-scale=1.0, maximum-scale=1.0, user-scalable=no";
                                        document.head.appendChild(meta);
                                        document.body.style.margin = '0';
                                        return document.documentElement.clientWidth + 'x' + document.documentElement.clientHeight;
                                    })();
                                    """.trimIndent(), null
                                )
                            }

                            // Aplicar zoom siempre
                            view?.evaluateJavascript(
                                """
                                document.body.style.zoom = '0.5';
                                """.trimIndent(), null
                            )
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            return true
                        }
                    }
                    loadUrl(url)
                }
            },
            update = { webViewInstance ->
                if (webViewInstance.url != url) {
                    loadingState.value = true
                    webViewInstance.loadUrl(url)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (loadingState.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp),
                color = Color.DarkGray,
            )
        }
    }
}
