package com.example.arking.ui.contracts

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arking.model.Contract
import com.example.arking.model.Part
import com.example.arking.presentation.contracts.ContractState
import com.example.arking.presentation.contracts.ContractViewModel

/**
 * The Contracts screen.
 */
@Composable
fun ContractsScreen(
    onContractClick: (Contract) -> Unit = {},
    viewModel: ContractViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ContractContent(state,onContractClick)
}
@Composable
fun ContractContent(contractState: ContractState, onContractClick: (Contract) -> Unit = {}) {
    if(contractState.contracts.any()){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                contractState.contracts.forEach { item ->
                    item {
                        Accordion(model = item, onContractClick = onContractClick)
                    }
                }
            }
        )
    }
    else{
        Text(text = "Sin registros")
    }
    
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Accordion(modifier: Modifier = Modifier, model: Contract,onContractClick: (Contract) -> Unit = {},) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        AccordionHeader(contract = model, isExpanded = expanded) {
            //expanded = !expanded
            onContractClick(model)
            //navController.navigate("contracts/"+ model.id)
        }
    }

    /*
    Column(
        modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)

        ) {

        AnimatedVisibility(visible = expanded) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                model.Part.forEach(){
                    Row(modifier= Modifier.fillMaxWidth()
                        ) {
                        Text(it.name)
                    }

                    Divider(color = Color.Gray, thickness = 1.dp)
                }
                /*LazyColumn()
                {
                    items(model.Part){
                        AccordionRow(it){
                            navController.navigate("tasks/" + it.id)
                        }
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }*/
            }
        }
    }*/
}
@Composable
private fun AccordionHeader(
    contract: Contract,
    isExpanded: Boolean = false,
    onTapped: () -> Unit = {}
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, GetColorStatus(contract)),
    ) {
        Row(
            modifier = Modifier
                .clickable { onTapped() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Text(contract.name, Modifier, color = Color.Black, fontSize = 20.sp)
                Text(contract.description, Modifier, color = Color.Gray)
            }
            Icon(
                Icons.Outlined.KeyboardArrowRight,
                contentDescription = "arrow-right",
                tint = Color.Gray
            )
        }
    }
}

private fun GetColorStatus(contract: Contract): Color {
    if(contract.status == "Open")
        return Color.Blue
    if(contract.status == "Pending")
        return Color.Gray
    if(contract.status == "Done")
        return Color.Green
    if(contract.status == "Canceled")
        return Color.Red
    return Color.Blue
}

@Preview(name="Open")
@Composable
private fun AccordionHeaderPreview(){
    AccordionHeader(
        Contract(1,"Contracto 1", "Descripcion de prueba del contrato","Open"
        ), true)
}

@Composable
private fun AccordionRow(
    model: Part,
    onTapped: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onTapped() },

    ) {
        Text(model.name, Modifier.weight(1f))
        Icon(
            Icons.Outlined.KeyboardArrowRight,
            contentDescription = "arrow-right",
            tint = Color.Gray
        )
    }
}
@Preview
@Composable
private fun AccordionRowPreview(){
    AccordionRow(Part(2,"Tarea 1", "Description",1,1))
}

/*@Composable
fun ContractCard(contract: Contract, onContractSelected: (Contract) -> Unit) {
    Column(modifier = Modifier.clickable { onContractSelected(contract) }) {
        Text(text = contract.name)
        Text(text = contract.description)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractList(contracts: List<Contract>, onContractSelected: (Contract) -> Unit) {
    LazyColumn {
        items(contracts) {contract  ->
            ContractCard(contract, onContractSelected)
        }
    }
}
@Composable
fun PartCard(part: Part, onPartelected: (Part) -> Unit) {
    Column(modifier = Modifier.clickable { onPartelected(part) }) {
        Text(text = part.name)
        Text(text = part.description)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartList(Part: List<Part> , onPartelected: (Part) -> Unit) {
    LazyColumn {
        items(Part) {contract  ->
            PartCard(contract,onPartelected)
        }
    }
}*/