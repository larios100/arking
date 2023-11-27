package com.example.arking.ui.components

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.arking.R
import com.github.gcacace.signaturepad.views.SignaturePad
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID


@Composable
fun Signature(
    onSave: (path: String) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val signaturePad = remember { SignaturePad(context,null) }
    val error = stringResource(id = R.string.sign_error)
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
                .background(MaterialTheme.colorScheme.background)

        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    onCancel()
                },
            ) {
                Text(stringResource(id = R.string.cancel))
            }
            Button(
                onClick = {
                    val signatureBitmap = signaturePad.signatureBitmap
                    val path = saveSignature(signatureBitmap, context)
                    if (path != null) {
                        Log.i("Sign", path)
                    }
                    if(path != null)
                        onSave(path)
                    else
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                },
            ) {
                Text(stringResource(id = R.string.save))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    signaturePad.clear()
                }
            ) {
                Text(stringResource(id = R.string.clean))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
fun saveSignature(imageBitmap: Bitmap, context: Context) : String? {
    try {
        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.png")
        val stream: OutputStream = FileOutputStream(file)
        imageBitmap.compress(Bitmap.CompressFormat.PNG,80,stream)
        stream.flush()
        stream.close()
        return file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        //showToast("Error al guardar la firma.")
    }
    return null
}