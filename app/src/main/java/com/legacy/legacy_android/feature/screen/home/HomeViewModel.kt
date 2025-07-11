package com.legacy.legacy_android.feature.screen.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.legacy.legacy_android.feature.network.block.Get.GetBlockRequest
import com.legacy.legacy_android.feature.network.block.Get.GetBlockResponse
import com.legacy.legacy_android.feature.network.block.Get.GetBlockService
import com.legacy.legacy_android.feature.network.block.Post.PostBlockRequest
import com.legacy.legacy_android.feature.network.block.Post.PostBlockService
import com.legacy.legacy_android.feature.network.ruins.RuinsMapRequest
import com.legacy.legacy_android.feature.network.ruins.RuinsMapResponse
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import com.legacy.legacy_android.feature.network.ruinsId.RuinsIdResponse
import com.legacy.legacy_android.feature.network.ruinsId.RuinsIdService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.legacy.legacy_android.domain.repository.UserRepository

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ruinsMapService: RuinsMapService,
    private val ruinsIdService: RuinsIdService,
    private val postBlockService: PostBlockService,
    private val getBlockService: GetBlockService,
    private val userRepository: UserRepository
): ViewModel() {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val profileFlow = userRepository.profile

    var minLat: Double = 0.0
    var maxLat: Double = 0.0
    var minLng: Double = 0.0
    var maxLng: Double = 0.0

    var selectedId = mutableIntStateOf(-1)

    var ruinsData = mutableListOf<RuinsMapResponse>()
    var ruinsIdData = mutableStateOf<RuinsIdResponse?>(null)

    var blockData by mutableStateOf<List<GetBlockResponse>>(emptyList())

    fun isFinite(vararg values: Double): Boolean {
        return values.all { it.isFinite() }
    }


    // ruin 정보 들고오기
    fun fetchRuinsId(id: Int) {
        viewModelScope.launch {
            try {
                val response = ruinsIdService.getRuinsById(id = id)
                ruinsIdData.value = response.data
                Log.d("RuinsId", "성공: ${response.data}")
            } catch (e: Exception) {
                Log.e("RuinsId", "에러: ${e.message}")
            }
        }
    }

    fun fetchBlock(latitude: Double?, longitude: Double?, userId: Long?){
        viewModelScope.launch {
            try {
                val request = PostBlockRequest(
                    blockType = "NORMAL",
                    latitude = latitude,
                    longitude = longitude,
                    userId = userId,
                    mobileOrWebsite = "MOBILE"
                )
//                val response = postBlockService.block(request)
//                Log.d("PostMap", "블록 생성 성공: ${response.blockId}")
            }catch (e: Exception){
                Log.e("PostMap", "에러: ${e.message}")
            }
        }
    }

    fun fetchGetBlock(userId: Long?){
        viewModelScope.launch {
            try{
                val response = getBlockService.getBlockById(userId)
                blockData = response.data ?: emptyList()
                Log.d("GetMap", "블럭 불러오기 성공 ${response.data}")
            }catch (e: Exception){
                Log.e("GetMap", "에러: ${e.message}")
            }
        }
    }

    fun fetchRuinsMap(minLat: Double, maxLat: Double, minLng: Double, maxLng: Double) {
        if (!isFinite(minLat, maxLat, minLng, maxLng)) {
            Log.e("RuinsMap", "좌표에 Infinity 또는 NaN이 포함되어 요청하지 않음")
            return
        }
        viewModelScope.launch {
            try {
                val request = RuinsMapRequest(minLat, maxLat, minLng, maxLng)
                val response = ruinsMapService.ruinsMap(minLat = request.minLat, maxLat = request.maxLat, minLng = request.minLng, maxLng = request.maxLng)
                ruinsData = response.data as MutableList<RuinsMapResponse>
            } catch (e: Exception) {
                Log.e("RuinsMap", "에러: ${e}")
            }
        }
    }
}
