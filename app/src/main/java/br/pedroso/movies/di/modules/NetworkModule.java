package br.pedroso.movies.di.modules;

import android.app.Application;

import br.pedroso.movies.data.retrofit.deserializer.UpcomingMoviesResultDeserializer;
import br.pedroso.movies.data.retrofit.interceptors.ApiKeyInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private final String baseUrl;
    private final String apiKey;

    private final long CACHE_SIZE = 10 * 1024 * 1024;

    public NetworkModule(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        Cache cache = new Cache(application.getCacheDir(), CACHE_SIZE);
        return cache;
    }

    @Provides
    @Singleton
    ApiKeyInterceptor provideApiKeyInterceptor() {
        return new ApiKeyInterceptor(apiKey);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, ApiKeyInterceptor apiKeyInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.cache(cache);

        builder.interceptors().add(apiKeyInterceptor);

        return builder.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(UpcomingMoviesResultDeserializer.getRetrofitMovieEntitiesListType(), new UpcomingMoviesResultDeserializer());

        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(httpClient)
                .build();

        return retrofit;
    }
}
