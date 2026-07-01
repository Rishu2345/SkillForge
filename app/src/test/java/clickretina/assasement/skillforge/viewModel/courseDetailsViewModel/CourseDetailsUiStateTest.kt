package clickretina.assasement.skillforge.viewModel.courseDetailsViewModel

import clickretina.assasement.skillforge.model.Course
import clickretina.assasement.skillforge.model.Instructor
import clickretina.assasement.skillforge.model.Lesson
import org.junit.Assert.assertEquals
import org.junit.Test

class CourseDetailsUiStateTest {

    @Test
    fun buildCourseDetailsDataComputesLessonCountAndTotalMinutes() {
        val course = Course(
            title = "Kotlin Fundamentals",
            rating = 4.7,
            durationHours = 6.5,
            thumbnailUrl = "https://example.com/course.png",
            level = "Beginner",
            instructor = Instructor(
                name = "Aarav Sharma",
                avatarUrl = "https://example.com/avatar.png",
                title = "Senior Android Engineer",
            ),
            lessons = listOf(
                Lesson(title = "Welcome & Setup", durationMinutes = 8, isFree = true, content = "Setup."),
                Lesson(title = "Variables & Null Safety", durationMinutes = 15, isFree = true, content = "Null safety."),
                Lesson(title = "Functions & Lambdas", durationMinutes = 18, isFree = false, content = "Functions."),
            ),
        )

        val data = buildCourseDetailsData(course)

        assertEquals(3, data.lessonCount)
        assertEquals(41, data.totalLessonMinutes)
        assertEquals(listOf(1, 2, 3), data.lessons.map { it.number })
    }
}
