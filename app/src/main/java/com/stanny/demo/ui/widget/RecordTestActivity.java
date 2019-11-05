package com.stanny.demo.ui.widget;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stanny.demo.R;
import com.stanny.demo.model.MyEntity;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.listener.ZXRecordListener;
import com.zx.zxutils.other.ZXItemClickSupport;
import com.zx.zxutils.util.ZXRecordUtil;
import com.zx.zxutils.util.ZXSystemUtil;
import com.zx.zxutils.util.ZXToastUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordTestActivity extends BaseActivity {

    @BindView(R.id.rv_test_record)
    RecyclerView rvTestRecord;
    @BindView(R.id.btn_test_record)
    Button btnTestRecord;

    private ZXRecordUtil recordUtil;
    private ArrayList<MyEntity> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_test);
        ButterKnife.bind(this);

        final MyAdapter adapter = new MyAdapter(dataList);
        rvTestRecord.setLayoutManager(new GridLayoutManager(this, 4));
        rvTestRecord.setAdapter(adapter);
        recordUtil = new ZXRecordUtil(this);
        recordUtil.bindView(btnTestRecord);
        ZXItemClickSupport.addTo(rvTestRecord)
                .setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                        recordUtil.playMedia(dataList.get(position).getFile());
                    }
                });
        recordUtil.setOnRecordListener(new ZXRecordListener() {
            @Override
            public String onInitPath() {
                return ZXSystemUtil.getSDCardPath() + System.currentTimeMillis() + "x.amr";
            }

            @Override
            public void onSuccess(File file) {
                ZXToastUtil.showToast("地址:" + file.getAbsolutePath());
                dataList.add(new MyEntity(file.getName(), file));
                adapter.notifyDataSetChanged();
            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter {

        private ArrayList<MyEntity> dataList;

        public MyAdapter(ArrayList<MyEntity> dataList) {
            this.dataList = dataList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.tvTime.setText(dataList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            private TextView tvTime;

            public MyHolder(View itemView) {
                super(itemView);
                tvTime = (TextView) itemView.findViewById(R.id.tv_record_time);
            }
        }
    }
}
