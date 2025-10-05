package com.legacy.legacy_android.feature.network.core.remote

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.annotation.RequiresPermission
import com.legacy.legacy_android.LegacyApplication
import com.legacy.legacy_android.feature.data.user.getAccToken
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


class RequestInterceptor @Inject constructor(
    private val networkUtil: NetworkUtil
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!networkUtil.isNetworkConnected()) {
                throw NoConnectivityException()
            }

            val request = chain.request()
            val context = LegacyApplication.getContext()

            val newRequest = request.newBuilder()
                .removeHeader("Authorization")
                .header("Authorization", "Bearer ${getAccToken(context)}")
                .build()

            return try {
                chain.proceed(newRequest)
            } catch (e: IOException) {
                throw NetworkException("네트워크 통신 중 오류가 발생했습니다: ${e.message}")
            }
        } catch (e: NoConnectivityException) {
            throw e
        } catch (e: Exception) {
            Log.e("NetworkInterceptor", "Error: ${e.message}", e)
            throw NetworkException(e.message ?: "네트워크 오류가 발생했습니다.")
        }
    }
}

@Singleton
class NetworkUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork

        Log.d("NetworkUtil", "Active network: $activeNetwork")

        if (activeNetwork == null) {
            Log.d("NetworkUtil", "No active network found")
            return false
        }

        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        Log.d("NetworkUtil", "Network capabilities: $capabilities")

        if (capabilities == null) {
            Log.d("NetworkUtil", "No network capabilities found")
            return false
        }

        val hasWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val hasCellular = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

        Log.d("NetworkUtil", "Has WiFi: $hasWifi, Has Cellular: $hasCellular")

        return hasWifi || hasCellular
    }
}

class NoConnectivityException : IOException("인터넷 연결이 없습니다. 네트워크 상태를 확인해주세요.")
class NetworkException(message: String) : IOException(message)