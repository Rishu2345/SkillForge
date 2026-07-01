package clickretina.assasement.skillforge.viewModel.lessonPlayerViewModel

import clickretina.assasement.skillforge.model.Course
import clickretina.assasement.skillforge.model.Lesson
import kotlin.math.roundToInt

data class LessonPlayerData(
    val course: Course,
    val courseTitle: String,
    val currentLesson: Lesson,
    val currentLessonIndex: Int,
    val currentLessonNumber: Int,
    val lessons: List<LessonPlayerLessonUiModel>,
    val activeTabIndex: Int,
    val isPlaying: Boolean,
    val currentTimestampText: String,
    val totalDurationText: String,
    val progressFraction: Float,
)

data class LessonPlayerLessonUiModel(
    val lesson: Lesson,
    val index: Int,
    val number: Int,
    val title: String,
    val durationMinutes: Int,
    val isFree: Boolean,
    val isCurrent: Boolean,
)

sealed interface LessonPlayerUiState {
    data object Loading : LessonPlayerUiState
    data class Success(val data: LessonPlayerData) : LessonPlayerUiState
    data class Error(val message: String) : LessonPlayerUiState
}

fun buildLessonPlayerData(
    course: Course,
    lessonIndex: Int,
    activeTabIndex: Int = 0,
    isPlaying: Boolean = false,
): LessonPlayerData? {
    val lessons = course.lessons
    if (lessons.isEmpty()) return null

    val clampedIndex = lessonIndex.coerceIn(0, lessons.lastIndex)
    val currentLesson = lessons[clampedIndex]
    val progressFraction = 0.36f
    val currentMinute = (currentLesson.durationMinutes * progressFraction).roundToInt()

    return LessonPlayerData(
        course = course,
        courseTitle = course.title,
        currentLesson = currentLesson,
        currentLessonIndex = clampedIndex,
        currentLessonNumber = clampedIndex + 1,
        lessons = lessons.mapIndexed { index, lesson ->
            LessonPlayerLessonUiModel(
                lesson = lesson,
                index = index,
                number = index + 1,
                title = lesson.title,
                durationMinutes = lesson.durationMinutes,
                isFree = lesson.isFree,
                isCurrent = index == clampedIndex,
            )
        },
        activeTabIndex = activeTabIndex.coerceIn(0, 2),
        isPlaying = isPlaying,
        currentTimestampText = currentMinute.formatTimestamp(),
        totalDurationText = currentLesson.durationMinutes.formatTimestamp(),
        progressFraction = progressFraction,
    )
}

private fun Int.formatTimestamp(): String {
    val minutes = coerceAtLeast(0)
    return "%02d:%02d".format(minutes, 0)
}
