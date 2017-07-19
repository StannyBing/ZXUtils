package com.zx.zxutils.views.PhotoPicker.utils;

import android.content.Intent;

import com.zx.zxutils.views.PhotoPicker.PhotoPicker;

import java.util.ArrayList;


/**
 * Created by donglua on 15/7/2.
 */
@Deprecated
public class PhotoPickerIntent {
  public static void setPhotoCount(Intent intent, int photoCount) {
    intent.putExtra(PhotoPicker.EXTRA_MAX_COUNT, photoCount);
  }

  public static void setShowCamera(Intent intent, boolean showCamera) {
    intent.putExtra(PhotoPicker.EXTRA_SHOW_CAMERA, showCamera);
  }

  public static void setShowGif(Intent intent, boolean showGif) {
    intent.putExtra(PhotoPicker.EXTRA_SHOW_GIF, showGif);
  }

  public static void setColumn(Intent intent, int column) {
    intent.putExtra(PhotoPicker.EXTRA_GRID_COLUMN, column);
  }

  /**
   * To set some photos that have been selected before
   * @param intent
   * @param imagesUri Selected photos
     */
  public static void setSelected(Intent intent, ArrayList<String> imagesUri) {
    intent.putExtra(PhotoPicker.EXTRA_ORIGINAL_PHOTOS, imagesUri);
  }
}
