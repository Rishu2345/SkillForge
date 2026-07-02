package clickretina.assasement.skillforge.viewModel.homeScreenViewModel

import clickretina.assasement.skillforge.model.Category
import clickretina.assasement.skillforge.model.Course

data class HomeData(
    val categories: List<CategoryUiModel>,
    val popularCourses: List<CourseUiModel>,
)

data class CategoryUiModel(
    val name: String,
    val courseCount: Int,
    val iconColorHex: String,
)

data class CourseUiModel(
    val course: Course,
    val title: String,
    val rating: Double,
    val durationHours: Double,
    val thumbnailUrl: String,
    val level: String,
    val instructorName: String,
)

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val data: HomeData) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

fun buildHomeData(categories: List<Category>): HomeData {
    return HomeData(
        categories = categories.map { category ->
            CategoryUiModel(
                name = category.name,
                courseCount = category.courses.size,
                iconColorHex = category.iconColorHex(),
            )
        },
        popularCourses = categories.mapNotNull { it.courses.firstOrNull() }.map { it.toUiModel() },
    )
}

fun Course.toUiModel(): CourseUiModel {
    return CourseUiModel(
        course = this,
        title = title,
        rating = rating,
        durationHours = durationHours,
        thumbnailUrl = thumbnailUrl,
        level = level,
        instructorName = instructor.name,
    )
}

private fun Category.iconColorHex(): String {
    return when (name.lowercase()) {
        "android development" -> "#2DD4BF"
        "backend & apis" -> "#34D399"
        "product & ui design" -> "#FBBF24"
        else -> "#2DD4BF"
    }
}

