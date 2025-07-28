package com.legacy.legacy_android.domain.repository.home

import android.util.Log
import com.legacy.legacy_android.feature.network.block.Get.GetBlockResponse
import com.legacy.legacy_android.feature.network.block.Get.GetBlockService
import com.legacy.legacy_android.feature.network.block.Post.PostBlockRequest
import com.legacy.legacy_android.feature.network.block.Post.PostBlockService
import com.legacy.legacy_android.res.component.adventure.PolygonStyle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockRepository @Inject constructor(
    private val postBlockService: PostBlockService,
    private val getBlockService: GetBlockService
) {
    suspend fun createBlock(latitude: Double, longitude: Double): Result<Unit> {
        return try {
            val request = PostBlockRequest(latitude = latitude, longitude = longitude)
            postBlockService.block(request)
            Log.d("BlockRepository", "블록 추가 성공")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("BlockRepository", "이미 추가 된 곳입니다.")
            Result.failure(e)
        }
    }

    suspend fun getBlocks(): Result<List<GetBlockResponse>> {
        return try {
            val response = getBlockService.getBlockById()
            val rawBlocks = response.data ?: emptyList()
            val distinctBlocks = rawBlocks.distinctBy { block ->
                PolygonStyle.getGridKey(block.latitude, block.longitude)
            }
            Result.success(distinctBlocks)
        } catch (e: Exception) {
            Log.e("BlockRepository", "Error fetching blocks: ${e.message}")
            Result.failure(e)
        }
    }
}
