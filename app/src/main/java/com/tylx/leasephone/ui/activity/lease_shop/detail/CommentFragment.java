package com.tylx.leasephone.ui.activity.lease_shop.detail;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.FragmetCommentBinding;
import com.tylx.leasephone.databinding.ItemCommentBinding;
import com.tylx.leasephone.databinding.ItemHomeBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.CommentListModel;
import com.tylx.leasephone.model.CommentModel;
import com.tylx.leasephone.model.GoodsModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.util.activity.BaseActivity;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListFragment2;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/6/22.
 * 评论
 */

public class CommentFragment extends BaseRecyclerViewListFragment2<CommentModel,ItemCommentBinding> {
    FragmetCommentBinding binding;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragmet_comment, _containerLayout, false);
        _listview = binding.listview;
        binding.listview.setNestedScrollingEnabled(false);
        return binding.getRoot();
    }
    @Override
    protected View Listplaceholder() {
        return binding.placeholder;
    }
    CommentListModel model;
    @Override
    protected void getListData() {
        super.getListData();
        new MemberModel().getCommondityComments(((GoodsDetailActivity)mActivity).goodsId,page,new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {

                model = (CommentListModel) o;
                total = model.total;
                loadData(model.result);
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
                if(binding.listview.isRefreshing()){
                    binding.listview.onRefreshComplete();
                }
            }
        });
    }

    @Override
    public int getItemLayout(int viewTyoe) {
        return R.layout.item_comment;
    }

    @Override
    public void bindItemData(ItemCommentBinding itemCommentBinding, CommentModel info, int position) {
        itemCommentBinding.setModel(info);

    }


}
