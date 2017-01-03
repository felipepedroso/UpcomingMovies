package br.pedroso.movies.shared.mvp;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
