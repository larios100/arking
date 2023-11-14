package com.example.arking.feature_otis.presentation.add_edit_oti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.arking.ui.components.Title
import com.example.arking.R
import com.example.arking.feature_otis.domain.utils.ConceptType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditOtiForm(
    state: AddEditOtiState,
    onEvent: (AddEditOtiEvent)-> Unit
) {
    Dialog(onDismissRequest = { /*TODO*/ }
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
                if(state.addingEditingConceptType is ConceptType.Concept){
                    Title(text = stringResource(id = R.string.concept))
                }
                if(state.addingEditingConceptType is ConceptType.Material){
                    Title(text = stringResource(id = R.string.materials))
                }
                if(state.addingEditingConceptType is ConceptType.Workforce){
                    Title(text = stringResource(id = R.string.workforce))
                }
                if(state.addingEditingConceptType is ConceptType.Tool){
                    Title(text = stringResource(id = R.string.tools))
                }
                TextField(value = state.concept,
                    onValueChange = {},
                    placeholder = {
                        Text(stringResource(id = R.string.concept))
                    },
                    modifier = Modifier.fillMaxWidth())
                TextField(value = "${state.quantity}",
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(stringResource(id = R.string.quantity))
                    },
                    modifier = Modifier.fillMaxWidth())
                TextField(value = state.unit,
                    onValueChange = {},
                    placeholder = {
                        Text(stringResource(id = R.string.unit))
                    },
                    modifier = Modifier.fillMaxWidth())
                TextField(value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(stringResource(id = R.string.unit_price))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth())
                TextField(value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(stringResource(id = R.string.amount))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth())
                Row {
                    Button(onClick = { onEvent(AddEditOtiEvent.CancelAddEditConcept) }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = stringResource(id = R.string.save))
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = stringResource(id = R.string.save_and_close))
                    }
                }

            }
        }

    }
}