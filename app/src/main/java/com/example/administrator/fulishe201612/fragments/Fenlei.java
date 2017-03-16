package com.example.administrator.fulishe201612.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.administrator.fulishe201612.R;
import com.example.administrator.fulishe201612.adapter.ExpandableAdapter;
import com.example.administrator.fulishe201612.application.I;
import com.example.administrator.fulishe201612.model.bean.CategoryChildBean;
import com.example.administrator.fulishe201612.model.bean.CategoryGroupBean;
import com.example.administrator.fulishe201612.model.net.CateListModel;
import com.example.administrator.fulishe201612.model.net.OnCompleteListener;
import com.example.administrator.fulishe201612.model.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fenlei extends Fragment {
    ExpandableListView expandableListView;

    ArrayList<CategoryGroupBean> categoryGroupBeen;
    ArrayList<ArrayList<CategoryChildBean>> categoryChildBeen;


    ExpandableAdapter expandableAdapter;

    public Fenlei() {

    }

    CateListModel cateListModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_fenlei, container, false);
        cateListModel = new CateListModel();
        categoryGroupBeen = new ArrayList<>();
        categoryChildBeen = new ArrayList<>();



        initData();
        initView(inflate);
        return inflate;
    }

    private void initData() {
        cateListModel.loadData(getActivity(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                ArrayList<CategoryGroupBean> list = OkHttpUtils.array2List(result);
                expandableAdapter.initContactgroup(list);
                for (CategoryGroupBean l : list) {
                    int id = l.getId();
                    downloadChildcate(id);
                }
                expandableAdapter.initContactchild(categoryChildBeen);

            }

            @Override
            public void onError(String error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void downloadChildcate(final int parentId) {
        final OkHttpUtils<CategoryChildBean[]> okHttpUtils = new OkHttpUtils<>(getActivity());
        okHttpUtils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, parentId + "")
                .targetClass(CategoryChildBean[].class)
                .execute(new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
                    @Override
                    public void onSuccess(CategoryChildBean[] result) {
                        ArrayList<CategoryChildBean> list = okHttpUtils.array2List(result);
                        categoryChildBeen.add(list);
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void initView(View inflate) {
        expandableListView = (ExpandableListView) inflate.findViewById(R.id.expandableListView);
        expandableAdapter = new ExpandableAdapter(getActivity(), categoryGroupBeen, categoryChildBeen);
        expandableListView.setAdapter(expandableAdapter);
    }

}
