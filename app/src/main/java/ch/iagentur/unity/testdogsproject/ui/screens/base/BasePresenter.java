package ch.iagentur.unity.testdogsproject.ui.screens.base;

public interface BasePresenter<T> {

    void setView(T view);

    void unSubscribe();
}
