package com.legacy.legacy_android.feature.network.course.search

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchCourseService {
    @GET("/course/search")
    suspend fun getSearchCourse(
        @Query("courseName") courseName: String
    ): BaseResponse<List<SearchCourseResponse>>
}