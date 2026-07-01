package clickretina.assasement.skillforge.network

import clickretina.assasement.skillforge.model.CourseResponse
import retrofit2.http.GET

interface ApiService {

    @GET("android-assesment/notes/refs/heads/main/data.json")
    suspend fun getCourses(): CourseResponse
}