package cn.tina.imageloader.glideloader;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import cn.tina.imageloader.glideloader.progress.ProgressManager;


/*
 * Create by Tina
 * Date: 2018/8/18
 * Description：
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
//        registry.register(SVG.class, PictureDrawable.class, new SvgDrawableTranscoder())
//                .append(InputStream.class, SVG.class, new SvgDecoder()); // 0422 由于iOS不支持svg Android 注释掉此功能
    }
    // Disable manifest parsing to avoid adding similar modules twice.
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

//    @Override
//    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//        super.applyOptions(context, builder);
//        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context) .setMemoryCacheScreens(2) .build();
//        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
//        int diskCacheSizeBytes = 1024 * 1024 * 500; // 500 MB
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
//    }
}
