package clickretina.assasement.skillforge.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startingScreen: AppRoute,
    modifier: Modifier,
) {
    val appNavController = remember {
        AppComposeNavController(navController)
    }

    NavHost(
        navController = navController,
        startDestination = startingScreen,
        modifier = modifier,
    ) {

    }
}
