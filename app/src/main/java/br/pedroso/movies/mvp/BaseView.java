package br.pedroso.movies.mvp;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
