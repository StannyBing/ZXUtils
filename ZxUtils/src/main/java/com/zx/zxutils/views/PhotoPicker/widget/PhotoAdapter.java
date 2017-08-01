package com.zx.zxutils.views.PhotoPicker.widget;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zx.zxutils.R;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.PhotoPicker.PhotoPickUtils;
import com.zx.zxutils.views.PhotoPicker.ZXPhotoPreview;
import com.zx.zxutils.views.PhotoPicker.listener.OnDeleteListener;
import com.zx.zxutils.views.PhotoPicker.listener.OnPhotoItemClickListener;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by donglua on 15/5/31.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<String> photoPaths;
    private LayoutInflater inflater;
    private OnDeleteListener deleteListener;
    private OnPhotoItemClickListener photoItemClickListener;

    private Context mContext;
    public int maxNum = 9;

    public void setAction(@ZXPhotoPickerView.MultiPicAction int action) {
        this.action = action;
    }

    private int action;

    public PhotoAdapter(Context mContext, ArrayList<String> photoPaths, OnDeleteListener deleteListener, OnPhotoItemClickListener onPhotoItemClickListener) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        this.deleteListener = deleteListener;
        if (onPhotoItemClickListener != null) {
            photoItemClickListener = onPhotoItemClickListener;
        } else {
            photoItemClickListener = defaultListener;
        }
        inflater = LayoutInflater.from(mContext);
        padding = dip2Px(8);
    }

    public void add(ArrayList<String> photoPaths) {
        if (photoPaths != null && photoPaths.size() > 0) {
            this.photoPaths.addAll(photoPaths);
            notifyDataSetChanged();
        }

    }

    public void refresh(ArrayList<String> photoPaths, boolean preview) {
        ArrayList<String> tempList = new ArrayList<>();
        tempList.addAll(this.photoPaths);
        this.photoPaths.clear();
        this.photoPaths.addAll(photoPaths);
        if (!preview) {
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).startsWith("http")) {
                    if (i > tempList.size() - 1) {
                        this.photoPaths.add(tempList.get(i));
                    } else {
                        this.photoPaths.add(i, tempList.get(i));
                    }
                }
            }
        }
//        if (!preview) {
//            for (int i = 0; i < this.photoPaths.size(); i++) {
//                if (this.photoPaths.get(i).startsWith("http")) {
//                    httpPath.add(this.photoPaths.get(i));
//                }
//            }
//        }
//        this.photoPaths.clear();
//        this.photoPaths.addAll(httpPath);
//        if (photoPaths != null && photoPaths.size() > 0) {
//            this.photoPaths.addAll(photoPaths);
//        }
        notifyDataSetChanged();
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.__picker_item_photo, parent, false);
        return new PhotoViewHolder(itemView);
    }

    public int dip2Px(int dip) {
        // px/dip = density;
        float density = mContext.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + .5f);
        return px;
    }

    int padding;

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (action == ZXPhotoPickerView.ACTION_SELECT) {
            // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.ivPhoto.getLayoutParams();

            holder.ivPhoto.setPadding(padding, padding, padding, padding);


            if (position == getItemCount() - 1) {//最后一个始终是+号，点击能够跳去添加图片
                Glide.with(mContext)
                        .load("")
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.mipmap.icon_pic_default)
                        .error(R.mipmap.icon_pic_default)
                        .into(holder.ivPhoto);
//                holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        photoItemClickListener.onPhotoItemClick(ZXPhotoPickerView.ACTION_SELECT, position);
//                        if (photoPaths != null && photoPaths.size() >= maxNum) {
//                            Toast.makeText(mContext, "图片数已到达上限", Toast.LENGTH_SHORT).show();
//                        } else {
//                            PhotoPickUtils.startPick((Activity) mContext, false, 9, photoPaths);
//                        }
//                    }
//                });

                holder.deleteBtn.setVisibility(View.GONE);

            } else {
                String str = photoPaths.get(position);
                Log.e("file", str);
                Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
                Glide.with(mContext)
                        .load(str.startsWith("http") ? str : uri)
                        .centerCrop()
                        .thumbnail(0.1f)
                        // .bitmapTransform(new RoundedCornersTransformation(mContext,6,0))
                        .placeholder(R.drawable.__picker_default_weixin)
                        .error(R.mipmap.__picker_ic_broken_image_black_48dp)
                        .into(holder.ivPhoto);


                holder.deleteBtn.setVisibility(View.VISIBLE);
                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deleteListener != null) {
                            deleteListener.OnDetele(position);
                        } else {
                            photoPaths.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });

//                holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ZXPhotoPreview.builder()
//                                .setPhotos(photoPaths)
//                                .setAction(action)
//                                .setCurrentItem(position)
//                                .start((Activity) mContext);
//                    }
//                });
            }
        } else if (action == ZXPhotoPickerView.ACTION_ONLY_SHOW) {
            //Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
            //Uri uri = Uri.parse(photoPaths.get(position));
            Log.e("pic", photoPaths.get(position));
            Glide.with(mContext)
                    .load(photoPaths.get(position))
                    .centerCrop()
                    .thumbnail(0.1f)
                    // .bitmapTransform(new RoundedCornersTransformation(mContext,4,0))
                    .placeholder(R.drawable.__picker_default_weixin)
                    .error(R.mipmap.__picker_ic_broken_image_black_48dp)
                    .into(holder.ivPhoto);

//            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    ZXPhotoPreview.builder()
////                            .setPhotos(photoPaths)
////                            .setAction(action)
////                            .setCurrentItem(position)
////                            .start((Activity) mContext);
//                }
//            });
        } else if (action == ZXPhotoPickerView.ACTION_DELETE) {
//Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
            //Uri uri = Uri.parse(photoPaths.get(position));
            Log.e("pic", photoPaths.get(position));
            Glide.with(mContext)
                    .load(photoPaths.get(position))
                    .centerCrop()
                    .thumbnail(0.1f)
                    // .bitmapTransform(new RoundedCornersTransformation(mContext,4,0))
                    .placeholder(R.drawable.__picker_default_weixin)
                    .error(R.mipmap.__picker_ic_broken_image_black_48dp)
                    .into(holder.ivPhoto);
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.OnDetele(position);
                }
            });
//            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    ZXPhotoPreview.builder()
//                            .setPhotos(photoPaths)
//                            .setAction(action)
//                            .setCurrentItem(position)
//                            .start((Activity) mContext);
//                }
//            });
        }
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoItemClickListener.onPhotoItemClick(position);
//                ZXPhotoPreview.builder()
//                        .setPhotos(photoPaths)
//                        .setAction(action)
//                        .setCurrentItem(position)
//                        .start((Activity) mContext);
            }
        });

    }


    private OnPhotoItemClickListener defaultListener = new OnPhotoItemClickListener() {
        @Override
        public void onPhotoItemClick(int position) {
            if (action == ZXPhotoPickerView.ACTION_SELECT && position == getItemCount() - 1) {
                if (photoPaths != null && photoPaths.size() >= maxNum) {
                    ZXToastUtil.showToast("图片数已到达上限");
                } else {
                    PhotoPickUtils.startPick((Activity) mContext, false, 9, photoPaths);
                }
            } else {
                ZXPhotoPreview.builder()
                        .setPhotos(photoPaths)
                        .setAction(action)
                        .setCurrentItem(position)
                        .start((Activity) mContext);
            }
        }
    };

    @Override
    public int getItemCount() {
        return action == ZXPhotoPickerView.ACTION_SELECT ? photoPaths.size() + 1 : photoPaths.size();
    }


    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vSelected;
        public View cover;
        public View deleteBtn;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            vSelected.setVisibility(View.GONE);
            cover = itemView.findViewById(R.id.cover);
            cover.setVisibility(View.GONE);
            deleteBtn = itemView.findViewById(R.id.v_delete);
            deleteBtn.setVisibility(View.GONE);
        }
    }
}
