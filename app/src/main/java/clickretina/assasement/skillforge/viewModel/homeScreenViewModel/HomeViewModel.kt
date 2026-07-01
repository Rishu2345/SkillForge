package clickretina.assasement.skillforge.viewModel.homeScreenViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import clickretina.assasement.skillforge.model.Course
import clickretina.assasement.skillforge.navigation.CourseDetailsRoute
import clickretina.assasement.skillforge.navigation.NavCommand
import clickretina.assasement.skillforge.repository.CourseRepository
import clickretina.assasement.skillforge.viewModel.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: CourseRepository,
) : AppViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHome()
    }

    fun retry() {
        loadHome()
    }

    fun onCourseClick(course: Course) {
        _navCommandFlow.tryEmit(NavCommand.Navigate(CourseDetailsRoute.create(course)))
    }

    private fun loadHome() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            runCatching { repository.getCourses() }
                .onSuccess { response ->
                    _uiState.value = HomeUiState.Success(buildHomeData(response.categories))
                }
                .onFailure {
                    _uiState.value = HomeUiState.Error("Something went wrong. Please try again.")
                }
        }
    }
}
