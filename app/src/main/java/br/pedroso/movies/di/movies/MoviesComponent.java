package br.pedroso.movies.di.movies;

import br.pedroso.movies.di.application.ApplicationComponent;
import br.pedroso.movies.di.scopes.FragmentScope;
import br.pedroso.movies.movies.ui.MoviesActivity;

import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = MoviesPresenterModule.class)
public interface MoviesComponent {
    void inject(MoviesActivity activity);
}
