package br.pedroso.upcomingmovies.network.di;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import br.pedroso.upcomingmovies.network.deserializer.UpcomingMoviesResultDeserializer;
import br.pedroso.upcomingmovies.network.interceptors.ApiKeyInterceptor;
import br.pedroso.upcomingmovies.network.services.TheMovieDbService;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private final String apiKey;

    private final long CACHE_SIZE = 10 * 1024 * 1024;

    public NetworkModule(String apiKey) {
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(TheMovieDbService.BASE_URL)
                .client(httpClient)
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    TheMovieDbService provideTheMovieDbService(Retrofit retrofit) {
        return retrofit.create(TheMovieDbService.class);
    }
}
