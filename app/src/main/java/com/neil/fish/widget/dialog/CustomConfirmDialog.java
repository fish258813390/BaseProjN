package com.neil.fish.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.neil.fish.R;

/**
 * 利用builder模式构建dialog
 * Created by yujin on 2018/10/29
 */
public class CustomConfirmDialog extends Dialog implements View.OnClickListener {

    private OnClickListener mOnClickListener;
    private OnCancelClickListener mOnCancelListener;

    private View mContentView;
    private Button mConfirmButton;
    private Button mCancelButton;
    private TextView mDialogText;


    public CustomConfirmDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CustomConfirmDialog(@NonNull Context context, int layoutId) {
        super(context, R.style.CustomConfirmDialog);
        init(layoutId);
    }

    public void init(int layoutId) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (layoutId > 0) {
            mContentView = LayoutInflater.from(this.getContext()).inflate(layoutId, null);
        }
        setContentView(mContentView);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mConfirmButton) {
            if (mOnClickListener != null) {
                mOnClickListener.onConfirm();
            }
            dismiss();
        } else if (v == mCancelButton) {
            if (mOnCancelListener != null) {
                mOnCancelListener.onCancel();
            }
            dismiss();
        }
    }

    private void apply(Builder builder) {
        if (builder != null) {
            ViewGroup.LayoutParams params = mContentView.getLayoutParams();
            if (builder.width > 0 && builder.height > 0) {
                params.width = builder.width;
                params.height = builder.height;
            }
            mContentView.setLayoutParams(params);
            mDialogText.setText(builder.msg);
            mConfirmButton.setText(builder.confirm);
            mCancelButton.setText(builder.cancel);

            if (builder.callback != null) {
                setOnClickListener(builder.callback);
            }
            if (builder.cancelClickListener != null) {
                setOnCancelListener(builder.cancelClickListener);
            }

        }
    }

    public interface OnClickListener {
        void onConfirm();
    }

    public interface OnCancelClickListener {
        void onCancel();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnCancelListener(OnCancelClickListener onCancelListener) {
        this.mOnCancelListener = onCancelListener;
    }

    public static class Builder {
        Context context;
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        String msg = "";
        String confirm = "确定";
        String cancel = "取消";

        OnClickListener callback = null;
        OnCancelClickListener cancelClickListener = null;

        int customLayoutId = 0;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder confirm(String confirm) {
            this.confirm = confirm;
            return this;
        }

        public Builder cancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setOnConfirmLisenter(OnClickListener callback) {
            this.callback = callback;
            return this;
        }

        public Builder setOnCancelLisenter(OnCancelClickListener cancelClickListener) {
            this.cancelClickListener = cancelClickListener;
            return this;
        }

        public Builder setLayout(int layoutId) {
            customLayoutId = layoutId;
            return this;
        }

        public CustomConfirmDialog build() {
            CustomConfirmDialog dialog = new CustomConfirmDialog(context, customLayoutId);
            dialog.apply(this);
            return dialog;
        }


    }


}
