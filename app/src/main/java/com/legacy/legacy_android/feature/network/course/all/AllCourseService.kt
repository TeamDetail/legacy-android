package com.legacy.legacy_android.feature.network.course.all

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET

interface AllCourseService{
    @GET("/course")
    suspend fun getAllCourse(): BaseResponse<List<AllCourseResponse>>
}
interface PopularCourseService {
    @GET("/course/popular")
    suspend fun getPopularCourse(): BaseResponse<List<AllCourseResponse>>
}
interface RecentCourseService {
    @GET("/course/recent")
    suspend fun getRecentCourse(): BaseResponse<List<AllCourseResponse>>
}

interface EventCourseService {
    @GET("/course/event")
    suspend fun getEventCourse(): BaseResponse<List<AllCourseResponse>>
}