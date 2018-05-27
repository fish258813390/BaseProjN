package com.neil.fish;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.neil.fish.base.app.BaseActivity;
import com.neil.fish.base.app.YiyuanApiResult;
import com.neil.fish.db.AssetsDatabaseManager;
import com.neil.fish.db.SQLdm;
import com.neil.fish.entity.BankcardBean;
import com.neil.fish.entity.ResBodyBean;
import com.neil.fish.http.HttpClient;
import com.neil.fish.service.yiyuan.HotNewService;
import com.neil.fish.ui.home.model.HomeModel;
import com.neil.fish.ui.home.presenter.HomePresenter;
import com.neil.fish.ui.home.view.HomeView;
import com.neil.fish.ui.sample.ClockActivity;
import com.neil.fish.ui.sample.WebviewActivity;
import com.neil.fish.utils.LogUtils;
import com.neil.fish.utils.ToastUtils;
import com.neil.fish.widget.dialog.LoadingDialog;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

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

    @OnClick({R.id.btn_test, R.id.btn_test1, R.id.btn_test2, R.id.btn_test3, R.id.btn_clock, R.id.btn_webview, R.id.btn_error})
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

            case R.id.btn_error:
                testRxjavaError();
                break;

        }
    }


    private void testRxjavaError() {

        Observable<String> observable1 = Observable.just("zhangsan").onErrorReturn(new Func1<Throwable, String>() {
            @Override
            public String call(Throwable throwable) {
                return "zhangsan is wrong";
            }
        });

        HotNewService hotNewService = HttpClient.getService(HotNewService.class);
        Observable<YiyuanApiResult<ResBodyBean>> observable = hotNewService
                .getHotSearchRank(10, "47526", "c05733048bb9427f8ae9b8ede645ff23")
                .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        // 关联被观察者
//        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<YiyuanApiResult<ResBodyBean>>() {
//                    @Override
//                    public void onCompleted() {
//                        LoadingDialog.cancelDialogForLoading();
//                        LogUtils.e("请求完成了----end");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LoadingDialog.cancelDialogForLoading();
//                        LogUtils.e("异常了---->" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(YiyuanApiResult<ResBodyBean> resBodyBeanYiyuanApiResult) {
//                        LogUtils.d("当前线程:" + Thread.currentThread().getName());
//                        LogUtils.e("相应数据：---->" + resBodyBeanYiyuanApiResult.toString());
//                    }
//
//                    @Override
//                    public void onStart() {
//                        LogUtils.e("请求开始----start");
//                    }
//                });


        Observable<Integer> observable2 = Observable.just(10000).onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                return 10000;
            }
        });

        final String[] str = new String[1];
        Observable.zip(observable, observable2, new Func2<YiyuanApiResult<ResBodyBean>, Integer, Object>() {
            @Override
            public Object call(YiyuanApiResult<ResBodyBean> resBodyBeanYiyuanApiResult, Integer integer) {
                if (resBodyBeanYiyuanApiResult != null) {
                    return resBodyBeanYiyuanApiResult.hashCode() + "-----" + integer;
                } else {
                    return integer;
                }
            }
        }).subscribe(new Subscriber<Object>() {

            @Override
            public void onCompleted() {
                LogUtils.d("当前线程:" + Thread.currentThread().getName());
                LogUtils.d("zip接收值onCompleted" + str);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("zip接收值onError：" + e.getMessage());
                str[0] = "error is happend";
                onCompleted();
            }

            @Override
            public void onNext(Object result) {
                str[0] = result + "";
                LogUtils.d("zip接收值:" + result);
            }
        });

//        Observable.merge(observable, observable2)
//                .subscribe(new Subscriber<Object>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtils.d("merge接收值onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtils.d("merge接收值onError：" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Object result) {
////                        if (result instanceof Integer) {
////                            throw new NullPointerException("observable1");
//////                            LogUtils.d("merge接收值Integer：" + result);
////                        } else if (result instanceof String) {
////                            throw new NullPointerException("observable2");
//////                            LogUtils.d("merge接收值String：" + result);
////                        }
//                        LogUtils.d("merge接收值:" + result);
//
//                    }
//                });

//        Observable.zip(observable1, observable2, new Func2<String, Integer, String>() {
//            @Override
//            public String call(String s, Integer integer) {
//
//                LogUtils.d("接收值" + s + ",,,," + integer);
//                String builder = s + "+" + integer + "";
//                if (1 != 2) {
//                    throw new NullPointerException("11111");
//                }
//                return builder;
//            }
//        }).onErrorReturn(new Func1<Throwable, String>() {
//            @Override
//            public String call(Throwable throwable) {
//                LogUtils.d("zip异常：" + "-------");
//                return null;
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                LogUtils.d("onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtils.d("onError");
//            }
//
//            @Override
//            public void onNext(String s) {
//                LogUtils.d("合并接收值" + s);
//            }
//        });


//        Observable.create(new Observable.OnSubscribe<Integer>(){
//
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                subscriber.onNext(1);
//            }
//        }).onErrorReturn(new Func1<Throwable, Integer>() {
//            @Override
//            public Integer call(Throwable throwable) {
//                return null;
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//
//            }
//        });


    }

    @Override
    public void showData(String str) {

    }
}
