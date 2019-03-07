# ZXUtils
![](https://github.com/StannyBing/ZXUtils/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)<br>
[Chinese Readme](https://github.com/StannyBing/ZXUtils)<br>

ZXUtils has now begun to gradually add a variety of good-looking third-party UI controls to fully integrate into version 2.0.0.<br>
For better use of the tool library, the model starting with ZX is still used.<br>
From this version, ZXUtils will gradually add more, more complete, better tools and UI controls.<br>
But at the same time, it may delete the original part of the ui, etc.<br>

//2018-7-10<br>
Reference：[BaseRecyclerViewAdapterHelper from CymChad, can be used with ZXQuickAdapter, ZXMultiItemQucikAdapter, etc.](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)<br>

//2018-8-22<br>
Reference：[SlideUp-Android from mancj, which can be used with ZXSlideUp, ZXSlideUpBuilder, etc.](https://github.com/mancj/SlideUp-Android)<br>

As mentioned in the above quote, ZXUtils infringes your rights, please contact me, I will immediately remove the relevant tools and sincerely apologize.<br>

<br>
You can download it by clicking the link below.<br>
[Click to download Demo Apk](https://github.com/StannyBing/ZXUtils/blob/master/ZXUtil演示Demo.apk?raw=true)<br>
PS: The functions in Demo only include some functions. Because there are too many methods in some tools, only some of them are selected for demonstration.<br>
<br>
A commonly used tool library for Android<br>
This library contains all the tools required for common android development + network request + common view(after the 2.2.0, the network request library was removed)<br>
All tools, etc. start with ZX.., such as ZXDialogUtil, ZXFileUtil, ZXSystemUtil<br>
All the views, such as custom views and dialogs, are designed based on Material Design and all provide custom functions for easy design.<br>
<br>
Some screenshots are as follows (this screenshot is cut by 2018-8-22, and some changes may occur later):<br>
<div align=center><img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_1.jpg"/>
                  <img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_2.jpg"/></div><br><br>
<div align=center><img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_3.jpg"/>
                  <img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_4.jpg"/></div><br><br>
<div align=center><img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_5.jpg"/>
                  <img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_6.jpg"/></div><br><br>
### 1.The tool library contains the following：<br>
　　ZXAnimUtil    Animation method tool class<br>
　　ZXAppUtil    App related tools<br>
　　ZXBitmapUtil    Bitmap processing tool class, including drawable, etc.<br>
　　ZXClipboardUtil    Clipboard related tools<br>
　　ZXCrashUtil    Crash related tools<br>
　　ZXDataBaseUtil    Database tool<br>
　　ZXDialogUtil    Tool class dedicated to dialog<br>
　　ZXFileUtil    File related util<br>
　　ZXFormatCheckUtil    Format detection tool class<br>
　　ZXFragmentUtil    Fragment related tools<br>
　　ZXImageLoaderUtil    Image loading tool class (based on Glide package)<br>
　　ZXIntentUtil    Intent related tools<br>
　　ZXJsonUtil    Json processing tool<br>
　　ZXLogUtil    Log tool class<br>
　　ZXMD5Util    MD5 encryption and encoding tools<br>
　　ZXNetWorkUtil    Network related tools<br>
　　ZXnotifyUtil    Notification bar tool<br>
　　ZXPermissionUtil    Permission application tool class<br>
　　ZXPinyinUtil    Pinyin related tools<br>
　　ZXProcessUtil    Process related tools<br>
　　ZXRegexUtil    Regular related tool class (use regular check mobile phone number, etc.)<br>
　　ZXScreenUtil    Screen related tools<br>
　　ZXServiceUtil    Service related tools<br>
　　ZXSharedPreUtil    Lightweight storage tools<br>
　　ZXStringUtil    String tool class<br>
　　ZXSystemUtil    System related tools<br>
　　ZXTimeUtil    Date tool class<br>
　　ZXToastUtil    Tools for Toast and SnackBar<br>
　　ZXUniqueIdUtil    Unique code tool class<br>
　　ZXUnZipRarUtil    Decompression tool<br>
　　ZXWindowUtil    Window tool class<br>
　　ZXRecordUtil    Recording and playback<br>
　　ZXDeviceUtil    Recording and playback<br>
　　ZXQuickAdapter    Fast package adapter<br>
　　ZXLightUtil   Brightness related tool<br>
　　ZXLocationUtil   Location related tool<br>
<br>
### 2.Other related tools<br>
　　ZXThreadPool    Thread pool tool for building thread pools<br>
　　ZXBroadCastManager    Package broadcast broadcastreceiver<br>
　　ZXInScrollRecylerManager    Can be nested into the Manager of Recylerview in the ScrollView<br>
　　ZXItemClickSupport    Recylerview click event, no need to adapt the listener in the adapter<br>
　　ZXOnDoubleClickListener    Double click event listener<br>
  <br>
### 3.view<br>
　　ZXBubbleView    Bubble pop-up view<br>
　　ZXSeekBar    Scaled Seekbar<br>
　　ZXBarChar、ZXLineChart、ZXPieChart    Statistical graph correlation<br>
　　ZXPhotoPickerView    WeChat picture selector<br>
　　ZXRecyclerDeleteHelper    Recycler left slide removal tool<br>
　　ZXSlidingRootNavBuilder    Sidebar tool<br>
　　ZXSwipeBackHelper    Slide exit tool<br>
　　ZXSwipeRecycler    Combination view of SwipeRefreshLayout+RecyclerViewbr>
　　ZXTableView    Form view<br>
　　ZXTabViewPager    TabVIEW+ViewPager combination view<br>
　　ZXNoScrollGridView    GridView that won't scroll<br>
　　ZXNoScrollListView    ListView that won't scroll<br>
　　ZXPhotoView    Zoom in and out of the picture control<br>
　　ZXSpinner    Spinnerview<br>
　　ZXStatusBarCompat    Transparent status bar<br>
　　ZXCameraView    Imitation WeChat video camera<br>
　　ZXSlideUp    Sliding control tool<br>
　　ZXShadowLayout    Shadow effect<br>
  <br>
### 4.Instructions<br>

    Import dependency packages first<br>
    
    ```
    compile 'com.github.StannyBing:ZXUtils:2.2.3'
    ```
    
    Note that you need to add the following code to the project's build.<br>
    
    ```
    maven {
            url "https://jitpack.io"
        }
    ```

　　Add the following code to your application's onCreate<br>
  `ZXApp.init(this, true);`<br>
  The second parameter refers to whether it is the debug mode. If it is false, it will stop the log output in the tool class and start the thread crash processing tool.<br>
  True means that this is the debug mode. If you encounter a crash, it will be thrown directly, which is convenient for positioning errors. <br>
