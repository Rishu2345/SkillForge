package clickretina.assasement.skillforge.viewModel.lessonPlayerViewModel

import clickretina.assasement.skillforge.model.Course
import clickretina.assasement.skillforge.model.Instructor
import clickretina.assasement.skillforge.model.Lesson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LessonPlayerUiStateTest {

    @Test
    fun buildLessonPlayerDataClampsOutOfRangeLessonIndex() {
        val data = buildLessonPlayerData(course = sampleCourse(), lessonIndex = 99)

        assertEquals(1, data?.currentLessonIndex)
        assertEquals(2, data?.currentLessonNumber)
        assertEquals("Variables & Null Safety", data?.currentLesson?.title)
    }

    @Test
    fun buildLessonPlayerDataClampsTabIndex() {
        val data = buildLessonPlayerData(course = sampleCourse(), lessonIndex = 0, activeTabIndex = 99)

        assertEquals(2, data?.activeTabIndex)
    }

    @Test
    fun buildLessonPlayerDataReturnsNullForEmptyLessons() {
        val course = sampleCourse().copy(lessons = emptyList())

        assertNull(buildLessonPlayerData(course = course, lessonIndex = 0))
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
