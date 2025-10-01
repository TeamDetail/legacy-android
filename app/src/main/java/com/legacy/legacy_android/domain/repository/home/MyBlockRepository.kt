package com.legacy.legacy_android.domain.repository.home

import com.legacy.legacy_android.feature.network.block.get.GetBlockResponse
import com.legacy.legacy_android.feature.network.block.get.GetBlockService
import com.legacy.legacy_android.feature.network.block.post.PostBlockRequest
import com.legacy.legacy_android.feature.network.block.post.PostBlockService
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
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBlocks(): Result<List<GetBlockResponse>> {
        return try {
            val response = getBlockService.getBlock()
            val rawBlocks = response.data ?: emptyList()
            val distinctBlocks = rawBlocks.distinctBy { block ->
                PolygonStyle.getGridKey(block.latitude, block.longitude)
            }
            Result.success(distinctBlocks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
