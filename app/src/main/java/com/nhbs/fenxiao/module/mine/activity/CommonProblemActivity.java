package com.nhbs.fenxiao.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nhbs.fenxiao.R;
import com.nhbs.fenxiao.base.BaseBarActivity;

public class CommonProblemActivity extends BaseBarActivity {


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_problem);
    }

    @Override
    protected void loadData() {
        setTitle("常见问题");
    }
}
