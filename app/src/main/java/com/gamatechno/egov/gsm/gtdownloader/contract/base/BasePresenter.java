package com.gamatechno.egov.gsm.gtdownloader.contract.base;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
