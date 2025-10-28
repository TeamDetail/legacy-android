package com.legacy.legacy_android.feature.network.course.all

import com.legacy.legacy_android.feature.data.core.BaseResponse
import com.legacy.legacy_android.feature.network.course.search.SearchCourseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AllCourseService{
    @GET("/course")
    suspend fun getAllCourse(): BaseResponse<List<SearchCourseResponse>>
}
interface CourseByIdService{
    @GET("/course/{courseId}")
    suspend fun getCourseById(@Path("courseId") courseId : Int): BaseResponse<AllCourseResponse>

    @PATCH("/course")
    suspend fun patchHeart(
        @Body request: PatchHeartRequest
    ): BaseResponse<Nothing>

}
interface PopularCourseService {
    @GET("/course/popular")
    suspend fun getPopularCourse(): BaseResponse<List<SearchCourseResponse>>
}
interface RecentCourseService {
    @GET("/course/recent")
    suspend fun getRecentCourse(): BaseResponse<List<SearchCourseResponse>>
}

interface EventCourseService {
    @GET("/course/event")
    suspend fun getEventCourse(): BaseResponse<List<SearchCourseResponse>>
}
interface CreateCourseService {
    @POST("/course")
    suspend fun createCourse(
        @Body data: CreateCourseRequest
    ): BaseResponse<SearchCourseResponse>
}