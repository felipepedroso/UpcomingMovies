package br.pedroso.movies.movieDetails.di;

import br.pedroso.movies.di.components.RepositoryComponent;
import br.pedroso.movies.di.scopes.FragmentScope;
import br.pedroso.movies.movieDetails.ui.MovieDetailsActivity;

import dagger.Component;

@FragmentScope
@Component(dependencies = RepositoryComponent.class, modules = MovieDetailsPresenterModule.class)
public interface MovieDetailsComponent {
    void inject(MovieDetailsActivity activity);
}
