package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.model.KeyValueEntity;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXSharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SharedPrefTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnbarView;

    private ZXSharedPrefUtil sharedPrefUtil = new ZXSharedPrefUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnbarView
                .addBtn("存储简单数据:abc-String,123-int,1.23f-float")
                .addBtn("取出简单数据")
                .addBtn("存储集合数据:arraylist<Bean>")
                .addBtn("取出集合数据")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://存储数据
                sharedPrefUtil.putString("put_test1", "abc");
                sharedPrefUtil.putInt("put_test2", 123);
                sharedPrefUtil.putFloat("put_test3", 1.23f);
                btnbarView.printInfo("数据添加成功");
                break;
            case 1:
                btnbarView.printInfo(sharedPrefUtil.getString("put_test1"));
                btnbarView.printInfo(sharedPrefUtil.getInt("put_test2") + "");
                btnbarView.printInfo(sharedPrefUtil.getFloat("put_test3") + "");
                break;
            case 2:
                List<KeyValueEntity> testList = new ArrayList<>();
                testList.add(new KeyValueEntity("1", "qwer"));
                testList.add(new KeyValueEntity("2", "asdf"));
                testList.add(new KeyValueEntity("3", "zxcv"));
                sharedPrefUtil.putList("list_test", testList);
                btnbarView.printInfo("数据添加成功，List长度为3");
                break;
            case 3:
                List<KeyValueEntity> tests = sharedPrefUtil.getList("list_test");
                for (int i = 0; i < tests.size(); i++) {
                    btnbarView.printInfo(tests.get(i).getValue());
                }
                break;
            default:
                break;
        }
    }
}
