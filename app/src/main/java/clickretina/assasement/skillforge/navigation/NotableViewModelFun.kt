package clickretina.assasement.skillforge.navigation

import androidx.compose.runtime.Composable
import clickretina.assasement.skillforge.viewModel.AppViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
inline fun <reified T : AppViewModel> appViewModel(
    navController: NotableComposeNavController,
): T {

    val viewModel: T = koinViewModel()



    NavCommandEffect(
        navHostController = navController,
        navCommandFlow = viewModel.navCommandFlow
    )

    return viewModel
}

