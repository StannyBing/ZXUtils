package com.stanny.demo.ui.widget;

import android.os.Bundle;
import android.view.View;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.TableView.ZXTableKeyValues;
import com.zx.zxutils.views.TableView.ZXTableListener;
import com.zx.zxutils.views.TableView.ZXTableView;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends BaseActivity {

    private ZXTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        tableView = (ZXTableView) findViewById(R.id.zxTv_table);
        tableView.setTitleInfo("名称", "数量")
                .showPercent(true)
                .setTableList(getList(20))
                .setTableListener(new ZXTableListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                        ZXToastUtil.showToast(tableView.getTableList().get(position).getValue() + "");
                        tableView.showBackIcon(true).setTableList(getList(10));
                    }
                    @Override
                    public void OnBack() {
                        tableView.showBackIcon(false).setTableList(getList(20));
                    }
                });
    }

    public List<ZXTableKeyValues> getList(int length) {
        List<ZXTableKeyValues> tableList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            ZXTableKeyValues key = new ZXTableKeyValues("第" + i + "个", Math.random() * 100);
            tableList.add(key);
        }
        return tableList;
    }
}
