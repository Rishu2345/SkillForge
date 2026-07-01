package clickretina.assasement.skillforge.viewModel.courseDetailsViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import clickretina.assasement.skillforge.model.Lesson
import clickretina.assasement.skillforge.navigation.CourseDetailsRoute
import clickretina.assasement.skillforge.navigation.LessonPlayerRoute
import clickretina.assasement.skillforge.navigation.NavCommand
import clickretina.assasement.skillforge.viewModel.AppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CourseDetailsViewModel(
    savedStateHandle: SavedStateHandle,
) : AppViewModel(savedStateHandle) {

    private val route: CourseDetailsRoute = savedStateHandle.toRoute()

    private val _uiState = MutableStateFlow<CourseDetailsUiState>(
        CourseDetailsUiState.Success(buildCourseDetailsData(route.course)),
    )
    val uiState: StateFlow<CourseDetailsUiState> = _uiState.asStateFlow()

    fun retry() {
        _uiState.value = CourseDetailsUiState.Success(buildCourseDetailsData(route.course))
    }

    fun onBackClick() {
        _navCommandFlow.tryEmit(NavCommand.PopUp())
    }

    fun onLessonClick(lesson: Lesson) {
        val lessonIndex = route.course.lessons.indexOf(lesson).takeIf { it >= 0 } ?: 0
        _navCommandFlow.tryEmit(
            NavCommand.Navigate(LessonPlayerRoute.create(route.course, lessonIndex)),
        )
    }
}
