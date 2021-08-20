package com.zx.zxutils.views.PhotoPicker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.views.PhotoPicker.fragment.ImagePagerFragment;
import com.zx.zxutils.views.PhotoPicker.widget.ZXPhotoPickerView;
import com.zx.zxutils.views.PhotoPicker.widget.Titlebar;

import java.util.List;


/**
 * Created by donglua on 15/6/24.
 */
public class PhotoPagerActivity extends AppCompatActivity {

    private ImagePagerFragment pagerFragment;

    //private ActionBar actionBar;
    private boolean showDelete;
    private Titlebar titlebar;
    private String viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.__picker_activity_photo_pager);


        viewId = getIntent().getStringExtra("id");
        int currentItem = getIntent().getIntExtra(ZXPhotoPreview.EXTRA_CURRENT_ITEM, 0);
        List<String> paths = getIntent().getStringArrayListExtra(ZXPhotoPreview.EXTRA_PHOTOS);
        showDelete = getIntent().getBooleanExtra(ZXPhotoPreview.EXTRA_SHOW_DELETE, true);
        int action = getIntent().getIntExtra(ZXPhotoPreview.EXTRA_ACTION, ZXPhotoPickerView.ACTION_ONLY_SHOW);

        if (pagerFragment == null) {
            pagerFragment =
                    (ImagePagerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPagerFragment);
        }
        pagerFragment.setPhotos(paths, currentItem);
        titlebar = (Titlebar) findViewById(R.id.titlebar);
        titlebar.init(this);
        if (action == ZXPhotoPickerView.ACTION_SELECT) {
            titlebar.setLeftOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Fragment> fragments = getSupportFragmentManager().getFragments();
                    for (int i = 0; i < fragments.size(); i++) {
                        if (fragments.get(i) instanceof ImagePagerFragment) {
                            onBackPressed();
                            return;
                        }
                    }
                    finish();
                }
            });
            if (!showDelete) {
                titlebar.getIvRight().setVisibility(View.GONE);
            } else {
                titlebar.setRitht(getApplicationContext().getResources().getDrawable(R.drawable.__picker_delete), "", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = pagerFragment.getViewPager().getCurrentItem();
                        if (pagerFragment.getPaths().size() > 0) {
                            pagerFragment.getPaths().remove(position);
                            pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
                            if (pagerFragment.getPaths().size() == 0) {
                                titlebar.setTitle(getString(R.string.__picker_preview) + " " + getString(R.string.__picker_image_index, 0,
                                        pagerFragment.getPaths().size()));
                            }

                        }


                    }
                });
            }
        }

        titlebar.setTitle(getString(R.string.__picker_preview));


    /*Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);

    actionBar = getSupportActionBar();

   // centerActionBarTitle(this);

    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      updateActionBarTitle();
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        actionBar.setElevation(25);
      }
    }*/


        pagerFragment.getViewPager().addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                titlebar.setTitle(getString(R.string.__picker_preview) + " " + getString(R.string.__picker_image_index, pagerFragment.getViewPager().getCurrentItem() + 1,
                        pagerFragment.getPaths().size()));
                // updateActionBarTitle();
            }
        });
    }

    //把actionBar的文字标题居中
    public static void centerActionBarTitle(Activity activity) {
        int titleId = activity.getResources().getIdentifier("action_bar_title", "id", "android");
        if (titleId <= 0) return;
        TextView titleTextView = (TextView) activity.findViewById(titleId);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        LinearLayout.LayoutParams txvPars = (LinearLayout.LayoutParams) titleTextView.getLayoutParams();
        txvPars.gravity = Gravity.CENTER_HORIZONTAL;
        txvPars.width = metrics.widthPixels;
        titleTextView.setLayoutParams(txvPars);
        titleTextView.setGravity(Gravity.CENTER);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showDelete) {
            getMenuInflater().inflate(R.menu.__picker_menu_preview, menu);
        }
        return true;
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra(PhotoPicker.KEY_SELECTED_PHOTOS, pagerFragment.getPaths());
        intent.putExtra("id", viewId);
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (item.getItemId() == R.id.delete) {
            final int index = pagerFragment.getCurrentItem();

            final String deletedPath = pagerFragment.getPaths().get(index);

            Snackbar snackbar = Snackbar.make(pagerFragment.getView(), R.string.__picker_deleted_a_photo,
                    Snackbar.LENGTH_LONG);

            if (pagerFragment.getPaths().size() <= 1) {

                // show confirm dialog
                new AlertDialog.Builder(this)
                        .setTitle(R.string.__picker_confirm_to_delete)
                        .setPositiveButton(R.string.__picker_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                pagerFragment.getPaths().remove(index);
                                pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(R.string.__picker_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

            } else {

                snackbar.show();

                pagerFragment.getPaths().remove(index);
                pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
            }

            snackbar.setAction(R.string.__picker_undo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pagerFragment.getPaths().size() > 0) {
                        pagerFragment.getPaths().add(index, deletedPath);
                    } else {
                        pagerFragment.getPaths().add(deletedPath);
                    }
                    pagerFragment.getViewPager().getAdapter().notifyDataSetChanged();
                    pagerFragment.getViewPager().setCurrentItem(index, true);
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

 /* public void updateActionBarTitle() {
    if (actionBar != null) actionBar.setTitle(
        getString(R.string.__picker_image_index, pagerFragment.getViewPager().getCurrentItem() + 1,
            pagerFragment.getPaths().size()));
  }*/
}
