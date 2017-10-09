package com.lixiang.titledemo.widgets;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lixiang.titledemo.R;

/**
 * Created by lixiang on 2017/10/9.
 */

public class ImmersionTitleView extends LinearLayout {
    //默认的顶部导航栏高度
    public static final int NAVIGATIONHEIGHT = 48;
    //默认中间的字体大小
    public static final int TEXTSISE = 15;
    private ImmersionTitleView.OnTitlerClickListener mListener;
    private TextView mTitleName;
    private ImageView mRightIV;
    private ImageView mLeftIV;
    private View mBottomView;
    private FrameLayout mRightFl;
    //状态栏高度
    private int height = 0;

    public ImmersionTitleView(Context context) {
        this(context, null, 0);
    }


    public ImmersionTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImmersionTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
        initAttrs(attrs);
    }

    @TargetApi(21)
    public ImmersionTitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData();
        initAttrs(attrs);
    }

    public void initData() {
        setOrientation(VERTICAL);
        if (getBackground() == null)
            setBackgroundResource(R.color.fff);
        //最左侧按钮默认是返回
        mLeftIV = new ImageView(getContext());
        mLeftIV.setImageResource(R.mipmap.back);
        mLeftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onBackClick(ImmersionTitleView.this, mLeftIV);
                else if (getContext() instanceof Activity) ((Activity) getContext()).finish();
            }
        });
        int px18 = (int) (getResources().getDisplayMetrics().density * 18 + 0.5);
        mLeftIV.setPadding(px18, 0, px18, 0);
        //标题
        mTitleName = new TextView(getContext());
        mTitleName.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXTSISE);
        mTitleName.setTextColor(ContextCompat.getColor(getContext(), R.color.c323232));
        mTitleName.setSingleLine();
        mTitleName.setGravity(Gravity.CENTER);
        mTitleName.setEllipsize(TextUtils.TruncateAt.END);
        mTitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onTitleClick(ImmersionTitleView.this, mTitleName.getText().toString());
            }
        });
        //右边的布局
        mRightFl = new FrameLayout(getContext());
        //右边图片
        mRightIV = new ImageView(getContext());
        mRightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onRightClick(ImmersionTitleView.this, mRightIV);
            }
        });
        mRightIV.setPadding(px18, 0, px18, 0);
        mRightFl.addView(mRightIV, ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //横着的线性布局
        RelativeLayout ll = new RelativeLayout(getContext());
        ll.addView(mTitleName, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll.addView(mLeftIV, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        //居于右侧
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ll.addView(mRightFl, params);
        //横线
        mBottomView = new View(getContext());
        mBottomView.setBackgroundResource(R.color.d7d7d7);
        //状态栏高度
        View mstate = new View(getContext());
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        //将线性布局和横线添加进来
        addView(mstate, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        addView(ll, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dip2px(getContext(), NAVIGATIONHEIGHT)));
        addView(mBottomView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyTitleView);
            mTitleName.setText(typedArray.getString(R.styleable.MyTitleView_titleName));
            setIsVisibleLine(typedArray.getBoolean(R.styleable.MyTitleView_isVisibleLine, true));
            Drawable rightDrawable = typedArray.getDrawable(R.styleable.MyTitleView_rightRes);
            if (rightDrawable != null) mRightIV.setImageDrawable(rightDrawable);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //绘制完之后设置高度，不然的话如果背景是图片的话会出现过高  设置的高度为状态栏高度加上导航栏高度默认为48dp
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height + dip2px(getContext(), NAVIGATIONHEIGHT)));

    }

    /**
     * 自定义监听
     */
    public void setOnTitlerClickListener(ImmersionTitleView.OnTitlerClickListener listener) {
        mListener = listener;
    }


    //下边的线是否可见
    public void setIsVisibleLine(boolean isVisible) {
        mBottomView.setVisibility(isVisible ? VISIBLE : INVISIBLE);
    }

    /**
     * @param titleText 标题
     */
    public void setTitle(String titleText) {
        mTitleName.setText(titleText);
    }

    public void setTitleColor(@ColorInt int color) {
        mTitleName.setTextColor(color);
    }

    public void setTitlesize(int size) {
        mTitleName.setTextSize(size);
    }

    //获得右边的iv对象,注意和{@link #setFLRightChild}冲突
    public ImageView getIVRight() {
        return mRightIV;
    }

    //获得返回的iv对象(注意返回键有padding属性)
    public ImageView getIVBack() {
        return mLeftIV;
    }

    /**
     * 自定义右侧的view可以根据不同需求自行设置
     *
     * @param view
     */
    public void setFLRightChild(View view) {
        mRightFl.removeAllViews();
        mRightFl.addView(view);
    }

    public abstract class OnTitlerClickListener {
        protected void onBackClick(ImmersionTitleView TitlerView, ImageView IVBack) {
            if (TitlerView.getContext() instanceof Activity)
                ((Activity) TitlerView.getContext()).finish();
        }

        protected void onTitleClick(ImmersionTitleView TitlerView, String text) {
        }

        protected abstract void onRightClick(ImmersionTitleView TitlerView, ImageView IVRight);
    }

}
