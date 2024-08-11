package br.pedroso.upcomingmovies.network.di

import android.app.Application
import br.pedroso.upcomingmovies.network.interceptors.ApiKeyInterceptor
import br.pedroso.upcomingmovies.network.services.TheMovieDbService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val apiKey: String) {
    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cache = Cache(application.cacheDir, CACHE_SIZE)
        return cache
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor(apiKey)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache?, apiKeyInterceptor: ApiKeyInterceptor?): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.cache(cache)

        builder.interceptors().add(apiKeyInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(TheMovieDbService.BASE_URL)
            .client(httpClient)
            .build()

        return retrofit
    }

    @Provides
    @Singleton
    fun provideTheMovieDbService(retrofit: Retrofit): TheMovieDbService {
        return retrofit.create(TheMovieDbService::class.java)
    }

    companion object {
        private const val CACHE_SIZE = (10 * 1024 * 1024).toLong()
    }
}
