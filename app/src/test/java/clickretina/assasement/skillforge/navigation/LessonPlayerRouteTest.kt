package clickretina.assasement.skillforge.navigation

import clickretina.assasement.skillforge.model.Course
import clickretina.assasement.skillforge.model.Instructor
import clickretina.assasement.skillforge.model.Lesson
import org.junit.Assert.assertEquals
import org.junit.Test

class LessonPlayerRouteTest {

    @Test
    fun createRoundTripsCourseAndPreservesLessonIndex() {
        val course = sampleCourse()

        val route = LessonPlayerRoute.create(course, 1)

        assertEquals(course, route.course)
        assertEquals(1, route.lessonIndex)
    }

    private fun sampleCourse(): Course {
        return Course(
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
            ),
        )
    }
}
