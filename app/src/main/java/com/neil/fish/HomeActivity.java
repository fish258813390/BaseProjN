package com.neil.fish;

import android.view.View;

import com.neil.fish.base.app.BaseActivity;
import com.neil.fish.ui.home.model.HomeModel;
import com.neil.fish.ui.home.presenter.HomePresenter;
import com.neil.fish.ui.home.view.HomeView;

import butterknife.OnClick;

/**
 * 主页面
 */
public class HomeActivity extends BaseActivity<HomePresenter,HomeModel> implements HomeView {


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

    @OnClick({R.id.btn_test,R.id.btn_test1})
    public void onViewCreated(View view){
        switch (view.getId()){
            case R.id.btn_test:
                mPresenter.getHotList(10, "47526", "c05733048bb9427f8ae9b8ede645ff23");
                break;

            case R.id.btn_test1:
                break;
        }
    }

    @Override
    public void showData(String str) {

    }
}
