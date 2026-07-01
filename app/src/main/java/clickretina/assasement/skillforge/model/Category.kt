package clickretina.assasement.skillforge.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val courses: List<Course>
)