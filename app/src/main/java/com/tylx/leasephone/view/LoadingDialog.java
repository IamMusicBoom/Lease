package com.tylx.leasephone.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.tylx.leasephone.R;


public class LoadingDialog extends Dialog {
	private Context mContext;
	private LayoutInflater inflater;
	private LayoutParams lp;
	private TextView loadingText;
	private ImageView mLoadingImg;
	public LoadingDialog(Context context, String content) {
		super(context, R.style.Dialog);

		this.mContext = context;
		// 设置点击对话框之外能消失
		setCanceledOnTouchOutside(true);
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.loading, null);
		loadingText = (TextView) layout.findViewById(R.id.progress_msg);
		mLoadingImg = (ImageView) layout.findViewById(R.id.animal_img);
		if (!TextUtils.isEmpty(content)) {
			loadingText.setText(content);
			loadingText.setVisibility(View.VISIBLE);
		}else{
			loadingText.setVisibility(View.GONE);

		}
		setContentView(layout);

		// 设置window属性
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0; // 去背景遮盖
		lp.alpha = 1.0f;
		getWindow().setAttributes(lp);
	}
	
	public LoadingDialog(Context context, String content, boolean isCancelable) {
		super(context, R.style.Dialog);

		this.mContext = context;
		// 设置点击对话框之外能消失
		setCanceledOnTouchOutside(isCancelable);

		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.loading, null);
		loadingText = (TextView) layout.findViewById(R.id.progress_msg);
		mLoadingImg = (ImageView) layout.findViewById(R.id.animal_img);
		if (!TextUtils.isEmpty(content)) {
			loadingText.setText(content);
			loadingText.setVisibility(View.VISIBLE);
		}else{
			loadingText.setVisibility(View.GONE);

		}
		setContentView(layout);

		// 设置window属性
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0; // 去背景遮盖
		lp.alpha = 1.0f;
		getWindow().setAttributes(lp);
	}

	public void setMessage(String message){
		this.loadingText.setText(message);
	}

	@Override
	public void show() {
		((AnimationDrawable) mLoadingImg.getDrawable()).start();
		super.show();
	}

	@Override
	public void dismiss() {
		mLoadingImg.clearAnimation();
		super.dismiss();
	}
}
