package com.tylx.leasephone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.system.AppContext;

/**
 * Created by track on 2017/5/19.
 */

public class SearchLayout extends LinearLayout {
    LayoutInflater inflater;
    ImageView searchimg;
    TextView infotextview;
    EditText editText;
    LinearLayout searchlayout;
    FrameLayout rootView;
    String hint;
    int backgroundId;

    public SearchLayout(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public SearchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchLayout);
        hint = typedArray.getString(R.styleable.SearchLayout_shint);
        hint = hint == null ? "请输入关键字" : hint;
        backgroundId = typedArray.getResourceId(R.styleable.SearchLayout_sbackground, R.color.gray_d6d6d6);
        typedArray.recycle();
        init();

    }

    boolean changeaction = false;

    public void setChangeAction() {
        changeaction = true;
    }

    public SearchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchLayout);
        hint = typedArray.getString(R.styleable.SearchLayout_shint);
        hint = hint == null ? context.getString(R.string.key_word) : hint;
        backgroundId = typedArray.getResourceId(R.styleable.SearchLayout_sbackground, R.color.gray_d6d6d6);
        typedArray.recycle();
        init();
    }

    public void close() {
        if (insearch) {
            closesearch();
        }
    }

    void init() {
        View view = inflater.inflate(R.layout.search, this, true);
        searchimg = (ImageView) view.findViewById(R.id.searchimg);
        view.findViewById(R.id.onsearch).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchAction != null && insearch)
                    searchAction.onSearch(editText.getText().toString());
                toggleInput(0);
            }
        });
        rootView = (FrameLayout) view.findViewById(R.id.root_view);
        rootView.setBackgroundResource(backgroundId);
        infotextview = (TextView) view.findViewById(R.id.infotextview);
        editText = (EditText) view.findViewById(R.id.searchedit);
        editText.setVisibility(INVISIBLE);
        searchlayout = (LinearLayout) view.findViewById(R.id.searchlayout);
        searchlayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!insearch)
                    onsearch();
            }
        });
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    closesearch();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (searchAction != null)
                    searchAction.onSearch(textView.getText().toString());
                return false;
            }
        });
        infotextview.setText(hint);
        editText.setHint(hint);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (searchAction != null && changeaction)
                    searchAction.onSearch(editText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    boolean insearch = false;
    float searchimgleft = -11110;

    void onsearch() {
        //动画 开始的时候一直是 原生位置
        if (searchimgleft == -11110) {
            searchimgleft = searchimg.getLeft();
        }
        insearch = true;
        Animation translate = new TranslateAnimation(0, -searchimgleft, 0.f, 0.0f);
        translate.setFillAfter(true);
        translate.setDuration(200);
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                infotextview.setVisibility(INVISIBLE);
                editText.setVisibility(VISIBLE);
                editText.setFocusable(true);
                editText.requestFocus();
                toggleInput(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        searchlayout.startAnimation(translate);
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator animatorx = ObjectAnimator.ofFloat(searchimg, "translationX", -searchimg.getLeft());
//        animatorSet.play(animatorx);
//        animatorSet.setDuration(200);
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                infotextview.setVisibility(GONE);
//                editText.setVisibility(VISIBLE);
//                editText.setFocusable(true);
//                editText.requestFocus();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        animatorSet.start();
    }

    //0 隐藏 1show
    void toggleInput(int s) {
        InputMethodManager imm = (InputMethodManager) AppContext.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    void closesearch() {

        Animation translate = new TranslateAnimation(-searchimgleft, 0, 0.f, 0.0f);
        translate.setFillAfter(true);
        translate.setDuration(200);
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                infotextview.setVisibility(VISIBLE);
                editText.setVisibility(INVISIBLE);
                editText.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                insearch = false;
                toggleInput(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        searchlayout.startAnimation(translate);

//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator animatorx = ObjectAnimator.ofFloat(searchimg, "translationX", 0);
//        animatorSet.play(animatorx);
//        animatorSet.setDuration(200);
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                editText.setVisibility(GONE);
//                editText.setFocusable(false);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                insearch = false;
//                infotextview.setVisibility(VISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        animatorSet.start();

    }

    onSearchAction searchAction;

    public void setonSearchAction(onSearchAction searchAction) {
        this.searchAction = searchAction;
    }

    public interface onSearchAction {
        public void onSearch(String str);
    }
}
