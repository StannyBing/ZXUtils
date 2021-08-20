package com.zx.zxutils.views.PhotoPicker.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zx.zxutils.R;
import com.zx.zxutils.views.PhotoPicker.PhotoPicker;
import com.zx.zxutils.views.PhotoPicker.PhotoPickerActivity;
import com.zx.zxutils.views.PhotoPicker.ZXPhotoPreview;
import com.zx.zxutils.views.PhotoPicker.adapter.PhotoGridAdapter;
import com.zx.zxutils.views.PhotoPicker.adapter.PopupDirectoryListAdapter;
import com.zx.zxutils.views.PhotoPicker.entity.Photo;
import com.zx.zxutils.views.PhotoPicker.entity.PhotoDirectory;
import com.zx.zxutils.views.PhotoPicker.event.OnPhotoClickListener;
import com.zx.zxutils.views.PhotoPicker.utils.ImageCaptureManager;
import com.zx.zxutils.views.PhotoPicker.utils.MediaStoreHelper;
import com.zx.zxutils.views.PhotoPicker.widget.Titlebar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoPickerFragment extends Fragment {

    private ImageCaptureManager captureManager;
    private PhotoGridAdapter photoGridAdapter;

    private PopupDirectoryListAdapter listAdapter;
    //所有photos的路径
    private List<PhotoDirectory> directories;
    //传入的已选照片
    private ArrayList<String> originalPhotos;

    private boolean justCamera = false;

    private int SCROLL_THRESHOLD = 30;
    int column;
    //目录弹出框的一次最多显示的目录数目
    public static int COUNT_MAX = 4;
    private final static String EXTRA_CAMERA = "camera";
    private final static String EXTRA_COLUMN = "column";
    private final static String EXTRA_COUNT = "count";
    private final static String EXTRA_GIF = "gif";
    private final static String EXTRA_ORIGIN = "origin";
    private ListPopupWindow listPopupWindow;
    private RequestManager mGlideRequestManager;
    private Context mContext;

    private Titlebar titlebar;

    public static PhotoPickerFragment newInstance(boolean showCamera, boolean showGif,
                                                  boolean previewEnable, int column, int maxCount, ArrayList<String> originalPhotos, boolean justCamera) {
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_CAMERA, showCamera);
        args.putBoolean(EXTRA_GIF, showGif);
        args.putBoolean(PhotoPicker.EXTRA_PREVIEW_ENABLED, previewEnable);
        args.putBoolean(PhotoPicker.EXTRA_JUST_CAMERA, justCamera);
        args.putInt(EXTRA_COLUMN, column);
        args.putInt(EXTRA_COUNT, maxCount);
        args.putStringArrayList(EXTRA_ORIGIN, originalPhotos);
        PhotoPickerFragment fragment = new PhotoPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mGlideRequestManager = Glide.with(this);

        directories = new ArrayList<>();
        originalPhotos = getArguments().getStringArrayList(EXTRA_ORIGIN);

        column = getArguments().getInt(EXTRA_COLUMN, PhotoPicker.DEFAULT_COLUMN_NUMBER);
        boolean showCamera = getArguments().getBoolean(EXTRA_CAMERA, true);
        boolean previewEnable = getArguments().getBoolean(PhotoPicker.EXTRA_PREVIEW_ENABLED, true);
        justCamera = getArguments().getBoolean(PhotoPicker.EXTRA_JUST_CAMERA, false);

        photoGridAdapter = new PhotoGridAdapter(mContext, mGlideRequestManager, directories, originalPhotos, column);
        photoGridAdapter.setShowCamera(showCamera);
        photoGridAdapter.setPreviewEnable(previewEnable);

        Bundle mediaStoreArgs = new Bundle();

        boolean showGif = getArguments().getBoolean(EXTRA_GIF);
        mediaStoreArgs.putBoolean(PhotoPicker.EXTRA_SHOW_GIF, showGif);
        MediaStoreHelper.getPhotoDirs(getActivity(), mediaStoreArgs,
                new MediaStoreHelper.PhotosResultCallback() {
                    @Override
                    public void onResultCallback(List<PhotoDirectory> dirs) {
                        directories.clear();
                        directories.addAll(dirs);
                        photoGridAdapter.notifyDataSetChanged();
                        listAdapter.notifyDataSetChanged();
                        adjustHeight();
                    }
                });

        captureManager = new ImageCaptureManager(getActivity());

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        if (justCamera) {
            doCameraAction(justCamera);
        }
//            }
//        }, 1000);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.__picker_fragment_photo_picker, container, false);
        titlebar = (Titlebar) rootView.findViewById(R.id.titlebar);

        listAdapter = new PopupDirectoryListAdapter(mGlideRequestManager, directories);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_photos);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(column, OrientationHelper.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photoGridAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final Button btSwitchDirectory = (Button) rootView.findViewById(R.id.button);

        Button btnPreview = (Button) rootView.findViewById(R.id.btn_preview);

        listPopupWindow = new ListPopupWindow(getActivity());

        listPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//替换背景
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int widths = wm.getDefaultDisplay().getWidth();
        listPopupWindow.setWidth(widths);//ListPopupWindow.MATCH_PARENT还是会有边距，直接拿到屏幕宽度来设置也不行，因为默认的background有左右padding值。
  /*  int height = wm.getDefaultDisplay().getHeight();
    listPopupWindow.setHeight((int) (height *0.7));*/
        listPopupWindow.setAnchorView(btSwitchDirectory);
        listPopupWindow.setAdapter(listAdapter);
        listPopupWindow.setModal(true);

        listPopupWindow.setDropDownGravity(Gravity.BOTTOM);
//    listPopupWindow.setAnimationStyle(R.style.__picker_mystyle);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();

                PhotoDirectory directory = directories.get(position);

                btSwitchDirectory.setText(directory.getName().toLowerCase());//默认会大写，这里要改成小写

                photoGridAdapter.setCurrentDirectoryIndex(position);
                photoGridAdapter.notifyDataSetChanged();
            }
        });

        photoGridAdapter.setOnPhotoClickListener(new OnPhotoClickListener() {
            @Override
            public void onClick(View v, int position, boolean showCamera) {
                final int index = showCamera ? position - 1 : position;

                List<String> photos = photoGridAdapter.getCurrentPhotoPaths();

                int[] screenLocation = new int[2];
                v.getLocationOnScreen(screenLocation);
                ImagePagerFragment imagePagerFragment =
                        ImagePagerFragment.newInstance(photos, index, screenLocation, v.getWidth(),
                                v.getHeight());

                ((PhotoPickerActivity) getActivity()).addImagePagerFragment(imagePagerFragment);
            }
        });

        photoGridAdapter.setOnCameraClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = captureManager.dispatchTakePictureIntent();
                    startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btSwitchDirectory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listPopupWindow.isShowing()) {
                    listPopupWindow.dismiss();
                } else if (!getActivity().isFinishing()) {
                    adjustHeight();
                    listPopupWindow.show();
                    listPopupWindow.getListView().setVerticalScrollBarEnabled(false);

                    //去掉滑动条,listview 在show之后才建立，所以需要该方法在show之后调用，否则会空指针
                }
            }
        });


        //预览按钮
        btnPreview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoGridAdapter.getSelectedPhotoPaths().size() > 0) {
                    ZXPhotoPreview.builder()
                            .setPhotos(photoGridAdapter.getSelectedPhotoPaths())
                            .setCurrentItem(0)
                            .start(getActivity());
                } else {
                    Toast.makeText(getActivity(), "还没有选择图片", Toast.LENGTH_SHORT).show();
                }
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Log.d(">>> Picker >>>", "dy = " + dy);
                if (Math.abs(dy) > SCROLL_THRESHOLD) {
                    mGlideRequestManager.pauseRequests();
                } else {
                    mGlideRequestManager.resumeRequests();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mGlideRequestManager.resumeRequests();
                }
            }
        });

        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (justCamera) {
            List<Photo> photos = getPhotoGridAdapter().getCurrentPhotos();
            for (int i = 0; i < photos.size(); i++) {
                Photo photo = photos.get(i);
                photo.setSelect(getPhotoGridAdapter().isSelected(photo));
            }
        }
        if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            captureManager.galleryAddPic();
            if (directories.size() > 0) {
                String path = captureManager.getCurrentPhotoPath();
                PhotoDirectory directory = directories.get(MediaStoreHelper.INDEX_ALL_PHOTOS);
                directory.getPhotos().add(MediaStoreHelper.INDEX_ALL_PHOTOS, new Photo(path.hashCode(), path));
                directory.setCoverPath(path);
                photoGridAdapter.notifyDataSetChanged();
                if (justCamera) {
                getPhotoGridAdapter().toggleSelection(getPhotoGridAdapter().getCurrentPhotos().get(0));
                    ((PhotoPickerActivity) getActivity()).onJustCameraResult();
                }
            }
        }
        if (justCamera) {
            ((PhotoPickerActivity) getActivity()).onJustCameraResult();
        }
    }


    public PhotoGridAdapter getPhotoGridAdapter() {
        return photoGridAdapter;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        captureManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        captureManager.onRestoreInstanceState(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    public ArrayList<String> getSelectedPhotoPaths() {
        return photoGridAdapter.getSelectedPhotoPaths();
    }

    public void adjustHeight() {
        if (listAdapter == null) return;
        int count = listAdapter.getCount();
        count = count < COUNT_MAX ? count : COUNT_MAX;
        if (listPopupWindow != null) {
            listPopupWindow.setHeight(count * getResources().getDimensionPixelOffset(R.dimen.__picker_item_directory_height));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (directories == null) {
            return;
        }

        for (PhotoDirectory directory : directories) {
            directory.getPhotoPaths().clear();
            directory.getPhotos().clear();
            directory.setPhotos(null);
        }
        directories.clear();
        directories = null;
    }

    public void doCameraAction(boolean justCamera) {
        this.justCamera = justCamera;
        if (justCamera) {
            try {
                Intent intent = captureManager.dispatchTakePictureIntent();
                startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
