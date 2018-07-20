package com.stanny.demo.ui.util;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.stanny.demo.R;
import com.stanny.demo.adapter.MultiAdapter;
import com.stanny.demo.adapter.QuickAdapter;
import com.stanny.demo.model.AdapterTestEntity;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter;
import com.zx.zxutils.util.ZXToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuickAdapterTestActivity extends BaseActivity {

    @BindView(R.id.rg_adapter)
    RadioGroup rgAdapter;
    @BindView(R.id.rv_adapter)
    RecyclerView rvAdapter;

    private List<String> datas = new ArrayList<>();
    private List<AdapterTestEntity> testEntities = new ArrayList<>();

    private QuickAdapter quickAdapter;
    private MultiAdapter multiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_adapter_test);
        ButterKnife.bind(this);
        for (int i = 0; i < 10; i++) {
            datas.add("title" + i);
        }
        for (int i = 0; i < 20; i++) {
            if (i % 5 == 0) {
                testEntities.add(new AdapterTestEntity(0, "title"));
            } else {
                testEntities.add(new AdapterTestEntity(1, "content"));
            }
        }
        rgAdapter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_quick) {
                    rvAdapter.setAdapter(quickAdapter = new QuickAdapter(datas));
                } else if (checkedId == R.id.rb_multi) {
                    rvAdapter.setAdapter(multiAdapter = new MultiAdapter(testEntities));
                }
            }
        });
        rvAdapter.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter.setAdapter(quickAdapter = new QuickAdapter(datas));
        rvAdapter.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        quickAdapter.setOnItemClickListener(new ZXQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ZXQuickAdapter adapter, View view, int position) {
                ZXToastUtil.showToast("点击" + position);
            }
        });
        quickAdapter.setOnItemLongClickListener(new ZXQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(ZXQuickAdapter adapter, View view, int position) {
                ZXToastUtil.showToast("长按" + position);
                return false;
            }
        });
        quickAdapter.setOnLoadMoreListener(new ZXQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                for (int i = 0; i < 10; i++) {
                    datas.add("title" + i);
                }
                quickAdapter.loadMoreComplete();
            }
        }, rvAdapter);
    }
}
