package com.example.arking.ui.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.arking.R
import com.example.arking.model.Contract
import com.example.arking.model.Part
import com.example.arking.model.Prototype

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current;
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .wrapContentSize(Alignment.Center)
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.insertPrototype(Prototype(1, "Lilly"))
            viewModel.insertPrototype(Prototype(2, "Girasol"))
            viewModel.insertPrototype(Prototype(3, "Tulipan"))
            viewModel.insertPrototype(Prototype(4, "Rosa morada"))
            viewModel.insertPrototype(Prototype(5, "Colibrí"))
            viewModel.insertContract(Contract(1,"Contrato 1","Contrato lega 20 casas manzana 3 y 4", "Open"))
            viewModel.insertContract(Contract(2,"Contrato 2","Contrato lega 10 casas manzana 1 y 2", "Open"))
            viewModel.insertContract(Contract(3,"Contrato 3","Contrato lega 15 casas manzana 1", "Open"))
            viewModel.insertPart(Part(1,"Lote 10 manzana 3","Lote 10 manzana 3",1,1))
            viewModel.insertPart(Part(2,"Lote 11 manzana 3","Lote 11 manzana 3",1,1))
            viewModel.insertPart(Part(3,"Lote 12 manzana 3","Lote 12 manzana 3",1,1))

            viewModel.insertPart(Part(4,"Lote 20 manzana 4","Lote 20 manzana 4",2,2))
            viewModel.insertPart(Part(5,"Lote 21 manzana 4","Lote 21 manzana 4",2,2))
            viewModel.insertPart(Part(6,"Lote 22 manzana 4","Lote 22 manzana 4",2,2))

            viewModel.insertPart(Part(7,"Lote 30 manzana 1","Lote 30 manzana 1",3,3))
            viewModel.insertPart(Part(8,"Lote 31 manzana 1","Lote 31 manzana 1",3,3))
            viewModel.insertPart(Part(9,"Lote 32 manzana 1","Lote 32 manzana 1",3,3))
            Toast.makeText(context, "Proceso realizado correctamente", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = stringResource(R.string.sync_download))
        }
    }


}