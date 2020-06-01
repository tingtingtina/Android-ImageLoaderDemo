package cn.tina.lib.imageloader;

import android.content.Context;


/*
 * Create by Tina
 * Date: 2018/8/18
 * Description：
 */
public class ImageManager {

    public static ILoader mLoader;

    public static volatile ImageManager instance;

    public static ImageManager instance() {
        if (instance == null) {
            synchronized (ImageManager.class) {
                if (instance == null) {
                    instance = new ImageManager();
                }
            }
        }
        return instance;
    }

    public ILoader getLoader(){
        return mLoader;
    }

    public static void init(ILoader loader){
        mLoader = loader;
    }
}
