package com.legacy.legacy_android.domain.repository.course

import android.util.Log
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.network.course.all.AllCourseService
import com.legacy.legacy_android.feature.network.course.all.EventCourseService
import com.legacy.legacy_android.feature.network.course.all.PopularCourseService
import com.legacy.legacy_android.feature.network.course.all.RecentCourseService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepository @Inject constructor(
    private val allCourseService: AllCourseService,
    private val popularCourseService: PopularCourseService,
    private val recentCourseService: RecentCourseService,
    private val eventCourseService: EventCourseService
) {
    suspend fun getAllCourse(): Result<List<AllCourseResponse>> {
        return try {
            val response = allCourseService.getAllCourse()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("코스 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Log.e("CourseRepository", "코스를 불러올 수 없습니다.", e)
            Result.failure(e)
        }
    }

    suspend fun getEventCourse(): Result<List<AllCourseResponse>> {
        return try {
            val response = eventCourseService.getEventCourse()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("이벤트 코스 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Log.e("CourseRepository", "코스를 불러올 수 없습니다.", e)
            Result.failure(e)
        }
    }

    suspend fun getRecentCourse(): Result<List<AllCourseResponse>> {
        return try {
            val response = recentCourseService.getRecentCourse()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("리센트 코스 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Log.e("CourseRepository", "코스를 불러올 수 없습니다.", e)
            Result.failure(e)
        }
    }

    suspend fun getPopularCourse(): Result<List<AllCourseResponse>> {
        return try {
            val response = popularCourseService.getPopularCourse()
            val data = response.data
            if (data != null) {
                Result.success(data)
            } else {
                Result.failure(NullPointerException("팝 코스 데이터가 null입니다."))
            }
        } catch (e: Exception) {
            Log.e("CourseRepository", "코스를 불러올 수 없습니다.", e)
            Result.failure(e)
        }
    }
}