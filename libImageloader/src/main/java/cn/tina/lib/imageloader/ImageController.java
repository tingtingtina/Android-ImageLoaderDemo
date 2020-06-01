package cn.tina.lib.imageloader;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;
import android.view.View;

import java.io.File;

/*
 * Create by Tina
 * Date: 2018/8/17
 * Description：
 */
public class ImageController {

    @StringDef({
            ImageType.GIF,
            ImageType.SVG,
            ImageType.OTHER
    })
    public @interface ImageType {
        String GIF = "gif";
        String SVG = "svg";
        String OTHER = "other";
    }

    private Context context;
    private String url;
    private float thumbnail;
    private String filePath;
    private File file;
    private int resId;
    private String rawPath;
    private String assertspath;
    private String contentProvider;
    private String imageType;

    private View targetView;
    private boolean asBitmap;//只获取bitmap

    private int placeHolderResId;
    private ColorDrawable placeHolderColorDrawable;

    private int errorResId;//请求失败时展示的资源
    private ColorDrawable errorColorDrawable;

    private int width;
    private int height;

    private int oWidth; //选择加载分辨率的宽
    private int oHeight; //选择加载分辨率的高

    private boolean isSkipMemoryCache;
    private boolean isSetSkipMemoryCache;

    private boolean isDontTransform;// 不用过渡动画

    //滤镜
    private boolean needBlur = false;//是否需要模糊
    private int blurRadius;
    private int blurSample;

    private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
    private int rectRoundRadius;//圆角矩形时圆角的半径

    private OnProgressListener listener; // 进度监听
    private OnLoadListener loadListener; // 加载监听

    public ImageController(Builder builder) {
        this.context = builder.context;
        this.targetView = builder.targetView;
        this.url = builder.url;
        this.filePath = builder.filePath;
        this.resId = builder.resId;
        this.placeHolderResId = builder.placeHolderResId;
        this.placeHolderColorDrawable = builder.placeHolderColorDrawable;
        this.errorResId = builder.errorResId;
        this.errorColorDrawable = builder.errorColorDrawable;
        this.imageType = builder.imageType;
        this.asBitmap = builder.asBitmap;
        this.shapeMode = builder.shapeMode;
        if (shapeMode == ShapeMode.RECT_ROUND) {
            this.rectRoundRadius = builder.rectRoundRadius;
        }
        this.oWidth = builder.oWidth;
        this.oHeight = builder.oHeight;
        this.isSkipMemoryCache = builder.isSkipMemoryCache;
        this.isSetSkipMemoryCache = builder.isSetSkipMemoryCache;
        this.isDontTransform = builder.isDontTransform;
        this.listener = builder.listener;
        this.loadListener = builder.loadListener;
        this.thumbnail = builder.thumbnail;
        this.needBlur = builder.needBlur;
        this.blurRadius = builder.blurRadius;
        this.blurSample = builder.blurSample;
    }

    private void show() {
        ImageManager.instance().getLoader().load(this);
    }

    public Context getContext() {
        return context;
    }

    public View getTargetView() {
        return targetView;
    }

    public String getUrl() {
        return url;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getResId() {
        return resId;
    }

    public int getPlaceHolderResId() {
        return placeHolderResId;
    }

    public ColorDrawable getPlaceHolderColorDrawable() {
        return placeHolderColorDrawable;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public ColorDrawable getErrorColorDrawable() {
        return errorColorDrawable;
    }

    public int getRectRoundRadius() {
        return rectRoundRadius;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public boolean isGif() {
        return imageType.equals(ImageType.GIF);
    }

    public boolean isSvg() {
        return imageType.equals(ImageType.SVG);
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public int getOWidth() {
        return oWidth;
    }

    public int getOHeight() {
        return oHeight;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public boolean isSetSkipMemoryCache() {
        return isSetSkipMemoryCache;
    }

    public boolean isDontTransform() {
        return isDontTransform;
    }

    public boolean isNeedBlur() {
        return needBlur;
    }

    public int getBlurRadius() {
        return blurRadius;
    }

    public int getBlurSample() {
        return blurSample;
    }

    public int getShapeMode() {
        return shapeMode;
    }

    public OnProgressListener getListener() {
        return listener;
    }

    public OnLoadListener getLoadListener() {
        return loadListener;
    }

    public static class Builder {
        private Context context;
        /**
         * 图片源
         * 类型	SCHEME	示例
         * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
         * 本地文件	file://	FileInputStream
         * Content provider	content://	ContentResolver
         * asset目录下的资源	asset://	AssetManager
         * res目录下的资源	  res://	Resources.openRawResource
         * Uri中指定图片数据	data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)
         *
         * @param config
         * @return
         */
        private String url;
        private float thumbnail;
        private String filePath;
        private File file;
        private int resId;
        private String rawPath;
        private String assertspath;
        private String contentProvider;
        private String imageType = ImageType.OTHER;

        private View targetView;
        private boolean asBitmap;//只获取bitmap

        // TODO: 2017/4/24 宽高的获取
        private int width;
        private int height;

        private int oWidth; //选择加载分辨率的宽
        private int oHeight; //选择加载分辨率的高

        private boolean isSkipMemoryCache;
        private boolean isSetSkipMemoryCache;
        private boolean isDontTransform;
        //滤镜
        private boolean needBlur = false;//是否需要模糊
        private int blurRadius;
        private int blurSample;

        private int placeHolderResId;
        private ColorDrawable placeHolderColorDrawable;

        private int errorResId;//请求失败时展示的资源
        private ColorDrawable errorColorDrawable;

        private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
        private int rectRoundRadius;//圆角矩形时圆角的半径
        private OnProgressListener listener; // 进度监听
        private OnLoadListener loadListener; // 加载监听

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置网络路径
         *
         * @param url 网络图片路径
         * @return
         */
        public Builder url(String url) {
            this.url = url;
            if (url != null && url.endsWith(".gif")) {
                imageType = ImageType.GIF;
            } else if (url != null && url.endsWith(".svg")) {
                imageType = ImageType.SVG;
            } else {
                imageType = ImageType.OTHER;
            }
            return this;
        }

        /**
         * 加载SD卡资源
         *
         * @param filePath 文件路径
         * @return
         */
        public Builder file(@NonNull String filePath) {
            if (filePath.startsWith("file:")) {
                this.filePath = filePath;
                return this;
            }

            if (!new File(filePath).exists()) {
                Log.e("imageloader", "文件不存在");
                return this;
            }

            this.filePath = filePath;
            if (filePath.endsWith("gif")) {
                imageType = ImageType.GIF;
            } else if (filePath.endsWith(".svg")) {
                imageType = ImageType.SVG;
            } else {
                imageType = ImageType.OTHER;
            }
            return this;
        }

        /**
         * 加载drawable资源
         *
         * @param resId 资源Id
         * @return
         */
        public Builder res(int resId) {
            this.resId = resId;
            return this;
        }

        /**
         * 加载静态图
         */
        public Builder asBitmap() {
            this.asBitmap = true;
            return this;
        }

        /**
         * 指定加载图片的分辨率
         *
         * @param oWidth  图片宽度
         * @param oHeight 图片高度
         * @return
         */
        public Builder override(int oWidth, int oHeight) {
            this.oWidth = oWidth;
            this.oHeight = oHeight;
            return this;
        }

        /**
         * 设置占位图
         *
         * @param placeHolderResId 占位图资源Id
         * @return
         */
        public Builder placeHolder(int placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        /**
         * 设置占位图
         *
         * @param colorDrawable
         * @return
         */
        public Builder placeHolder(ColorDrawable colorDrawable) {
            this.placeHolderColorDrawable = colorDrawable;
            return this;
        }

        /**
         * 设置error图
         *
         * @param errorResId error资源Id
         * @return
         */
        public Builder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        /**
         * 设置error图
         *
         * @param errorColorDrawable
         * @return
         */
        public Builder error(ColorDrawable errorColorDrawable) {
            this.errorColorDrawable = errorColorDrawable;
            return this;
        }

        /**
         * 是否跳过内存缓存功能，默认开启
         * 传入true，就表示禁用掉Glide的内存缓存功能。
         *
         * @param skip 是否跳过内存缓存功能
         * @return
         */
        public Builder skipMemoryCache(boolean skip) {
            isSetSkipMemoryCache = true;
            this.isSkipMemoryCache = skip;
            return this;
        }

        /**
         * @return
         */
        public Builder dontTransform() {
            isDontTransform = true;
            return this;
        }

        /**
         * 是否需要高斯模糊
         *
         * @return
         */
        public Builder blur(int blurRadius, int blurSample) {
            this.needBlur = true;
            this.blurRadius = blurRadius;
            this.blurSample = blurSample;
            return this;
        }

        /**
         * 圆形
         *
         * @return
         */
        public Builder asCircle() {
            this.shapeMode = ShapeMode.OVAL;
            return this;
        }

        /**
         * 形状为圆角矩形时的圆角半径
         *
         * @param rectRoundRadius
         * @return
         */
        public Builder rectRoundCorner(int rectRoundRadius) {
            this.rectRoundRadius = rectRoundRadius;
            this.shapeMode = ShapeMode.RECT_ROUND;
            return this;
        }

        /**
         * 正方形
         *
         * @return
         */
        public Builder asSquare() {
            this.shapeMode = ShapeMode.SQUARE;
            return this;
        }

        /**
         * 设置进度监听
         *
         * @param listener
         * @return
         */
        public Builder listener(OnProgressListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 设置加载监听
         *
         * @param listener
         * @return
         */
        public Builder listener(OnLoadListener listener) {
            this.loadListener = listener;
            return this;
        }

        /**
         * 设置缩略图比例
         *
         * @param thumbnail
         * @return
         */
        public Builder thumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public void into(View targetView) {
            this.targetView = targetView;
            new ImageController(this).show();
        }
    }
}
