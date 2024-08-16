package br.pedroso.upcomingmovies.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAMETER, apiKey)
            .build()

        val modifiedRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        val response = chain.proceed(modifiedRequest)

        return response
    }

    private companion object {
        const val API_KEY_QUERY_PARAMETER = "api_key"
    }
}
