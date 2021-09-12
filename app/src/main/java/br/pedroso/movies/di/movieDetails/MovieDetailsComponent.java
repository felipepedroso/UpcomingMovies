package br.pedroso.movies.di.movieDetails;

import br.pedroso.movies.di.application.ApplicationComponent;
import br.pedroso.movies.di.scopes.FragmentScope;
import br.pedroso.movies.movieDetails.ui.MovieDetailsActivity;
import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = MovieDetailsPresenterModule.class)
public interface MovieDetailsComponent {
    void inject(MovieDetailsActivity activity);
}
