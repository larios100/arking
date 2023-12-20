package com.example.arking.ui.components

import android.util.Log
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CurrencyTextField(
    value: String,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
) {
    Log.i("CurrencyTextField", value)
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        placeholder= {
                     Text(text = placeholder)
        },
        onValueChange = { newText: String ->
            if (newText.length <= Long.MAX_VALUE.toString().length && newText.isDigitsOnly()) {
                //text = newText
                onValueChange(newText)
            }
        },
        keyboardOptions = keyboardOptions.copy(
            keyboardType = KeyboardType.Number,
        ),
        keyboardActions = keyboardActions,
        visualTransformation = NumberCommaTransformation(),
    )
}

// format long to 123,456,789,9
class NumberCommaTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        Log.i("NumberCommaTransformation",text.text)
        Log.i("NumberCommaTransformation",text.text.toDoubleOrNull().toString())
        return TransformedText(
            text = AnnotatedString(text.text.toDoubleOrNull().formatWithComma()),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return text.text.toDoubleOrNull().formatWithComma().length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.length
                }
            }
        )
    }
}

fun Double?.formatWithComma(): String =
    NumberFormat.getNumberInstance().format(this ?: 0)