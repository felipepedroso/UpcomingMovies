package br.pedroso.upcomingmovies.di.movieDetails;

import br.pedroso.upcomingmovies.di.application.ApplicationComponent;
import br.pedroso.upcomingmovies.di.scopes.FragmentScope;
import br.pedroso.upcomingmovies.moviedetails.ui.MovieDetailsActivity;
import dagger.Component;

@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = MovieDetailsPresenterModule.class)
public interface MovieDetailsComponent {
    void inject(MovieDetailsActivity activity);
}
