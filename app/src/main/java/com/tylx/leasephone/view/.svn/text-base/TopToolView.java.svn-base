package com.tylx.leasephone.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylx.leasephone.R;

import java.util.ArrayList;


public class TopToolView extends LinearLayout {
    ArrayList<TextView> textViews;
    TextView currentTextView;
    Context context;
    Paint paint;
    OnselectItemListener myItemListener;
    Rect currentRect;
    Rect downRect;
    int movespeed = 60;
    String[] txts;
    int bottomheight = 5;
    int sum;
    int onselectcolor = 0xffcccccc;
    int lancolor = 0xffffaa01;
    int textsize = 15;
    int heght = 4; // padding 觉得控件高度的
    int width = 20; // 宽度的
    int currentindex = 0;

    public void setLanwidth(int lanwidth) {
        this.lanwidth = lanwidth;
        postInvalidate();
    }

    int lanwidth = 20; //0 是每个item底部画全

    public void setItemSelectListener(OnselectItemListener myItemListener) {
        this.myItemListener = myItemListener;
    }

    public TopToolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
//        TypedArray a = context
//                .obtainStyledAttributes(attrs, R.styleable.MyView);
        String txt = " , ";
//        txt = a.getString(R.styleable.MyView_infotxt);
        if (txt == null)
            txt = "列1,列2";
        txts = txt.split(",");
//        sum = txts.length;
//        textViews = new TextView[sum];
//        a.recycle();
//        Init();
        setWillNotDraw(false);
        setGravity(Gravity.CENTER);
//        onselectcolor = getResources().getColor(R.color.blue_unpress_3d9ce6);
        onselectcolor = ContextCompat.getColor(context, R.color.color_title);//字体颜色
        paint = new Paint();
//        paint.setColor(getResources().getColor(R.color.blue_unpress_3d9ce6));
        paint.setColor(ContextCompat.getColor(context,R.color.color_title));//下划线
        heght = (int) dp2px(context, heght); // 因为这个时候 textview
    }

    public void setSelectColor(int colorid) {
        onselectcolor = getResources().getColor(colorid);
        paint.setColor(getResources().getColor(colorid));
    }
    public void setTextDefaultColor(int colorid) {
        onselectcolor = getResources().getColor(colorid);
    }

    public void addTextTab(String m) {
        addTextTab(m, -1);
    }

    public void addTextTab(String m, int rightreid) {
        if (textViews == null)
            textViews = new ArrayList<>();
        LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        OnClickListener click = new OnClickListener() {
            public void onClick(View view) {
                currentTextView = (TextView) view;
                downRect = new Rect(currentTextView.getLeft() + lanwidth,
                        getHeight() - bottomheight,
                        currentTextView.getRight() - lanwidth, getHeight()); // 这货用的是dip
                if (downRect.left == currentRect.left)
                    return;// 原来的 就不操作
                postInvalidate();
                for (int i = 0; i < sum; i++) {
                    if (currentTextView == textViews.get(i)) {
                        currentindex = i;
                    }
                }
                setOnselectColor();
                if (myItemListener != null)
                    myItemListener.selectItem(currentindex);
            }
        };
        TextView textView = new TextView(context);
        textView.setText(m);
        textView.setTextSize(textsize);
        textView.setTextColor(Color.GRAY);
        textView.setGravity(Gravity.CENTER);
        if (rightreid != -1) {
            Drawable drawable = getResources().getDrawable(rightreid);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示

            textView.setCompoundDrawables(null, null, drawable, null);
        }
        textView.setSingleLine();
        textView.setPadding(width, heght, width, heght);// 扩大点击区
        textView.setOnClickListener(click);
        addView(textView, lp);
        textViews.add(textView);
        sum = textViews.size();
    }

    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * density + 0.5f);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (textViews == null || textViews.size() == 0)
            return;
        if (currentRect == null) {// 还没画呢
            currentRect = new Rect(currentTextView.getLeft() + lanwidth,
                    this.getHeight() - bottomheight, currentTextView.getRight()
                    - lanwidth, this.getHeight()); // 这货用的是dip
            downRect = currentRect;
        }
        canvas.drawRect(currentRect, paint);
        if (!isMoveComeple())
            postInvalidate();
    }

    public void setCurrentSelect(int item) {
        currentTextView = textViews.get(item);
        downRect = new Rect(currentTextView.getLeft() + lanwidth,
                TopToolView.this.getHeight() - bottomheight,
                currentTextView.getRight() - lanwidth,
                TopToolView.this.getHeight());
        currentindex = item;
        setOnselectColor();
        postInvalidate();
    }

    public interface OnselectItemListener {
        public void selectItem(int i);
    }

    boolean isMoveComeple() {// 当点击下的 绘制 是否移动完成了 移动完成返回真
        if (currentRect.left == downRect.left) {// 还要继续移动
            currentRect = downRect;
            return true;
        } else {
            if (Math.abs(currentRect.left - downRect.left) < movespeed) {
                currentRect.left = downRect.left;
                currentRect.right = downRect.right;
            } else {
                if (currentRect.left > downRect.left) { // 点下的在 当前的 左边
                    // current要变小
                    currentRect.left -= movespeed;
                    currentRect.right -= movespeed;
                } else {
                    currentRect.left += movespeed;
                    currentRect.right += movespeed;
                }
            }
            return false;
        }

    }

    void setOnselectColor() {// 被选中的 颜色修改

        for (int i = 0; i < sum; i++) {
            if (currentindex == i)
                textViews.get(i).setTextColor(onselectcolor);
            else
                textViews.get(i).setTextColor(getResources().getColor(R.color.gray_cccccc));
        }
    }

//    public void setInfoTexts(String text) {
//        txts = text.split(",");
//        sum = txts.length;
//        textViews = new TextView[sum];
//        removeAllViews();
//        Init();
//        postInvalidate();
//    }
}
