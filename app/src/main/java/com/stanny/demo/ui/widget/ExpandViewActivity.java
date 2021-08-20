package com.stanny.demo.ui.widget;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.stanny.demo.R;
import com.zx.zxutils.other.ZXExpandItemClickListener;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.ExpandableView.ZXExpandBean;
import com.zx.zxutils.views.ExpandableView.ZXExpandRecyclerHelper;

import java.util.ArrayList;
import java.util.List;

public class ExpandViewActivity extends AppCompatActivity {

    private RecyclerView rvExpand;
    private List<ZXExpandBean> dataList = new ArrayList<>();
    private ZXExpandRecyclerHelper recyclerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_view);
        for (int i = 0; i < 5; i++) {
            ZXExpandBean expandBean1 = new ZXExpandBean("123000", "123");
            List<ZXExpandBean> dataList1 = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                ZXExpandBean expandBean2 = new ZXExpandBean("120003", "123");
                List<ZXExpandBean> dataList2 = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    dataList2.add(new ZXExpandBean("12003", "123"));
                }
                expandBean2.setChildList(dataList2);
                dataList1.add(expandBean2);
            }
            expandBean1.setChildList(dataList1);
            dataList.add(expandBean1);
        }

        rvExpand = findViewById(R.id.rv_expand);
        recyclerHelper = ZXExpandRecyclerHelper.getInstance(this);
        recyclerHelper.withRecycler(rvExpand)
                .showSelect(true, true)
                .setData(dataList)
                .setItemTextSizeSp(14)
                .setItemHeightDp(40)
                .setChildSelectWhenMenuSelect(true)
                .setMenuCanSelect(true)
                .setItemClickListener(new ZXExpandItemClickListener() {
                    @Override
                    public void onItemClick(ZXExpandBean expandBean, int showPosition) {
                        ZXToastUtil.showToast(expandBean.getId());
                        recyclerHelper.changeOpenStatus(showPosition);
                    }
                })
                .build();
    }
}
