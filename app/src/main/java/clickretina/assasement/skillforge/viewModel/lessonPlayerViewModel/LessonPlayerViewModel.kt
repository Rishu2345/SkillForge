package clickretina.assasement.skillforge.viewModel.lessonPlayerViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import clickretina.assasement.skillforge.navigation.LessonPlayerRoute
import clickretina.assasement.skillforge.navigation.NavCommand
import clickretina.assasement.skillforge.viewModel.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LessonPlayerViewModel(
    savedStateHandle: SavedStateHandle,
) : AppViewModel(savedStateHandle) {

    private val route: LessonPlayerRoute = savedStateHandle.toRoute()
    private var selectedLessonIndex = route.lessonIndex
    private var activeTabIndex = 0
    private var isPlaying = false

    private val _uiState = MutableStateFlow<LessonPlayerUiState>(buildState())
    val uiState: StateFlow<LessonPlayerUiState> = _uiState.asStateFlow()

    fun retry() {
        _uiState.value = buildState()
    }

    fun onBackClick() {
        _navCommandFlow.tryEmit(NavCommand.PopUp())
    }

    fun onTabSelected(index: Int) {
        activeTabIndex = index.coerceIn(0, 2)
        _uiState.value = buildState()
    }

    fun onLessonClick(lessonIndex: Int) {
        selectedLessonIndex = lessonIndex
        activeTabIndex = 0
        _uiState.value = buildState()
    }

    fun onPlayPauseClick() {
        isPlaying = !isPlaying
        _uiState.value = buildState()
    }

    private fun buildState(): LessonPlayerUiState {
        return buildLessonPlayerData(
            course = route.course,
            lessonIndex = selectedLessonIndex,
            activeTabIndex = activeTabIndex,
            isPlaying = isPlaying,
        )?.let { data ->
            selectedLessonIndex = data.currentLessonIndex
            LessonPlayerUiState.Success(data)
        } ?: LessonPlayerUiState.Error("Unable to load lesson")
    }
}
