package com.example.arking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.arking.ui.contracts.ContractsScreen
import com.example.arking.ui.prototypes.PrototypesScreen

interface IDestination {
    val icon: ImageVector
    val route: String
    val title: String
}
object Contracts : IDestination {
    override val icon = Icons.Filled.Home
    override val route = "contracts"
    override val title = "Contratos"
}

object Prototypes : IDestination {
    override val icon = Icons.Filled.List
    override val route = "prototypes"
    override val title = "Modelos"
}

object Tasks : IDestination {
    override val icon = Icons.Filled.List
    override val route = "tasks/{id}"
    override val title = "Tareas"
}
object Camera : IDestination {
    override val icon = Icons.Filled.List
    override val route = "camera/{id}"
    override val title = "Tareas"
}
object Signature : IDestination {
    override val icon = Icons.Filled.List
    override val route = "signature"
    override val title = "Firma"
}
object PartDetail : IDestination {
    override val icon = Icons.Filled.List
    override val route = "part"
    override val title = "Lote"
}
object ContractDetail : IDestination {
    override val icon = Icons.Filled.List
    override val route = "contracts/{id}"
    override val title = "Lote"
}
object Settings : IDestination {
    override val icon = Icons.Filled.Settings
    override val route = "settings"
    override val title = "Configuración"
}
object Otis : IDestination {
    override val icon = Icons.Filled.Architecture
    override val route = com.example.arking.feature_otis.util.Screen.OtisScreen.route
    override val title = "Otis"
}
val arkingTabRowScreens = listOf(Contracts, Prototypes, Otis,Settings)
