package com.example.arking.feature_otis.presentation.add_edit_oti

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.arking.ui.components.Title
import com.example.arking.R
import com.example.arking.feature_otis.domain.utils.ConceptType
import com.example.arking.ui.components.CurrencyTextField

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
            val focusManager = LocalFocusManager.current
            val focusQuantity = remember { FocusRequester() }
            val focusUnit = remember { FocusRequester() }
            val focusUnitPrice = remember { FocusRequester() }
            val focusAmount = remember { FocusRequester() }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
                if(state.addingEditingConceptType == ConceptType.Concept){
                    Title(text = stringResource(id = R.string.concept))
                }
                if(state.addingEditingConceptType == ConceptType.Material){
                    Title(text = stringResource(id = R.string.materials))
                }
                if(state.addingEditingConceptType == ConceptType.Workforce){
                    Title(text = stringResource(id = R.string.workforce))
                }
                if(state.addingEditingConceptType == ConceptType.Tool){
                    Title(text = stringResource(id = R.string.tools))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.concept), modifier = Modifier.weight(0.3f))
                    Column(modifier = Modifier.weight(0.7f)) {
                        TextField(value = state.concept,
                            onValueChange = {
                                onEvent(AddEditOtiEvent.SetConcept(it))
                            },
                            //imeOptions = ImeAction.Next,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                //onDone = { focusManager.clearFocus() },
                                onNext = { focusUnit.requestFocus() }
                            ),
                        )
                        if (state.conceptError != null) {
                            Text(
                                text = state.conceptError.asString(),
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.unit), modifier = Modifier.weight(0.3f))
                    Column(modifier = Modifier.weight(0.7f)) {
                        TextField(value = state.unit,
                            onValueChange = {onEvent(AddEditOtiEvent.SetUnit(it))},
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                //onDone = { focusManager.clearFocus() },
                                onNext = { focusQuantity.requestFocus() }
                            ),
                            modifier = Modifier
                                .focusRequester(focusUnit))
                        if (state.unitError != null) {
                            Text(
                                text = state.unitError.asString(),
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }

                }

                if(state.addingEditingConceptType != ConceptType.Concept){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(id = R.string.quantity), modifier = Modifier.weight(0.3f))
                        TextField(value = "${state.quantity}",
                            onValueChange = {onEvent(AddEditOtiEvent.SetQuantity(it))},
                            keyboardActions = KeyboardActions(
                                onNext = { focusUnitPrice.requestFocus() }
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next),
                            modifier = Modifier
                                .weight(0.7f)
                                .focusRequester(focusQuantity))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(id = R.string.unit_price), modifier = Modifier.weight(0.3f))
                        CurrencyTextField(value = "${state.unitPrice}",
                            onValueChange = {onEvent(AddEditOtiEvent.SetUnitPrice(it))},
                            placeholder = "",
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() },
                                //onNext = { focusAmount.requestFocus() }
                            ),
                            modifier = Modifier
                                .weight(0.7f)
                                .focusRequester(focusUnitPrice)
                        )
                    }
                }
                else{
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(id = R.string.quantity), modifier = Modifier.weight(0.3f))
                        TextField(value = "${state.quantity}",
                            onValueChange = {onEvent(AddEditOtiEvent.SetQuantity(it))},
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() },
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done),
                            modifier = Modifier
                                .weight(0.7f)
                                .focusRequester(focusQuantity))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Column(modifier =Modifier.weight(1f)) {
                        Button(onClick = { onEvent(AddEditOtiEvent.CancelAddEditConcept) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        ) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                    }

                    Button(onClick = { onEvent(AddEditOtiEvent.SaveConcept) }) {
                        Text(text = stringResource(id = R.string.save))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onEvent(AddEditOtiEvent.SaveConceptAndClose) }) {
                        Text(text = stringResource(id = R.string.save_and_close))
                    }
                }

            }
        }

    }
}