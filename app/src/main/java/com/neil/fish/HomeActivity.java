package com.neil.fish;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.neil.fish.base.app.BaseActivity;
import com.neil.fish.db.AssetsDatabaseManager;
import com.neil.fish.db.SQLdm;
import com.neil.fish.entity.BankcardBean;
import com.neil.fish.ui.home.model.HomeModel;
import com.neil.fish.ui.home.presenter.HomePresenter;
import com.neil.fish.ui.home.view.HomeView;
import com.neil.fish.ui.sample.ClockActivity;
import com.neil.fish.ui.sample.WebviewActivity;
import com.neil.fish.utils.LogUtils;
import com.neil.fish.utils.ToastUtils;
import com.neil.fish.widget.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页面
 */
public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeView {

    @BindView(R.id.et_input_bankcard)
    EditText etBank;

    private CharSequence temp;

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
        initData();
    }

    private void initData() {
        AssetsDatabaseManager.initManager(this);
        etBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = temp.length();
                if (length >= 6) {
//                    AssetsDatabaseManager manager = AssetsDatabaseManager.getManager();
//                    SQLiteDatabase db = manager.getDatabase("card_bin.db");

                    //TODO 从数据库中获取银行卡信息
                    SQLdm sql = new SQLdm();
                    SQLiteDatabase db = sql.openDatabase(HomeActivity.this);
//                    SQLiteDatabase db = sql.openDatabaseNew(HomeActivity.this);
                    //查询数据库中testid=1的数据
//                    Cursor cursor = db.rawQuery("select * from card_bin where BIN=?", new String[]{"95599"});
                    Cursor cursor = db.rawQuery("select * from card_bin where BIN like ?", new String[]{"%" + etBank.getText().toString().trim() + "%"});
                    String name = null;
                    if (cursor.moveToNext()) {
                        String BIN = cursor.getString(cursor.getColumnIndex("BIN"));
                        String Bank_name = cursor.getString(cursor.getColumnIndex("Bank_name"));
                        String Bank_code = cursor.getString(cursor.getColumnIndex("Bank_code"));
                        String is_enable = cursor.getString(cursor.getColumnIndex("is_enable"));
                        BankcardBean bean = new BankcardBean();
                        bean.setBIN(BIN);
                        bean.setBank_name(Bank_name);
                        bean.setBank_code(Bank_code);
                        bean.setIs_enable(is_enable);
                        LogUtils.e("查询到的银行信息:" + bean.toString());
                        ToastUtils.showShort(bean.getBank_name());
                    }
                    //这是一个TextView，把得到的数据库中的name显示出来.
                    cursor.close();

                }
            }
        });
    }

    @OnClick({R.id.btn_test, R.id.btn_test1, R.id.btn_test2, R.id.btn_test3, R.id.btn_clock, R.id.btn_webview})
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

            case R.id.btn_clock:
                startActivity(new Intent(HomeActivity.this, ClockActivity.class));
                break;

            case R.id.btn_webview:
                startActivity(new Intent(HomeActivity.this, WebviewActivity.class));
                break;

        }
    }

    @Override
    public void showData(String str) {

    }
}
