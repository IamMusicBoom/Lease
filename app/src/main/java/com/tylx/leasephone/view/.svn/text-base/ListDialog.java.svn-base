package com.tylx.leasephone.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.adapter.IViewDataAdapter;
import com.tylx.leasephone.databinding.DialogBaseBinding;
import com.tylx.leasephone.databinding.ItemDialogBaseBinding;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by track on 2017/4/19.
 */

public class ListDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {
    Context context;
    DialogBaseBinding binding;
    TheAdapter adapter;
    String title;

    public void setOnDialogItemClick(ListDialog.onDialogItemClick onDialogItemClick) {
        this.onDialogItemClick = onDialogItemClick;
    }

    onDialogItemClick onDialogItemClick;

    public ListDialog(@NonNull Context context, ArrayList<String> items) {
        super(context, R.style.Dialog);
        this.context = context;
        adapter = new TheAdapter();
        adapter.addInfos(items);
        init();
    }

    public ListDialog(@NonNull Context context, String title, String[] items) {
        super(context, R.style.Dialog);
        this.context = context;
        this.title = title;
        adapter = new TheAdapter();
        adapter.addInfos(Arrays.asList(items));
        init();
    }

    public ListDialog(@NonNull Context context, int title, int[] items) {
        super(context, R.style.Dialog);
        this.context = context;
        this.title = context.getString(title);
        adapter = new TheAdapter();
        for (int item : items) {
            adapter.addInfo(context.getString(item));
        }
        init();
    }

    public ListDialog(@NonNull Context context, String title, ArrayList<String> items) {
        super(context, R.style.Dialog);
        this.context = context;
        this.title = title;
        adapter = new TheAdapter();
        adapter.addInfos(items);
        init();
    }

    public ListDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ListDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected ListDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        init();
    }

    void init() {
        if (adapter == null)
            adapter = new TheAdapter();
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_base, null, false);
        setContentView(binding.getRoot());

        if (title != null) {
            binding.setTitle(title);
            binding.title.setVisibility(View.VISIBLE);
        }
        binding.listview.setAdapter(adapter);
        binding.listview.setOnItemClickListener(this);
        binding.setOnClickListener(this);
        Point point = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(point);
        WindowManager.LayoutParams lp = this.getWindow()
                .getAttributes();
        lp.width = point.x;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;

        this.getWindow().setWindowAnimations(R.style.dialoganimation);
        this.getWindow().setAttributes(lp);

    }

    public void setTitle(String title) {
        if (title == null)
            return;
        this.title = title;
        binding.setTitle(title);
        binding.title.setVisibility(View.VISIBLE);
    }

    public void setItem(ArrayList<String> items) {
        adapter.addInfos(items);
        adapter.notifyDataSetChanged();
    }

    public void addItem(String item) {
        adapter.addInfo(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (onDialogItemClick != null) {
            onDialogItemClick.onItemClick(i, adapter.getItem(i));
        }
        dismiss();
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    class TheAdapter extends IViewDataAdapter<String, ItemDialogBaseBinding> {


        @Override
        protected int getItemLayoutId(int position) {
            return R.layout.item_dialog_base;
        }

        @Override
        protected void bindData(ItemDialogBaseBinding binding, String info, int position) {
            binding.setInfo(info);
        }
    }

    public interface onDialogItemClick {
        public void onItemClick(int index, String info);
    }
}
