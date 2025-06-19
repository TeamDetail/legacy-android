package com.legacy.legacy_android.feature.network.remote

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.annotation.RequiresPermission
import com.legacy.legacy_android.MyApplication
import com.test.beep_and.feature.data.user.getUser.getAccToken
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RequestInterceptor(
    private val networkUtil: NetworkUtil
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!networkUtil.isNetworkConnected()) {
                throw NoConnectivityException()
            }
            val request = chain.request()
            val context = MyApplication.getContext()
            val skipPaths = listOf(
                "/dauth/login",
                "/auth/login",
                "/auth/refresh"
            )
            val path = request.url.encodedPath

            val shouldSkipHeader = skipPaths.any { path.startsWith(it) }
            val newRequest = if (shouldSkipHeader) {
                request.newBuilder().build()
            } else {
                request.newBuilder()
                    .addHeader("Authorization", "Bearer ${getAccToken(context)}")
                    .build()
            }
            println("Request URL: ${newRequest.url}")
            println("Headers: ${newRequest.headers}")

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

class NetworkUtil(private val context: Context) {
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
    }}

class NoConnectivityException : IOException("인터넷 연결이 없습니다. 네트워크 상태를 확인해주세요.")
class NetworkException(message: String) : IOException(message)


