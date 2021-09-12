package br.pedroso.upcomingmovies.shared.data.retrofit.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.pedroso.upcomingmovies.shared.data.retrofit.entities.RetrofitMovieEntity;

public class UpcomingMoviesResultDeserializer implements JsonDeserializer<List<RetrofitMovieEntity>> {

    public static final String RESULTS_FIELD = "results";

    @Override
    public List<RetrofitMovieEntity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement results = json.getAsJsonObject().get(RESULTS_FIELD);

        return new Gson().fromJson(results, UpcomingMoviesResultDeserializer.getRetrofitMovieEntitiesListType());
    }

    public static Type getRetrofitMovieEntitiesListType() {
        return new TypeToken<List<RetrofitMovieEntity>>() {
        }.getType();
    }
}
