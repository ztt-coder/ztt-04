package com.example.jxxy.z.fragment;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jxxy.z.R;
import com.example.jxxy.z.activity.GoodsListActivity;
import com.example.jxxy.z.adapter.CategoryLeftAdapter;
import com.example.jxxy.z.adapter.CategoryRightAdapter;
import com.example.jxxy.z.common.BaseFragment;
import com.example.jxxy.z.http.ProgressDialogSubscriber;
import com.example.jxxy.z.http.entity.CategoryEntity;
import com.example.jxxy.z.http.presenter.CategoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CategroyFragment extends BaseFragment {
    @BindView(R.id.rv_left)
    RecyclerView rv_left;

    @BindView(R.id.rv_right)
    RecyclerView rv_right;

    private List<CategoryEntity> leftData;
    private List<CategoryEntity> rightData;
    private CategoryLeftAdapter leftAdapter;
    private CategoryRightAdapter rightAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_category;
    }

    @OnClick(R.id.ll_search)
    void search(){toastshort("开发中...");}//点击搜索框操作反馈


    @Override
    protected void initData() {
        super.initData();

        CategoryPresenter.getTopList(new ProgressDialogSubscriber<List<CategoryEntity>>(getActivity()) {
            @Override
            public void onNext(List<CategoryEntity> categoryEntities) {
                leftData.clear();
                leftData.addAll(categoryEntities);
                leftAdapter.notifyDataSetChanged();;
                leftAdapter.setSelect(0);
                loadSecondList(0);
            }
        });
    }//利用CategoryPresenter先加载左边适配器，在左适配器成功加载后再加载有适配器

    private void loadSecondList(int pos){
        if (leftData==null||leftData.size()==0){
            return;
        }

        CategoryEntity entity = leftData.get(pos);
        CategoryPresenter.getSecondList(new ProgressDialogSubscriber<List<CategoryEntity>>(getActivity()) {
            @Override
            public void onNext(List<CategoryEntity> categoryEntities) {
                rightData.clear();
                rightData.addAll(categoryEntities);
                rightAdapter.notifyDataSetChanged();
            }
        },entity.getCat_id());
    }//和左相同，加载操作包括清除clear，添加所有数据addAll和数据刷新notifyDataSetChanged

    @Override
    protected void initView(View view) {
        super.initView(view);
        leftData =new ArrayList<>();
        rightData = new ArrayList<>();

        LinearLayoutManager leftManager = new LinearLayoutManager(getActivity());
        leftManager.setOrientation(OrientationHelper.VERTICAL);
        rv_left.setLayoutManager(leftManager);

        GridLayoutManager rightManager = new GridLayoutManager(getActivity(),3,OrientationHelper.VERTICAL,false);
        rv_right.setLayoutManager(rightManager);

        leftAdapter = new CategoryLeftAdapter(getActivity(),leftData);
        leftAdapter.setOnItemClickListener(new CategoryLeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, CategoryEntity entity) {
                leftAdapter.setSelect(position);
                loadSecondList(position);
            }
        });
        rightAdapter = new CategoryRightAdapter(getActivity(),rightData);
        rightAdapter.setOnItemClickListener(new CategoryRightAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, CategoryEntity entity) {
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                intent.putExtra("cat_id",entity.getCat_id());
                startActivity(intent);
            }
        });

        rv_left.setAdapter(leftAdapter);
        rv_right.setAdapter(rightAdapter);

    }//初始化视图
}
