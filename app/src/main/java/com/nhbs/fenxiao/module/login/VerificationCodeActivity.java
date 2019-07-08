package com.nhbs.fenxiao.module.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;
import com.nhbs.fenxiao.module.login.presenter.VerificationCodePresenter;
import com.nhbs.fenxiao.module.login.presenter.VerificationCodeViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.windown.SoftInputMethodUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yudneghao
 */
public class VerificationCodeActivity extends BaseBarActivity
        implements VerificationCodeViewer, TextWatcher, View.OnKeyListener {

    public final static int INPUT_VER_CODE_REQUEST = 0x001;
    private final static String PHONE_NUMBER = "phone_number";

    @PresenterLifeCycle
    private VerificationCodePresenter mPresenter =
            new VerificationCodePresenter(this);

    private List<String> codes = new ArrayList<>();
    private TextView mNumber1;
    private TextView mNumber2;
    private TextView mNumber3;
    private TextView mNumber4;
    private EditText verCodeEdit;


    public static Intent getIntent(Context context, String phoneNumber) {
        Intent intent = new Intent(context, VerificationCodeActivity.class);
        intent.putExtra(PHONE_NUMBER, phoneNumber);
        return intent;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_verification_code_view);
    }

    @Override
    protected boolean softInputClickOutsideAutoDismiss() {
        return false;
    }

    @Override
    protected void loadData() {
        SoftInputMethodUtil.showKeyboard(this, true);
        initView();
        initListener();
    }

    private void initView() {
        setBackImgRes(R.drawable.ic_cancel);
        mNumber1 = bindView(R.id.number1);
        mNumber2 = bindView(R.id.number2);
        mNumber3 = bindView(R.id.number3);
        mNumber4 = bindView(R.id.number4);
        verCodeEdit = bindView(R.id.input_ver_code);
    }

    private void initListener() {
        verCodeEdit.addTextChangedListener(this);
        verCodeEdit.setOnKeyListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null && editable.length() > 0) {
            verCodeEdit.setText("");
            if (codes.size() < 4) {
                String result = editable.toString().trim();
                if (!TextUtils.isEmpty(result)) {
                    codes.add(result);
                }
                setCode();
            }
        }
    }

    private void setCode() {
        if (codes.size() == 0) {
            mNumber1.setText("");
            mNumber2.setText("");
            mNumber3.setText("");
            mNumber4.setText("");
        }
        if (codes.size() == 1) {
            mNumber1.setText(codes.get(0));
            mNumber2.setText("");
            mNumber3.setText("");
            mNumber4.setText("");
        }
        if (codes.size() == 2) {
            mNumber1.setText(codes.get(0));
            mNumber2.setText(codes.get(1));
            mNumber3.setText("");
            mNumber4.setText("");
        }
        if (codes.size() == 3) {
            mNumber1.setText(codes.get(0));
            mNumber2.setText(codes.get(1));
            mNumber3.setText(codes.get(2));
            mNumber4.setText("");
        }
        if (codes.size() == 4) {
            mNumber1.setText(codes.get(0));
            mNumber2.setText(codes.get(1));
            mNumber3.setText(codes.get(2));
            mNumber4.setText(codes.get(3));
            SoftInputMethodUtil.hideKeyboard(getActivity());
            mPresenter.sendVerCode(getIntent().getStringExtra(PHONE_NUMBER), codes.get(0) + codes.get(1) + codes.get(2) + codes.get(3));
        }
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (codes.size() > 0 && event.getAction() == KeyEvent.ACTION_UP) {
                codes.remove(codes.size() - 1);
            }
            if (KeyEvent.ACTION_UP == event.getAction()) {
                SoftInputMethodUtil.showKeyboard(getActivity(), true);
            }
        }
        return false;
    }

    @Override
    public void loginSuccess() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
