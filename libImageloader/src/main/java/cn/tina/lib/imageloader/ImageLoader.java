package cn.tina.lib.imageloader;

import android.content.Context;

/*
 * Create by Tina
 * Date: 2018/8/17
 * Description：
 */
public class ImageLoader {
    /**
     * 加载图片
     * @param context
     * @return
     */
    public static ImageController.Builder with(Context context){
        return new ImageController.Builder(context);
    }
}
