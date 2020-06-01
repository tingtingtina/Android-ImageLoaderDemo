package cn.tina.imageloader;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import cn.tina.imageloader.glideloader.GlideLoader;
import cn.tina.imageloader.picasso.PicassoLoader;
import cn.tina.lib.imageloader.ImageLoader;
import cn.tina.lib.imageloader.ImageManager;
import cn.tina.lib.imageloader.OnLoadListener;
import cn.tina.lib.imageloader.OnProgressListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageManager.init(new GlideLoader());
//        ImageManager.init(new PicassoLoader());
        ImageView imageView = findViewById(R.id.imageView);
        ImageLoader
                .with(this)
                .url("https://github.com/tingtingtina/wxPic/blob/master/%E4%B8%8D%E8%AF%B4%E5%86%8D%E8%A7%81.jpg?raw=true") //设置网络路径
                .placeHolder(R.mipmap.ic_launcher_round)    // 设置占位图 (资源id)
                .error(new ColorDrawable(Color.RED))        // 设置错误图 (ColorDrawable)
                .into(imageView);                          // 最后加载到指定View中
    }

    public void apiRef(){
        ImageView imageView = new ImageView(this);
        ImageLoader
                .with(this)
                .url("https://github.com/tingtingtina/wxPic/blob/master/%E4%B8%8D%E8%AF%B4%E5%86%8D%E8%A7%81.jpg?raw=true") //设置网络路径
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
                .blur(20, 2)        // 模糊(模糊半径，模糊采样)
                .dontTransform()                            // 不加载动画
                .override(300, 300)       //指定加载图片分辨率(宽，高)
                .skipMemoryCache(true)                      // 是否跳过内存缓存
                .thumbnail(0.1f)                            // 设置缩略图比例
                .listener(onLoadListener)                   // 图片加载监听(OnLoadListener)
                .listener(onProgressListener)               // 图片加载进度监听(OnProgressListener)
                .into(imageView);                          // 最后加载到指定View中
    }

    OnLoadListener onLoadListener = new OnLoadListener() {
        @Override
        public void onLoadStart() {

        }

        @Override
        public void onLoadFailed() {

        }

        @Override
        public void onLoadFinish() {

        }
    };

    OnProgressListener onProgressListener = new OnProgressListener() {
        @Override
        public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {

        }
    };
}