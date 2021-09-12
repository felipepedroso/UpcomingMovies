package br.pedroso.upcomingmovies.moviedetails.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.MoviesApplication;
import br.pedroso.upcomingmovies.R;
import br.pedroso.upcomingmovies.di.ApplicationComponent;
import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.moviedetails.MovieDetailsContract;
import br.pedroso.upcomingmovies.moviedetails.di.DaggerMovieDetailsComponent;
import br.pedroso.upcomingmovies.moviedetails.di.MovieDetailsPresenterModule;
import br.pedroso.upcomingmovies.moviedetails.presenter.MovieDetailsPresenter;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {
    public static final String EXTRA_MOVIE_ID = "movie_id";

    @Inject
    MovieDetailsPresenter presenter;

    private ImageView imageViewMoviePoster;
    private TextView textViewMovieReleaseDate;
    private TextView textViewMovieVoteAverage;
    private CollapsingToolbarLayout collapsingToolbarMovieDetails;
    private TextView textViewMovieOverview;
    private TextView textViewMovieTitle;
    private RecyclerView recyclerViewSimilarMovies;
    private SimilarMoviesAdapter similarMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        setupView();

        injectPresenter();
    }

    private void setupView() {
        setContentView(R.layout.activity_movie_details);

        bindViews();

        setupToolbar();

        setupSimilarMoviesRecyclerView();
    }

    private void setupSimilarMoviesRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSimilarMovies.setLayoutManager(layoutManager);
        similarMoviesAdapter = new SimilarMoviesAdapter(this);
        recyclerViewSimilarMovies.setAdapter(similarMoviesAdapter);
    }

    private void bindViews() {
        imageViewMoviePoster = (ImageView) findViewById(R.id.imageViewMoviePoster);
        textViewMovieTitle = (TextView) findViewById(R.id.textViewMovieTitle);
        textViewMovieReleaseDate = (TextView) findViewById(R.id.textViewMovieReleaseDate);
        textViewMovieVoteAverage = (TextView) findViewById(R.id.textViewMovieVoteAverage);
        textViewMovieOverview = (TextView) findViewById(R.id.textViewMovieOverview);
        collapsingToolbarMovieDetails = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarMovieDetails);
        recyclerViewSimilarMovies = (RecyclerView) findViewById(R.id.recyclerViewSimilarMovies);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_movie_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void injectPresenter() {
        ApplicationComponent applicationComponent = ((MoviesApplication) getApplication()).getApplicationComponent();

        DaggerMovieDetailsComponent.builder()
                .applicationComponent(applicationComponent)
                .movieDetailsPresenterModule(new MovieDetailsPresenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderMovieDetails(Movie movie) {
        String title = movie.getTitle();
        textViewMovieTitle.setText(title);
        collapsingToolbarMovieDetails.setTitle(title);

        Resources resources = getResources();

        textViewMovieReleaseDate.setText(movie.getReleaseDate());

        String votingAverageText = String.format(resources.getString(R.string.movie_rating_format), movie.getVoteAverage());
        textViewMovieVoteAverage.setText(votingAverageText);

        textViewMovieOverview.setText(movie.getOverview());

        Picasso.with(this).load(movie.getPosterPath()).into(imageViewMoviePoster, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) imageViewMoviePoster.getDrawable()).getBitmap();
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

        collapsingToolbarMovieDetails.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarMovieDetails.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(palette.getDarkMutedColor(primaryDark));
        }
    }

    @Override
    public void hideSimilarMoviesPanel() {
//        cardViewSimilarMovies.setVisibility(View.GONE);
    }

    @Override
    public void displaySimilarMoviesPanel() {
//        cardViewSimilarMovies.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderSimilarMovies(List<Movie> movies) {
        similarMoviesAdapter.updateAdapterData(movies);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Integer movieToBePresentedId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);

        presenter.loadMovieDetails(movieToBePresentedId);
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.pause();
    }
}
