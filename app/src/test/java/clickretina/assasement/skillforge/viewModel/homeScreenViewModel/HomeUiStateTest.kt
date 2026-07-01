package clickretina.assasement.skillforge.viewModel.homeScreenViewModel

import clickretina.assasement.skillforge.model.Category
import clickretina.assasement.skillforge.model.Course
import clickretina.assasement.skillforge.model.Instructor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeUiStateTest {

    @Test
    fun buildHomeDataKeepsCategoriesAndUsesFirstCourseFromEachCategory() {
        val categories = listOf(
            Category(
                name = "Android Development",
                courses = listOf(
                    course("Kotlin Fundamentals"),
                    course("Jetpack Compose Essentials"),
                ),
            ),
            Category(
                name = "Backend & APIs",
                courses = listOf(course("Node.js from Scratch")),
            ),
        )

        val data = buildHomeData(categories)

        assertEquals(listOf("Android Development", "Backend & APIs"), data.categories.map { it.name })
        assertEquals(listOf(2, 1), data.categories.map { it.courseCount })
        assertEquals(listOf("Kotlin Fundamentals", "Node.js from Scratch"), data.popularCourses.map { it.title })
    }

    @Test
    fun buildHomeDataHandlesEmptyCategories() {
        val data = buildHomeData(emptyList())

        assertTrue(data.categories.isEmpty())
        assertTrue(data.popularCourses.isEmpty())
    }

    private fun course(title: String): Course {
        return Course(
            title = title,
            rating = 4.7,
            durationHours = 6.5,
            thumbnailUrl = "https://example.com/course.png",
            level = "Beginner",
            instructor = Instructor(
                name = "Aarav Sharma",
                avatarUrl = "https://example.com/avatar.png",
                title = "Senior Android Engineer",
            ),
            lessons = emptyList(),
        )
    }
}
