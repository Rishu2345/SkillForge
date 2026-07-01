package clickretina.assasement.skillforge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun NavCommandEffect(
    navHostController: AppComposeNavController,
    navCommandFlow: Flow<NavCommand>,
) {
    LaunchedEffect(navHostController) {
        navCommandFlow.collect { navCommand ->
            navHostController.onCollectNavCommand(navCommand)
        }
    }
}
