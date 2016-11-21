package br.pedroso.movies.movies.di;

import br.pedroso.movies.di.components.RepositoryComponent;
import br.pedroso.movies.di.scopes.FragmentScope;
import br.pedroso.movies.movies.ui.MoviesActivity;

import dagger.Component;

@FragmentScope
@Component(dependencies = RepositoryComponent.class, modules = MoviesPresenterModule.class)
public interface MoviesComponent {
    void inject(MoviesActivity activity);
}
