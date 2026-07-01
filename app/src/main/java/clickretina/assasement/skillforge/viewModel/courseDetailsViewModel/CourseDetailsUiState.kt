package clickretina.assasement.skillforge.viewModel.courseDetailsViewModel

import clickretina.assasement.skillforge.model.Course
import clickretina.assasement.skillforge.model.Instructor
import clickretina.assasement.skillforge.model.Lesson

data class CourseDetailsData(
    val course: Course,
    val title: String,
    val subtitle: String,
    val description: String,
    val thumbnailUrl: String,
    val rating: Double,
    val studentsEnrolled: Int,
    val durationHours: Double,
    val level: String,
    val tags: List<String>,
    val instructor: Instructor,
    val lessons: List<LessonUiModel>,
    val lessonCount: Int,
    val totalLessonMinutes: Int,
)

data class LessonUiModel(
    val lesson: Lesson,
    val number: Int,
    val title: String,
    val durationMinutes: Int,
    val isFree: Boolean,
)

sealed interface CourseDetailsUiState {
    data object Loading : CourseDetailsUiState
    data class Success(val data: CourseDetailsData) : CourseDetailsUiState
    data class Error(val message: String) : CourseDetailsUiState
}

fun buildCourseDetailsData(course: Course): CourseDetailsData {
    return CourseDetailsData(
        course = course,
        title = course.title,
        subtitle = "",
        description = course.lessons.firstOrNull()?.content.orEmpty(),
        thumbnailUrl = course.thumbnailUrl,
        rating = course.rating,
        studentsEnrolled = 0,
        durationHours = course.durationHours,
        level = course.level,
        tags = emptyList(),
        instructor = course.instructor,
        lessons = course.lessons.mapIndexed { index, lesson ->
            LessonUiModel(
                lesson = lesson,
                number = index + 1,
                title = lesson.title,
                durationMinutes = lesson.durationMinutes,
                isFree = lesson.isFree,
            )
        },
        lessonCount = course.lessons.size,
        totalLessonMinutes = course.lessons.sumOf { it.durationMinutes },
    )
}

