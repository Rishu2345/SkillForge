package clickretina.assasement.skillforge.model

import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val title: String,
    val durationMinutes: Int,
    val isFree: Boolean,
    val content: String
)