package com.tylx.leasephone.view;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tylx.leasephone.R;


/**
 * Created by Administrator on 2017/2/9.
 */

public class EditDialog extends android.app.Dialog {

    private EditText editText;
    private final Button confirm;

    public EditDialog(Context context) {
        super(context);
        getWindow().setDimAmount(0.5f);
//        getWindow().setAttributes(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewGroup decorView = ((ViewGroup) getWindow().getDecorView());
        decorView.removeAllViews();
        decorView.addView(LayoutInflater.from(context).inflate(R.layout.dialog_edit, decorView, false));

        editText = (EditText) findViewById(R.id.edit);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmClickListener != null) {
                    onConfirmClickListener.onConfirm(EditDialog.this, editText.getText().toString());
                }
            }
        });
    }

    public EditDialog(Context context, String content) {
        super(context);
        getWindow().setDimAmount(0.5f);
//        getWindow().setAttributes(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewGroup decorView = ((ViewGroup) getWindow().getDecorView());
        decorView.removeAllViews();
        decorView.addView(LayoutInflater.from(context).inflate(R.layout.dialog_edit, decorView, false));

        editText = (EditText) findViewById(R.id.edit);
        editText.setText(content);
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmClickListener != null) {
                    onConfirmClickListener.onConfirm(EditDialog.this, editText.getText().toString());
                }
            }
        });
    }

    public interface OnConfirmClickListener {
        void onConfirm(DialogInterface dialog, String content);
    }

    private OnConfirmClickListener onConfirmClickListener;

    public EditDialog setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
        return this;
    }
    public EditText getEditText(){
        return editText;
    }
    public void clear(){
        if(!TextUtils.isEmpty(editText.getText().toString())){
            editText.setText("");
        }
    }
    public String getContent(){
        return editText.getText().toString();
    }
}
