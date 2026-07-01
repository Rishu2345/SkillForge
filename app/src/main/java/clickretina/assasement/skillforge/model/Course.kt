package clickretina.assasement.skillforge.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val title: String,
    val rating: Double,
    val durationHours: Double,
    val thumbnailUrl: String,
    val level: String,
    val instructor: Instructor,
    val lessons: List<Lesson>
)