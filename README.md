# ZXUtils
![](https://github.com/StannyBing/ZXUtils/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)<br>
[英文版Readme](https://github.com/StannyBing/ZXUtils/blob/master/README-English.md)<br>


ZXUtils现在已开始逐步加入各种好看的第三方UI控件，全面跨入2.0.0版本。<br>
为更好的使用工具库，仍然采用ZX开头的模式。<br>
从这个版本起，ZXUtils会逐渐加入更多，更全，更好的工具以及UI控件<br>
但同时可能会对原有的部分ui进行删除等操作，望体谅<br>

//2018-7-10<br>
引用：[来自于CymChad的BaseRecyclerViewAdapterHelper,可使用ZXQuickAdapter、ZXMultiItemQucikAdapter等进行使用](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)<br>

//2018-8-22<br>
引用：[来自于mancj的SlideUp-Android,可使用ZXSlideUp、ZXSlideUpBuilder等进行使用](https://github.com/mancj/SlideUp-Android)<br>

如在以上引用中，ZXUtils侵犯了您的权益，请联系我，我将立即移除相关工具，并诚挚道歉。<br>
<br>
大家可点击下面的链接进行下载<br>
[点击下载演示Demo](https://github.com/StannyBing/ZXUtils/blob/master/ZXUtil演示Demo.apk?raw=true)<br>
PS:Demo中的功能只包括了部分功能的展示，由于有些工具的方法太多，就只挑选了部分进行演示<br>
<br>

A commonly used tool library for Android<br>
本库包含常用android开发所需的所有工具类+网络请求+常用view(2.2.0以后移除了网络请求库)<br>
所有的工具等都是以ZX..开头，如ZXDialogUtil、ZXFileUtil、ZXSystemUtil<br>
所有涉及到view的比如自定义view及dialog等都是基于Material Design进行设计，并都提供了自定义的功能，方便设计<br>
<br>
部分截图如下(该截图为2018-8-22所截，后期可能会产生部分变化)：<br>
<div align=center><img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_1.jpg"/>
                  <img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_2.jpg"/></div><br><br>
<div align=center><img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_3.jpg"/>
                  <img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_4.jpg"/></div><br><br>
<div align=center><img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_5.jpg"/>
                  <img width="324" height="576" src="https://github.com/StannyBing/ZXUtils/blob/master/image/Screenshot_6.jpg"/></div><br><br>
### 1.工具库包含以下：<br>
　　ZXHttpTool 网络请求相关<br>
　　ZXAnimUtil 动画方法工具类<br>
　　ZXAppUtil App相关工具类<br>
　　ZXBitmapUtil bitmap处理工具类，包含了drawable等<br>
　　ZXClipboardUtil 剪贴板相关工具类<br>
　　ZXCrashUtil 崩溃相关工具类<br>
　　ZXDataBaseUtil 数据库工具<br>
　　ZXDialogUtil 专用于dialog的工具类<br>
　　ZXFileUtil 文件相关util<br>
　　ZXFormatCheckUtil 格式检测工具类<br>
　　ZXFragmentUtil Fragment相关工具类<br>
　　ZXImageLoaderUtil 图片加载工具类（基于Glide封装）<br>
　　ZXIntentUtil Intent相关工具<br>
　　ZXJsonUtil json处理工具<br>
　　ZXLogUtil Log工具类<br>
　　ZXMD5Util MD5加密和编码工具<br>
　　ZXNetWorkUtil 网络相关工具类<br>
　　ZXnotifyUtil 通知栏工具<br>
　　ZXPermissionUtil 权限申请工具类<br>
　　ZXPinyinUtil 拼音相关工具类<br>
　　ZXProcessUtil 进程相关工具类<br>
　　ZXRegexUtil 正则相关工具类(使用正则检查手机号等)<br>
　　ZXScreenUtil 屏幕相关工具类<br>
　　ZXServiceUtil 服务相关工具类<br>
　　ZXSharedPreUtil 轻量存储的工具类<br>
　　ZXStringUtil 字符串工具类<br>
　　ZXSystemUtil 系统相关工具类<br>
　　ZXTimeUtil 日期工具类<br>
　　ZXToastUtil 专用于Toast及SnackBar的工具类<br>
　　ZXUniqueIdUtil 唯一码工具类<br>
　　ZXUnZipRarUtil 解压工具<br>
　　ZXWindowUtil 窗口工具类<br>
　　ZXRecordUtil 录音及播放类<br>
　　ZXDeviceUtil 录音及播放类<br>
　　ZXQuickAdapter 快速封装适配器<br>
　　ZXLightUtil 亮度控制工具<br>
　　ZXLocationUtil 定位相关工具<br>
<br>
### 2.其他相关工具<br>
　　ZXThreadPool 线程池工具，用于建立线程池<br>
　　ZXBroadCastManager 封装广播broadcastreceiver<br>
　　ZXInScrollRecylerManager 可以嵌套进ScrollView中的Recylerview的Manager<br>
　　ZXItemClickSupport recylerview的点击事件，不需要再adapter中再适配监听器<br>
　　ZXOnDoubleClickListener 双击事件监听器<br>
  <br>
### 3.view<br>
　　ZXBubbleView 气泡弹出框view<br>
　　ZXSeekBar 带刻度的Seekbar<br>
　　ZXBarChar、ZXLineChart、ZXPieChart 统计图相关<br>
　　ZXPhotoPickerView 仿微信的图片选择器<br>
　　ZXRecyclerDeleteHelper recycler的左滑删除工具<br>
　　ZXSlidingRootNavBuilder 侧边栏工具<br>
　　ZXSwipeBackHelper 滑动退出工具<br>
　　ZXSwipeRecycler SwipeRefreshLayout+RecyclerView的组合view<br>
　　ZXTableView 表格view<br>
　　ZXTabViewPager TabLayout+ViewPager的组合view<br><br>
　　ZXNoScrollGridView 不会滚动的GridView<br>
　　ZXNoScrollListView 不会滚动的ListView<br>
　　ZXPhotoView 可放大缩小的图片控件<br>
　　ZXSpinner Spinnerview<br>
　　ZXStatusBarCompat 透明状态栏<br>
　　ZXCameraView 仿微信录像照相<br>
　　ZXSlideUp 滑动控件工具<br>
　　ZXShadowLayout 阴影效果<br>
  <br>
### 4.使用方法<br>

    先导入依赖包<br>
    
    ```
    implementation 'com.github.StannyBing:ZXUtils:2.3.9'
    如AS提示依赖冲突，可以使用以下方式
    implementation ('com.github.StannyBing:ZXUtils:2.3.9'){
            exclude group: 'com.android.support'
            exclude group: 'com.squareup.okhttp3'
        }
    ZXUtils中集成了okhttp3，创建了简易的网络请求工具，如果您项目中已使用，可以排除掉
    In zxutils, okhttp3 is integrated and a simple network request tool is created. If you have used it in your project, you can exclude it
    ```
    
    注意需要在项目的build里面添加以下代码<br>
    
    ```
    maven {
            url "https://jitpack.io"
        }
    ```

　　在你的Application的onCreate中添加以下代码<br>
　　`ZXApp.init(this, true);`<br>
　　第二个参数是指是否为debug模式，如果为false，将停止工具类中的log输出，同时开始线程崩溃处理工具<br>
　　为true则代表此时为debug模式，如果遇到崩溃将直接抛出，方便定位错误。<br>
