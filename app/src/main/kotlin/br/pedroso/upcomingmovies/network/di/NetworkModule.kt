package br.pedroso.upcomingmovies.network.di

import android.app.Application
import br.pedroso.upcomingmovies.BuildConfig
import br.pedroso.upcomingmovies.network.interceptors.ApiKeyInterceptor
import br.pedroso.upcomingmovies.network.services.TheMovieDbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cache = Cache(application.cacheDir, CACHE_SIZE)
        return cache
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor(BuildConfig.MOVIES_DB_API_KEY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache?, apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.cache(cache)

        builder.addInterceptor(apiKeyInterceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideJsonConverterFactory(json: Json): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideRetrofit(converterFactory: Converter.Factory, httpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(converterFactory)
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
