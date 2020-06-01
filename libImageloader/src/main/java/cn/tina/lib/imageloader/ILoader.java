package cn.tina.lib.imageloader;

import android.content.Context;

/*
 * Create by Tina
 * Date: 2018/8/18
 * Description：
 */
public interface ILoader {

    //加载图片
    void load(ImageController controller);

    void pause(Context context);

    void resume(Context context);

    void clearDiskCache(Context context);

    void clearMemory(Context context);
}
