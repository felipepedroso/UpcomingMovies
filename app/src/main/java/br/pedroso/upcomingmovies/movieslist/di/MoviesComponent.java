package br.pedroso.upcomingmovies.movieslist.di;

import br.pedroso.upcomingmovies.di.ApplicationComponent;
import br.pedroso.upcomingmovies.di.scopes.FragmentScope;
import br.pedroso.upcomingmovies.movieslist.ui.MoviesActivity;
import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = MoviesPresenterModule.class)
public interface MoviesComponent {
    void inject(MoviesActivity activity);
}
