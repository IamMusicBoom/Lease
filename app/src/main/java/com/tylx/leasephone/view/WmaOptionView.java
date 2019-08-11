package com.tylx.leasephone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylx.leasephone.R;

/**
 * Created by wangm on 2017/8/10.
 */

public class WmaOptionView extends LinearLayout {
    private Context mContext;
    private String keyText = "",valueText = "";
    private int keyTextColor,valueTextColor, rightImgId,lineColor,leftImgId;
    private float keyTextSize,valueTextSize;
    private int isLineVisable;
    private int lineLeftMargin,lineRightMargin;
    private int linearlayouPadding;

    public WmaOptionView(Context context) {
        this(context,null);
    }

    public WmaOptionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WmaOptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.getResources().obtainAttributes(attrs, R.styleable.WmaOptionView);
        keyText = array.getString(R.styleable.WmaOptionView_key_text);
        valueText = array.getString(R.styleable.WmaOptionView_value_text);
        keyTextSize = px2sp(context,array.getDimension(R.styleable.WmaOptionView_key_text_size,28));
        valueTextSize = px2sp(context,array.getDimension(R.styleable.WmaOptionView_value_text_size,28));
        keyTextColor = array.getColor(R.styleable.WmaOptionView_key_text_color,0xff000000);
        valueTextColor = array.getColor(R.styleable.WmaOptionView_value_text_color,0xffcccccc);
        rightImgId = array.getResourceId(R.styleable.WmaOptionView_right_img, 0);
        leftImgId = array.getResourceId(R.styleable.WmaOptionView_left_img,0);
        lineColor = array.getColor(R.styleable.WmaOptionView_line_color,0xffcccccc);
        isLineVisable = array.getInt(R.styleable.WmaOptionView_set_line_visable,0);
        lineLeftMargin = array.getDimensionPixelSize(R.styleable.WmaOptionView_line_left_margin,30);
        lineRightMargin = array.getDimensionPixelSize(R.styleable.WmaOptionView_line_right_margin,30);
        linearlayouPadding = array.getDimensionPixelSize(R.styleable.WmaOptionView_linearLayout_padding,30);
        array.recycle();
        setOrientation(VERTICAL);
        init();
    }
    TextView mValueTv;//选项语
    TextView mKeyTv;//提示语
    ImageView mRightImg;//右边图片
    ImageView mleftImg;//左边图片
    ImageView mLineImg;
    private void init() {
        LinearLayout mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
        mLinearLayout.setPadding(linearlayouPadding,linearlayouPadding,linearlayouPadding,linearlayouPadding);

        mKeyTv = new TextView(mContext);
        LayoutParams mKeyLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mKeyLp.weight = 1;
        mKeyTv.setGravity(Gravity.LEFT);
        mKeyTv.setTextColor(keyTextColor);
        mKeyTv.setText(keyText);
        mKeyTv.setTextSize(keyTextSize);
        mKeyTv.setMaxLines(1);
        mKeyTv.setEllipsize(TextUtils.TruncateAt.END);
        mKeyTv.setLayoutParams(mKeyLp);



        mleftImg = new ImageView(mContext);
        mleftImg.setImageResource(leftImgId);
        LayoutParams mImgLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mImgLp.rightMargin = 15;
        mleftImg.setLayoutParams(mImgLp);


        mValueTv = new TextView(mContext);
        mValueTv.setGravity(Gravity.CENTER);
        mValueTv.setTextColor(valueTextColor);
        mValueTv.setText(valueText);
        mValueTv.setTextSize(valueTextSize);
        mValueTv.setMaxLines(1);
        mValueTv.setEllipsize(TextUtils.TruncateAt.END);


        mRightImg = new ImageView(mContext);
        mRightImg.setImageResource(rightImgId);
        LayoutParams mImgLp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mImgLp2.leftMargin = 15;
        mRightImg.setLayoutParams(mImgLp2);


        mLinearLayout.addView(mleftImg);
        mLinearLayout.addView(mKeyTv);
        mLinearLayout.addView(mValueTv);
        mLinearLayout.addView(mRightImg);


        mLineImg = new ImageView(mContext);
        LayoutParams mLineImgLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mLineImgLp.leftMargin = lineLeftMargin;
        mLineImgLp.rightMargin = lineRightMargin;
        mLineImgLp.height = 1;
        mLineImgLp.gravity = Gravity.CENTER;
        mLineImg.setBackgroundColor(lineColor);
        mLineImg.setLayoutParams(mLineImgLp);
        if(isLineVisable == 0){
            mLineImg.setVisibility(VISIBLE);
        }else if(isLineVisable == 1){
            mLineImg.setVisibility(GONE);
        }else if(isLineVisable == 2){
            mLineImg.setVisibility(INVISIBLE);
        }
        addView(mLinearLayout);
        addView(mLineImg);
    }
    public TextView getKeyTv(){
        return mKeyTv;
    }
    public TextView getValueTv(){
        return mValueTv;
    }
    /**
     * dp转换成px
     */
    private int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    /**
     * px转换成dp
     */
    private int px2dp(Context context, float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
    /**
     * sp转换成px
     */
    private int sp2px(Context context, float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    private int px2sp(Context context, float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }
}
