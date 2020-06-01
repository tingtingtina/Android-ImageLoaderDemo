# ImageLoader
对图片库进行封装，配合图片加载库使用

## API

|Builder |Controller|
|----|----|
|with(this)   |getContext():Context|
|url("url")           |getUrl(),isSvg(), isGif()|
|res(R.mipmap.ic_launcher)                  |getFilePath()|
|file("filePath")                           |getResId()|
|placeHolder(new ColorDrawable(Color.RED))  |getPlaceHolderRColorDrawable()|
|placeHolder(R.mipmap.ic_launcher_round)    |getPlaceHodlerResId()|
|error(new ColorDrawable(Color.RED))        |getErrorColorDrawable()|
|error(R.mipmap.ic_launcher_round)          |getErrorResId()|
|asBitmap()                                 |isAsBitmap()|
|asCircle()                                 |getShapeMode() == OVAL|
|asSquare()                                 |getShapeMode() == SQUARE|
|rectRoundCorner(8)                         |getShapeMode() == RECT_ROUND，getRecRoundRadius()|
|blur(20, 2)  |isNeedBlur(),getBlurRadius(),getBlurSample()|
|dontTransform()                            |isDontTransForm()|
|override(300, 300)                         |getOwidth(),getOHeigh()|
|skipMemoryCache(true)                      |isSkipMemoryCache(), isSetSkipMemoryCache()|
|thumbnail(0.1f)                            |getThumbnail()|
|listener(onLoadListener)                   |getLoadListener()|
|listener(onProgressListener)               |getListener()|
|into(imageView);                           |getTargetView()|

## Usage
1. 实现ILoader，根据ImageController中属性，设置加载图片
2. 在使用前调用
```
ImageManager.instance().init(ILoader loader);
```

可设属性如下
```java
ImageManager.init(new GlideLoader());
ImageView imageView = findViewById(R.id.imageView);
ImageLoader
    .with(this)
    .url("url")                                 //设置网络路径
    .res(R.mipmap.ic_launcher)                  // 加载Drawable资源
    .file("filePath")                           // 加载文件(文件路径)
    .placeHolder(new ColorDrawable(Color.RED))  // 设置占位图(ColorDrawable)
    .placeHolder(R.mipmap.ic_launcher_round)    // 设置占位图 (资源id)
    .error(new ColorDrawable(Color.RED))        // 设置错误图 (ColorDrawable)
    .error(R.mipmap.ic_launcher_round)          // 设置错误图 (资源id)
    .asBitmap()                                 // 加载静态图
    .asCircle()                                 // 加载圆形图片
    .asSquare()                                 // 正方形
    .rectRoundCorner(8)                         // 圆角矩形(圆角半径)
    .blur(20, 2)                                // 模糊(模糊半径，模糊采样)
    .dontTransform()                            // 不加载动画
    .override(300, 300)                         // 指定加载图片分辨率(宽，高)
    .skipMemoryCache(true)                      // 是否跳过内存缓存
    .thumbnail(0.1f)                            // 设置缩略图(缩略比例)
    .listener(onLoadListener)                   // 图片加载监听(OnLoadListener)
    .listener(onProgressListener)               // 图片加载进度监听(OnProgressListener)
    .into(imageView);                           // 最后加载到指定View中
```
OnLoadeListener 包含三个方法
```java
public interface OnLoadListener {
    void onLoadStart(); //开始加载

    void onLoadFailed();//加载失败

    void onLoadFinish();//加载完成
}
```

```java
public interface OnProgressListener {
    /**
     * 
     * @param isComplete  是否加载完成
     * @param percentage  当前百分比
     * @param bytesRead   已加载数据
     * @param totalBytes  总需要加载数据
     */
    void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes);
}
```

## 效果
https://s33.aconvert.com/convert/p3r68-cdx67/rnxc4-n1sb0.gif
