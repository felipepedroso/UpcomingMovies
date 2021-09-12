package br.pedroso.upcomingmovies.moviedetails;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import br.pedroso.upcomingmovies.MoviesApplication;
import br.pedroso.upcomingmovies.R;
import br.pedroso.upcomingmovies.databinding.ActivityMovieDetailsBinding;
import br.pedroso.upcomingmovies.di.ApplicationComponent;
import br.pedroso.upcomingmovies.domain.Movie;
import br.pedroso.upcomingmovies.moviedetails.adapter.SimilarMoviesAdapter;
import br.pedroso.upcomingmovies.moviedetails.di.DaggerMovieDetailsComponent;
import br.pedroso.upcomingmovies.moviedetails.di.MovieDetailsPresenterModule;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsView {
    public static final String EXTRA_MOVIE_ID = "movie_id";

    @Inject
    MovieDetailsPresenter presenter;

    private SimilarMoviesAdapter similarMoviesAdapter;

    private ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        injectPresenter();

        setupView();
    }

    private void setupView() {
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setupToolbar();

        setupSimilarMoviesRecyclerView();
    }

    private void setupSimilarMoviesRecyclerView() {
        similarMoviesAdapter = new SimilarMoviesAdapter(presenter::clickedOnSimilarMovie);
        binding.movieDetailsContent.recyclerViewSimilarMovies.setAdapter(similarMoviesAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbarMovieDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.toolbarMovieDetails.setNavigationOnClickListener(v -> onBackPressed());
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
        binding.movieDetailsContent.textViewMovieTitle.setText(title);
        binding.collapsingToolbarMovieDetails.setTitle(title);

        Resources resources = getResources();

        binding.movieDetailsContent.textViewMovieReleaseDate.setText(movie.getReleaseDate());

        String votingAverageText = String.format(resources.getString(R.string.movie_rating_format), movie.getVoteAverage());
        binding.movieDetailsContent.textViewMovieVoteAverage.setText(votingAverageText);

        binding.movieDetailsContent.textViewMovieOverview.setText(movie.getOverview());

        Picasso.with(this).load(movie.getPosterPath()).into(binding.imageViewMoviePoster, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) binding.imageViewMoviePoster.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(MovieDetailsActivity.this::applyPalette);
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

        binding.collapsingToolbarMovieDetails.setContentScrimColor(palette.getMutedColor(primary));
        binding.collapsingToolbarMovieDetails.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));

        getWindow().setStatusBarColor(palette.getDarkMutedColor(primaryDark));
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
    public void startMovieDetailsActivity(Integer movieId) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
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
