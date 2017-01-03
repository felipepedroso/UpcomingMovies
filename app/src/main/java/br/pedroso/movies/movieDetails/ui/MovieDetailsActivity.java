package br.pedroso.movies.movieDetails.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciandt.moviespoc.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.movies.MoviesApplication;
import br.pedroso.movies.di.application.ApplicationComponent;
import br.pedroso.movies.di.movieDetails.DaggerMovieDetailsComponent;
import br.pedroso.movies.di.movieDetails.MovieDetailsPresenterModule;
import br.pedroso.movies.movieDetails.presenter.MovieDetailsPresenter;
import br.pedroso.movies.movieDetails.MovieDetailsContract;
import br.pedroso.movies.shared.domain.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {
    public static final String EXTRA_MOVIE_ID = "movie_id";

    @Inject
    MovieDetailsPresenter presenter;

    private ImageView imageView_moviePoster;
    private TextView textView_releaseDate;
    private TextView textView_voteAverage;
    private CollapsingToolbarLayout collapsingToolbar;
    private TextView textView_overview;
    private TextView textView_title;
    private RecyclerView recyclerView_similarMovies;
    private SimilarMoviesAdapter similarMoviesAdapter;
    private CardView cardView_similarMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        setContentView(R.layout.activity_movie_details);

        setupViews();

        injectPresenter();
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_movie_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView_moviePoster = (ImageView) findViewById(R.id.imageView_movie_poster);
        textView_title = (TextView) findViewById(R.id.textView_movie_title);
        textView_releaseDate = (TextView) findViewById(R.id.textView_movie_release_date);
        textView_voteAverage = (TextView) findViewById(R.id.textView_movie_vote_average);
        textView_overview = (TextView) findViewById(R.id.textView_movie_overview);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_movie_details);

        cardView_similarMovies = (CardView) findViewById(R.id.cardView_similar_movies);

        recyclerView_similarMovies = (RecyclerView) findViewById(R.id.recyclerView_movies);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_similarMovies.setLayoutManager(layoutManager);
        similarMoviesAdapter = new SimilarMoviesAdapter(this);
        recyclerView_similarMovies.setAdapter(similarMoviesAdapter);
    }

    private void injectPresenter() {
        ApplicationComponent applicationComponent = ((MoviesApplication) getApplication()).getApplicationComponent();

        Integer movieToBePresentedId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);

        DaggerMovieDetailsComponent.builder()
                .applicationComponent(applicationComponent)
                .movieDetailsPresenterModule(new MovieDetailsPresenterModule(movieToBePresentedId, this))
                .build()
                .inject(this);
    }

    @Override
    public void renderMovieDetails(Movie movie) {
        String title = movie.getTitle();
        textView_title.setText(title);
        collapsingToolbar.setTitle(title);

        Resources resources = getResources();

        textView_releaseDate.setText(movie.getReleaseDate());

        String votingAverageText = String.format(resources.getString(R.string.movie_rating_format), movie.getVoteAverage());
        textView_voteAverage.setText(votingAverageText);

        textView_overview.setText(movie.getOverview());

        Picasso.with(this).load(movie.getPosterPath()).into(imageView_moviePoster, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) imageView_moviePoster.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {
                // TODO: handle error properly.
            }
        });
    }

    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        int primary = ContextCompat.getColor(this, R.color.colorPrimary);

        collapsingToolbar.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbar.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(palette.getDarkMutedColor(primaryDark));
        }
    }

    @Override
    public void hideSimilarMoviesPanel() {
        cardView_similarMovies.setVisibility(View.GONE);
    }

    @Override
    public void displaySimilarMoviesPanel() {
        cardView_similarMovies.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderSimilarMovies(List<Movie> movies) {
        similarMoviesAdapter.updateAdapterData(movies);
    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        // We don't need to set the presenter, since it's being injected by Dagger
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
