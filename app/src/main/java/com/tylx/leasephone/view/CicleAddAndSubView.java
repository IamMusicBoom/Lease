package com.tylx.leasephone.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.tylx.leasephone.R;

import java.math.BigDecimal;


/**
 * Created by zhouweilong on 2015/3/25.
 */
public class CicleAddAndSubView extends LinearLayout {

    private Context context = getContext();//上下文

    private OnNumChangeListener onNumChangeListener;

    private ImageView addButton;//加按钮
    private View addLinear;

    private ImageView subButton;//减按钮
    private View subLinear;

    public EditText getEditText() {
        return editText;
    }

    private EditText editText;//数量显示

    int num;          //数量值
    /**
     * 减
     */
    public static final int TYPE_SUBTRACT = 0;
    /**
     * 加
     */
    public static final int TYPE_ADD = 1;

    public boolean isAutoChangeNum = true;//是否自动转变数量

    private int minvalue = 1;
    private int maxvalue = 1000000;

    public float getAddvalue() {
        return addvalue;
    }

    public void setAddvalue(int addvalue) {
        this.addvalue = addvalue;
    }

    private int addvalue = 1;//增量

    public float getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(int minvalue) {
        this.minvalue = minvalue;
    }

    public float getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }

    public boolean isfomatinfo() {
        return isfomatinfo;
    }

    public void setFomatinfo(boolean isfomatinfo) {
        this.isfomatinfo = isfomatinfo;
    }

    /***
     * 是否格式化显示
     */
    boolean isfomatinfo;

    public void setInteger(boolean isinteger) {
        this.isinteger = isinteger;
    }

    boolean isinteger;//是否是整型增减

    /**
     * 构造方法
     */
    public CicleAddAndSubView(Context context) {
        super(context);
        this.context = context;
        num = 1;
        control();
    }

    /**
     * 构造方法
     *
     * @param context
     * @param
     */
    public CicleAddAndSubView(Context context, int num) {
        super(context);
        this.context = context;
        this.num = num;
        control();
    }

    public CicleAddAndSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        num = 1;
        control();
    }

    /**
     * 初始化
     */
    private void control() {
        setPadding(1, 5, 1, 5);
        initView();
        setViewListener();
//        setBackgroundResource(R.drawable.white_frame_shape);
    }


    /**
     * 初始化view
     */
    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.add_sub_view, null);
        addButton = (ImageView) view.findViewById(R.id.add_btn_id);
        subButton = (ImageView) view.findViewById(R.id.sub_btn_id);
        editText = (EditText) view.findViewById(R.id.num_text_id);
        addLinear = view.findViewById(R.id.linear_add);
        subLinear = view.findViewById(R.id.linear_sub);
        setAddBtnBackgroudResource(R.mipmap.goods_add_btn);
        setSubBtnBackgroudResource(R.mipmap.goods_sub_btn);
        editText.setEnabled(false);
        setNum(0);
        addView(view);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editText.getText().toString().equals("") || editText.getText().toString().equals("."))
                        setNum(minvalue);
                    else
                        setNum(Integer.valueOf(editText.getText().toString()));
                }
            }
        });
    }

    /**
     * 设置中间的距离
     *
     * @param distance
     */
    public void setMiddleDistance(int distance) {
        editText.setPadding(distance, 0, distance, 0);
    }

    /**
     * 设置默认数量
     *
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
        if (num <= minvalue) {
            num = minvalue;
//            subLinear.setVisibility(View.INVISIBLE);
            subLinear.setClickable(false);
        } else{
//            subLinear.setVisibility(View.VISIBLE);
            subLinear.setClickable(true);
        }
        if (num >= maxvalue) {
            num = maxvalue;
//            addLinear.setVisibility(View.INVISIBLE);
            addLinear.setClickable(false);
        } else{
//            addLinear.setVisibility(View.VISIBLE);
            addLinear.setClickable(true);
        }
        if (isfomatinfo)
            editText.setText(getInfotofloat(num) + "");
        else {
            if (isinteger)
                editText.setText((int) num + "");
            else
                editText.setText(num + "");
        }

    }

    public float getInfotofloat(float b) {
        return new BigDecimal(b + "").setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 获取值
     *
     * @return
     */
    public float getNum() {
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
            return Float.parseFloat(editText.getText().toString());
        } else {
            return 0;
        }
    }

    /**
     * 设置加号图片
     *
     * @param addBtnDrawable
     */
    public void setAddBtnBackgroudResource(int addBtnDrawable) {
        addButton.setBackgroundResource(addBtnDrawable);
    }

    /**
     * 设置减法图片
     *
     * @param subBtnDrawable
     */
    public void setSubBtnBackgroudResource(int subBtnDrawable) {
        subButton.setBackgroundResource(subBtnDrawable);
    }

    /**
     * 设置是否自动改变数量玩
     *
     * @param isAutoChangeNum
     */
    public void setAutoChangeNumber(boolean isAutoChangeNum) {
        this.isAutoChangeNum = isAutoChangeNum;
    }

    /**
     * 设置加法减法的背景色
     *
     * @param addBtnColor
     * @param subBtnColor
     */
    public void setButtonBgColor(int addBtnColor, int subBtnColor) {
        addButton.setBackgroundColor(addBtnColor);
        subButton.setBackgroundColor(subBtnColor);
    }

    /**
     * 设置监听回调
     *
     * @param onNumChangeListener
     */
    public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }


    /**
     * 设置监听器
     */
    private void setViewListener() {
        addLinear.setOnClickListener(new OnButtonClickListener());
        subLinear.setOnClickListener(new OnButtonClickListener());
    }


    /**
     * 监听器监听事件
     */
    private class OnButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            String numString = editText.getText().toString();
            if (TextUtils.isEmpty(numString)) {
                num = 0;
                editText.setText("0");
            } else {
                num = Integer.valueOf(numString);
            }

            if (v == addLinear) {
                num =  (num + addvalue);
                setNum(num);
                if (onNumChangeListener != null) {
                    onNumChangeListener.onNumChange(CicleAddAndSubView.this, TYPE_ADD, getNum());
                }
            } else if (v == subLinear) {
                num =  (num - addvalue);
                setNum(num);
                if (onNumChangeListener != null) {
                    onNumChangeListener.onNumChange(CicleAddAndSubView.this, TYPE_ADD, getNum());
                }
            }
        }
    }

    public interface OnNumChangeListener {
        /**
         * 监听方法
         *
         * @param view
         * @param stype 点击按钮的类型
         * @param num   返回的数值
         */
        public void onNumChange(View view, int stype, float num);
    }

}
