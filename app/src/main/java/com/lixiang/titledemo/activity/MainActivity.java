package com.lixiang.titledemo.activity;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lixiang.titledemo.R;
import com.lixiang.titledemo.adapter.HomeViewpagerAdapter;
import com.lixiang.titledemo.fragemnt.FirstFragment;
import com.lixiang.titledemo.fragemnt.SecondFragment;
import com.lixiang.titledemo.fragemnt.ThirdlyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    ViewPager mViewPager;
    RadioButton HomeRadioButton;
    RadioButton ShoppingRadioButton;
    RadioButton MyRadioButton;
    RadioGroup mRadioGroup;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdlyFragment thirdlyFragment;
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen(this);
        setContentView(R.layout.activity_main);
        init();
        setListener();
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
    private void setListener() {


        mViewPager.setAdapter(new HomeViewpagerAdapter(getSupportFragmentManager(), fragments));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        HomeRadioButton.setChecked(true);
                        break;
                    case 1:
                        ShoppingRadioButton.setChecked(true);
                        break;
                    case 2:
                        MyRadioButton.setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.HomeRadioButton:
                        mViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.ShoppingRadioButton:
                        mViewPager.setCurrentItem(1, true);
                        break;
                    case R.id.MyRadioButton:
                        mViewPager.setCurrentItem(2, true);
                        break;

                }

            }
        });
    }

    private void init() {

        mViewPager = findViewById(R.id.mViewPager);
        HomeRadioButton = findViewById(R.id.HomeRadioButton);
        ShoppingRadioButton = findViewById(R.id.ShoppingRadioButton);
        MyRadioButton = findViewById(R.id.MyRadioButton);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        HomeRadioButton.setChecked(true);
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdlyFragment = new ThirdlyFragment();
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdlyFragment);
    }
}
