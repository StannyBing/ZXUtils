package com.stanny.demo.ui.widget;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanny.demo.R;
import com.stanny.demo.adapter.DeleteTestAdapter;
import com.stanny.demo.model.KeyValueEntity;
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.RecylerMenu.ZXRecyclerDeleteHelper;

import java.util.ArrayList;
import java.util.List;

public class RecylerDeleteFragment extends Fragment {

    private RecyclerView recyclerView;
    private DeleteTestAdapter deleteAdapter;
    private ZXRecyclerDeleteHelper swipeHelper;
    private List<KeyValueEntity> keyValueEntities = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyler_delete, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_delete);
        initView();
        return view;
    }

    private void initView() {
        deleteAdapter = new DeleteTestAdapter(keyValueEntities);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(deleteAdapter);
        swipeHelper = new ZXRecyclerDeleteHelper(getActivity(), recyclerView)
                .setClickable(new ZXRecyclerDeleteHelper.OnItemClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        ZXToastUtil.showToast("点击了栏目");

                    }
                })
                .setSwipeOptionViews(R.id.tv_delete, R.id.tv_cancle)
                .setSwipeable(R.id.ll_content, R.id.ll_menu, new ZXRecyclerDeleteHelper.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID) {
                            case R.id.tv_delete:
                                ZXToastUtil.showToast("删除第" + position + "个");
                                break;
                            case R.id.tv_cancle:
                                ZXToastUtil.showToast("取消第" + position + "个");
                                break;
                            default:
                                break;
                        }
                    }
                });
        keyValueEntities.add(new KeyValueEntity("name", "dfsdgsgs"));
        keyValueEntities.add(new KeyValueEntity("namedf", "dfsdgsfadfwefgs"));
        keyValueEntities.add(new KeyValueEntity("namfasde", "dfsdgsgsggsgs"));
        keyValueEntities.add(new KeyValueEntity("nggame", "dfsdgsggasgegs"));
        keyValueEntities.add(new KeyValueEntity("nsgdfaame", "dfsegsegsdgsgs"));
        keyValueEntities.add(new KeyValueEntity("naffgme", "dfsggagdgsgs"));
        deleteAdapter.notifyDataSetChanged();

        deleteAdapter.setOnLoadMoreListener(new ZXQuickAdapter.RequestLoadMoreListener(){

            @Override
            public void onLoadMoreRequested() {

            }
        });
    }

    public static RecylerDeleteFragment newInstance(String s) {
        RecylerDeleteFragment detail = new RecylerDeleteFragment();
        return detail;
    }
}
