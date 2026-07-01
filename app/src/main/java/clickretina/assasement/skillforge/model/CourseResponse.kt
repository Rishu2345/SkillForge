package clickretina.assasement.skillforge.model

import kotlinx.serialization.Serializable

@Serializable
data class CourseResponse(
    val categories: List<Category>
)