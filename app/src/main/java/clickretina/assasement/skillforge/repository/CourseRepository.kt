package clickretina.assasement.skillforge.repository

import clickretina.assasement.skillforge.network.ApiService

class CourseRepository(
    private val api : ApiService
) {
    suspend fun getCourses() =
        api.getCourses()
}