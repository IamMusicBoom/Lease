package com.tylx.leasephone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RightOrderView extends LinearLayout implements
        View.OnTouchListener {
    List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
        // initKeys();
        keysum = keys.size();
        removeAllViews();
        addWord();
    }

    Context context;
    LayoutParams layoutoarams;
    OnWordDownListenter onWordDownListenter;
    int currentindex;
    int keysum;

    public void setOnWordDownListenter(OnWordDownListenter onWordDownListenter) {
        this.onWordDownListenter = onWordDownListenter;
    }

    public RightOrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
        layoutoarams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutoarams.weight = 1;
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        initKeys();
        addWord();
        setOnTouchListener(this);
        keysum = keys.size();
    }

    void addWord() {
        for (int i = 0; i < keys.size(); i++) {
            TextView textview = new TextView(context);
            textview.setText(keys.get(i));
            textview.setPadding(15, 0, 15, 0);
            textview.setTextColor(0xff000000);
            textview.setTag(i);
            textview.setTextSize(11);
            addView(textview, layoutoarams);
        }
    }

    void initKeys() {
        keys = new ArrayList<String>();
        keys.add("A");
        keys.add("B");
        keys.add("C");
        keys.add("D");
        keys.add("E");
        keys.add("F");
        keys.add("G");
        keys.add("H");
        keys.add("I");
        keys.add("J");
        keys.add("K");
        keys.add("L");
        keys.add("M");
        keys.add("N");
        keys.add("O");
        keys.add("P");
        keys.add("Q");
        keys.add("R");
        keys.add("S");
        keys.add("T");
        keys.add("U");
        keys.add("V");
        keys.add("W");
        keys.add("X");
        keys.add("Y");
        keys.add("Z");
    }

    int oldindex = -1;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        currentindex = (int) (event.getY() / (getHeight() / keysum));
        if (currentindex < 0)
            currentindex = 0;
        if (currentindex >= keysum)
            currentindex = keysum - 1;
        if (currentindex == oldindex)
            return false;
        oldindex = currentindex;
        if (onWordDownListenter != null)
            onWordDownListenter
                    .onWordDown(currentindex, keys.get(currentindex));

        return true;
    }

    public interface OnWordDownListenter {
        public void onWordDown(int index, String word);
    }
}
