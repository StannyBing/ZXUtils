package com.zx.zxutils.views.PhotoPicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.zx.zxutils.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by donglua on 15/6/21.
 */
public class PhotoPagerAdapter extends PagerAdapter {

    private List<String> paths = new ArrayList<>();
    private RequestManager mGlide;

    public PhotoPagerAdapter(RequestManager glide, List<String> paths) {
        this.paths = paths;
        this.mGlide = glide;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final Context context = container.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.__picker_picker_item_pager, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pager);

        final String path = paths.get(position);
        final Uri uri;
        if (path.startsWith("http") || path.startsWith("file:")) {
            uri = Uri.parse(path);
        } else {
            uri = Uri.fromFile(new File(path));
        }
        mGlide.load(uri)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .dontTransform()
                        .override(800, 800)
                        .placeholder(R.mipmap.__picker_ic_photo_black_48dp)
                        .error(R.mipmap.__picker_ic_broken_image_black_48dp)
                )
                .thumbnail(0.1f)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing()) {
                        ((Activity) context).onBackPressed();
                    }
                }
            }
        });

        container.addView(itemView);

        return itemView;
    }


    @Override
    public int getCount() {
        return paths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        Glide.with(container.getContext()).clear((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
