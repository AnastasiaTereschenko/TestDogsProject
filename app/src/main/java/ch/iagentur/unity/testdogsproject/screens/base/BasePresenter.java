package ch.iagentur.unity.testdogsproject.screens.base;

public interface BasePresenter<T> {

    void setView(T view);

    void unSubscribe();
}
