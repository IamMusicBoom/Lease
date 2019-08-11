package com.tylx.leasephone.ui.activity.lease_shop.order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tylx.leasephone.R;
import com.tylx.leasephone.databinding.FragmetCommentBinding;
import com.tylx.leasephone.databinding.FragmetLeaseOrderBinding;
import com.tylx.leasephone.databinding.ItemOrderBinding;
import com.tylx.leasephone.databinding.ItemOrderLeaseBinding;
import com.tylx.leasephone.model.BaseModel;
import com.tylx.leasephone.model.GoodsListModel;
import com.tylx.leasephone.model.MemberModel;
import com.tylx.leasephone.model.OrderListModel;
import com.tylx.leasephone.model.OrderModel;
import com.tylx.leasephone.model.ResultsModel;
import com.tylx.leasephone.ui.activity.HomeActivity;
import com.tylx.leasephone.util.DatasStore;
import com.tylx.leasephone.util.activity.BaseFragment;
import com.tylx.leasephone.util.activity.BaseRecyclerViewListFragment2;
import com.tylx.leasephone.view.MyPayDialog;
import com.tylx.leasephone.view.TopToolView;

/**
 * Created by wangm on 2017/6/22.
 * 租赁订单列表
 */

public class LeaseOrderFragment extends BaseRecyclerViewListFragment2<OrderModel, ItemOrderBinding> {
    FragmetLeaseOrderBinding binding;
    private int curStatus = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTopView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (OrderListAcitivity.isLeaseCommentComplite) {
            binding.listview.setRefreshing(true);
            OrderListAcitivity.isLeaseCommentComplite = false;
        }
    }

    @Override
    public int getItemLayout(int viewTyoe) {
        return R.layout.item_order;
    }

    @Override
    public void bindItemData(ItemOrderBinding itemOrderBinding, final OrderModel info, int position) {
        OrderListModel.setDatas(itemOrderBinding, info, (OrderListAcitivity) mActivity, OrderListAcitivity.LEASE);
        itemOrderBinding.redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redBtnOnclick(info);
            }
        });
        itemOrderBinding.grayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grayBtnOnclick(info);
            }
        });
        if (info.tradeStatus == 9 || info.tradeStatus == 6) {
            itemOrderBinding.mSmlItem.setSwipeEnable(true);
        } else {
            itemOrderBinding.mSmlItem.setSwipeEnable(false);
        }
        itemOrderBinding.orderListDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDelete(info);
            }


        });
    }

    //    @Override
//    public void bindItemData(ItemOrderLeaseBinding itemOrderLeaseBinding, final OrderModel info, int position) {
//        OrderListModel.setDatas(itemOrderLeaseBinding, info, (OrderListAcitivity) mActivity, OrderListAcitivity.LEASE);
//        itemOrderLeaseBinding.redBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                redBtnOnclick(info);
//            }
//        });
//        itemOrderLeaseBinding.grayBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                grayBtnOnclick(info);
//            }
//        });
//
//    }

    /**
     * 删除
     * @param info
     */
    private void goDelete(OrderModel info) {
        new MemberModel().delOrder(info.id, DatasStore.getCurMember().id + "", new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading();
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                binding.listview.setRefreshing(true);
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    @Override
    protected View onCreateContentView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragmet_lease_order, _containerLayout, false);
        _listview = binding.listview;
        return binding.getRoot();
    }

    private void initTopView() {
        binding.toptoolview.setLanwidth(100);
        binding.toptoolview.addTextTab("申请中");
        binding.toptoolview.addTextTab("待评价");
        binding.toptoolview.setCurrentSelect(0);
        binding.toptoolview.setItemSelectListener(new TopToolView.OnselectItemListener() {
            @Override
            public void selectItem(int i) {
                switch (i) {
                    case 0:
                        curStatus = 0;
                        break;
                    case 1:
                        curStatus = 9;
                        break;
                }
                binding.listview.setRefreshing(true);
            }
        });
    }

    @Override
    protected View Listplaceholder() {
        return binding.placeholder;
    }

    @Override
    protected void getListData() {
        super.getListData();
        new MemberModel().getOrderTradePage(page, curStatus, 1, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                if (page == 1 && !_listview.isRefreshing())
                    showLoading();
            }

            @Override
            public void successful(Object o) {
                OrderListModel model = (OrderListModel) o;
                total = model.total;
                loadData(model.result);
                hideLoading();
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
                if (binding.listview.isRefreshing()) {
                    binding.listview.onRefreshComplete();
                }
            }
        });
    }

    /**
     * 灰色按钮
     *
     * @param info
     */
    private static void grayBtnOnclick(OrderModel info) {
        if (info.tradeStatus == 0) {//申请中

        } else if (info.tradeStatus == 2) {//已通过

        } else if (info.tradeStatus == 9 || info.tradeStatus == 6) {//待评价

        } else {

        }
    }

    /**
     * 红色按钮
     *
     * @param info
     */
    private void redBtnOnclick(OrderModel info) {
        if (info.tradeStatus == 0) {//申请中

        } else if (info.tradeStatus == 2) {//已通过
            goSure(info);
        } else if (info.tradeStatus == 9) {//待评价

        } else {

        }

    }

    /**
     * 确认收货
     *
     * @param info
     */
    private void goSure(OrderModel info) {
        new MemberModel().confirmReceipt(info.id, DatasStore.getCurMember().id, new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                showLoading(true);
            }

            @Override
            public void successful(Object o) {
                hideLoading();
                binding.listview.setRefreshing(true);
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                hideLoading(resultsModel.getErrorMsg().toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OrderDetailActivity.REQUEST_CODE && resultCode == mActivity.RESULT_OK) {
            binding.listview.setRefreshing(true);
        }
    }
}
