package com.lixiang.titledemo.fragemnt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixiang.titledemo.R;
import com.lixiang.titledemo.widgets.ImmersionTitleView;

/**
 * Created by lixiang on 2017/10/9.
 */

public class ThirdlyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thirdly, null);
        ImmersionTitleView mImmersionTitleView = view.findViewById(R.id.mImmersionTitleView);
        mImmersionTitleView.setTitleColor(getResources().getColor(R.color.fff));
        return view;
    }
}
