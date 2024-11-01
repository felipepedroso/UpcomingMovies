package br.pedroso.upcomingmovies.network.di

import android.app.Application
import br.pedroso.upcomingmovies.BuildConfig
import br.pedroso.upcomingmovies.network.interceptors.ApiKeyInterceptor
import br.pedroso.upcomingmovies.network.services.TheMovieDbService
import br.pedroso.upcomingmovies.network.services.TheMovieDbServiceKtor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.Cache
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
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        cache: Cache?,
        apiKeyInterceptor: ApiKeyInterceptor,
        json: Json,
    ): HttpClient {
        return HttpClient(OkHttp) {
            expectSuccess = true

            engine {
                addInterceptor(apiKeyInterceptor)
                config {
                    cache(cache)
                }
            }

            defaultRequest {
                url(TheMovieDbService.BASE_URL)
            }

            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Provides
    @Singleton
    fun provideTheMovieDbService(httpClient: HttpClient): TheMovieDbService {
        return TheMovieDbServiceKtor(httpClient)
    }

    companion object {
        private const val CACHE_SIZE = (10 * 1024 * 1024).toLong()
    }
}
