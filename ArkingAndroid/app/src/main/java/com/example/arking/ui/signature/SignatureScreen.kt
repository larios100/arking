package com.example.arking.ui.signature

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.ByteArrayOutputStream

@Composable
fun SignatureScreen() {
    val context = LocalContext.current
    val signaturePad = remember { SignaturePad(context,null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // SignaturePad personalizable con opciones según tus necesidades
        AndroidView(
            factory = { signaturePad },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(1.dp, Color.Blue)

        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val signatureBitmap = signaturePad.signatureBitmap
                    //saveSignatureToDatabase(signatureBitmap, signatureDao)
                },
            ) {
                Text("Guardar Firma")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    signaturePad.clear()
                }
            ) {
                Text("Limpiar Firma")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

/*private fun saveSignatureToDatabase(bitmap: Bitmap, signatureDao: SignatureDao) {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val signatureBytes = outputStream.toByteArray()
    val signatureEntity = SignatureEntity(signatureBytes = signatureBytes)
    viewModelScope.launch {
        signatureDao.insertSignature(signatureEntity)
    }
}*/


