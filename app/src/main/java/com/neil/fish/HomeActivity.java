package com.neil.fish;

import android.view.View;

import com.neil.fish.base.app.BaseActivity;
import com.neil.fish.base.app.BaseApplication;
import com.neil.fish.ui.home.model.HomeModel;
import com.neil.fish.ui.home.presenter.HomePresenter;
import com.neil.fish.ui.home.view.HomeView;
import com.neil.fish.widget.dialog.LoadingDialog;

import butterknife.OnClick;

/**
 * 主页面
 */
public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeView {


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btn_test, R.id.btn_test1, R.id.btn_test2, R.id.btn_test3})
    public void onViewCreated(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
                LoadingDialog.showDialogForLoading(this); // 全局对话框
                mPresenter.getHotList(10, "47526", "c05733048bb9427f8ae9b8ede645ff23");
                break;

            case R.id.btn_test1:
                LoadingDialog.showDialogForLoading(this);
                mPresenter.getHotListDefault(10, "47526", "c05733048bb9427f8ae9b8ede645ff23");
                break;

            case R.id.btn_test2:
                LoadingDialog.showDialogForLoading(this);
                mPresenter.getHotListByRxAndRetrofit(10, "47526", "c05733048bb9427f8ae9b8ede645ff23");
                break;

            case R.id.btn_test3:
                LoadingDialog.showDialogForLoading(this);
                mPresenter.getHotListNew(10, "47526", "c05733048bb9427f8ae9b8ede645ff23");
                break;
        }
    }

    @Override
    public void showData(String str) {

    }
}
