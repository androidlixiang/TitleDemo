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

public class FirstFragment extends Fragment {

    private ImmersionTitleView mImmersionTitleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);
        mImmersionTitleView = view.findViewById(R.id.mImmersionTitleView);
        mImmersionTitleView.setFLRightChild(LayoutInflater.from(getContext()).inflate(R.layout.rit, null));
        return view;
    }
}
