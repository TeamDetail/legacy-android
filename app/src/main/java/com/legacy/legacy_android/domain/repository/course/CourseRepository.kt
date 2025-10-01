package com.legacy.legacy_android.domain.repository.course

import android.util.Log
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.feature.network.course.all.AllCourseResponse
import com.legacy.legacy_android.feature.network.course.all.AllCourseService
import com.legacy.legacy_android.feature.network.course.all.CourseByIdService
import com.legacy.legacy_android.feature.network.course.all.CreateCourseRequest
import com.legacy.legacy_android.feature.network.course.all.CreateCourseService
import com.legacy.legacy_android.feature.network.course.all.EventCourseService
import com.legacy.legacy_android.feature.network.course.all.PatchHeartRequest
import com.legacy.legacy_android.feature.network.course.all.PopularCourseService
import com.legacy.legacy_android.feature.network.course.all.RecentCourseService
import com.legacy.legacy_android.feature.network.course.search.SearchCourseResponse
import com.legacy.legacy_android.feature.network.course.search.SearchCourseService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepository @Inject constructor(
    private val allCourseService: AllCourseService,
    private val popularCourseService: PopularCourseService,
    private val recentCourseService: RecentCourseService,
    private val eventCourseService: EventCourseService,
    private val searchCourseService: SearchCourseService,
    private val createCourseService: CreateCourseService,
    private val courseByIdService: CourseByIdService,
) {
    suspend fun getAllCourse(): Result<List<SearchCourseResponse>> {
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

    suspend fun getEventCourse(): Result<List<SearchCourseResponse>> {
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

    suspend fun getRecentCourse(): Result<List<SearchCourseResponse>> {
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

    suspend fun getPopularCourse(): Result<List<SearchCourseResponse>> {
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

    suspend fun getSearchCourse(name: String): Result<List<SearchCourseResponse>> {
        return try {
            val response = searchCourseService.getSearchCourse(name)
            val data = response.data
            if (data != null) {
                println("search Course성공")
                Result.success(data)
            } else {
                Result.failure(NullPointerException("SearchCourse data null"))
            }
        } catch (e: Exception) {
            Log.e("CourseRepository", "서치 코스 에러", e)
            Result.failure(e)
        }
    }

    suspend fun createCourse(data: CreateCourseRequest): Result<SearchCourseResponse> {
        return try {
            val response = createCourseService.createCourse(data)
            if (response.status == 200 || response.status == 201) {
                val body = response.data
                if (body != null) {
                    println("Course 만들기 성공")
                    Result.success(body)
                } else {
                    Result.failure(Exception("Course 만들기 성공했지만 data가 null임"))
                }
            } else {
                Result.failure(Exception("Course 만들기 실패: ${response.status}"))
            }
        } catch (e: Exception) {
            Log.e("CourseRepository", "코스 만들기 에러", e)
            Result.failure(e)
        }
    }

    suspend fun getCourseById(id: Int): Result<AllCourseResponse> {
        return try {
            val response = courseByIdService.getCourseById(id)
            val data = response.data
            if (data != null) {
                println("getCourseById 성공")
                Result.success(data)
            } else {
                Result.failure(NullPointerException("CourseById data null"))
            }
        } catch (e: Exception) {
            Log.e("CourseRepository", "CourseById ${id} 에러", e)
            Result.failure(e)
        }
    }


    suspend fun patchHeart(id: PatchHeartRequest): Result<Unit> {
        return try {
            val response = courseByIdService.patchHeart(id)
            if (response.status == 200) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("서버 에러: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}