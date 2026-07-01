package clickretina.assasement.skillforge.model

import kotlinx.serialization.Serializable

@Serializable
data class Instructor(
    val name: String,
    val avatarUrl: String,
    val title: String
)