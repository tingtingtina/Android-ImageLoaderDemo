package cn.tina.imageloader.glideloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


import cn.tina.imageloader.glideloader.progress.ProgressManager;
import cn.tina.imageloader.glideloader.svg.SvgSoftwareLayerSetter;
import cn.tina.lib.imageloader.ILoader;
import cn.tina.lib.imageloader.ImageController;
import cn.tina.lib.imageloader.OnProgressListener;
import cn.tina.lib.imageloader.ShapeMode;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/*
 * Create by Tina
 * Date: 2018/8/18
 * Description：
 */
public class GlideLoader implements ILoader {

    private ImageController controller;

    @SuppressLint("CheckResult")
    @Override
    public void load(final ImageController controller) {
        this.controller = controller;
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.nb_default_avatar)
//                .error(controller.getErrorResId());
//        GlideApp.with(context)
//                .load(controller.getUrl())
//                .apply(requestOptions)
//                .into((ImageView) controller.getTargetView());
//        GlideApp.with(context)
//                .load(controller.getUrl())
//                .into((ImageView) controller.getTargetView());

        GlideRequests requests = GlideApp.with(controller.getContext());


        GlideRequest request = getRequestBuilder(controller, requests);

        if (request == null) return;

        if (controller.getOWidth() != 0 && controller.getOHeight() != 0) {
            request.override(controller.getOWidth(), controller.getOHeight());
        }
        if (controller.isSetSkipMemoryCache()) {
            request.skipMemoryCache(controller.isSkipMemoryCache());
        }
        request.thumbnail(controller.getThumbnail());

        setPlaceHolder(controller, request);

        setError(controller, request);

        // 圆形ImageView库或者其他的一些自定义的圆形ImageView，并且又刚好设置了占位，第一次加载的是展位图，取消加载动画OK
        // 标准的ImageView 在列表加载时会忽大忽小，去掉动画OK
//        if (controller.getTargetView() instanceof CircleImageView) {
//            request.dontTransform();
//        } else {
            if (controller.isDontTransform()) {
                request.dontTransform();
            } else {
                request.transition(DrawableTransitionOptions.withCrossFade(200));
            }
//        }
        request.dontTransform();
        setOptions(controller, request);


        if (controller.getTargetView() instanceof ImageView) {
            if (controller.isSvg() || controller.getListener() == null) {
                request.into((ImageView) controller.getTargetView());
            } else {
                // TODO: chiemy 2018/10/19 SimpleTarget 已废弃，注释里不建议使用，可以考虑其他方式
                // 使用Target之后，error和placeholder not work
                request.into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        // 判断加载长图
//                    if (resource.getIntrinsicHeight() > DisplayUtils.getScreenHeight(BaseApplication.getAppContext())) {
//                        ((ImageView) controller.getTargetView()).setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    }
                        //添加进度监听
                        if (controller.getListener() != null && getUrl() != null) {
                            ProgressManager.addListener(getUrl(), controller.getListener());
                            request.into(new GlideImageViewTarget((ImageView) controller.getTargetView()));
                        } else {
                            request.into((ImageView) controller.getTargetView());
                        }
                        if (controller.getLoadListener() != null) {
                            controller.getLoadListener().onLoadFinish();
                        }
                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        if (controller.getLoadListener() != null) {
                            controller.getLoadListener().onLoadStart();
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        if (controller.getLoadListener() != null) {
                            controller.getLoadListener().onLoadFailed();
                        }
                    }
                });
            }
        }
    }

    public GlideRequests getImageRequest(Context context) {
        return GlideApp.with(context);
    }

    private GlideRequest getRequestBuilder(ImageController controller, GlideRequests requests) {
        GlideRequest request = null;
        if (controller.isAsBitmap() || controller.isGif()
                || controller.isSvg()) {
            if (controller.isAsBitmap()) {
                request = requests.asDrawable();// 使用asDrawable代替asBitmap，drawable更省内存
            } else if (controller.isGif()) {
                request = requests.asGif();
            } else if (controller.isSvg()) {
                request = requests
                        .as(PictureDrawable.class) //实测，API21+ 可不用添加这这两句
                        .listener(new SvgSoftwareLayerSetter());
            }
            if (request == null) return null;
            if (!TextUtils.isEmpty(getUrl())) {// url
                if (controller.isSvg()) {
                    return request.load(Uri.parse(getUrl()));
                }
                return request.load(getUrl());
            } else if (!TextUtils.isEmpty(controller.getFilePath())) {// load from File
                return request.load(controller.getFilePath());
            } else if (controller.getResId() > 0) {
                return request.load(controller.getResId());
            } else {
                return request;
            }
        } else {
            if (!TextUtils.isEmpty(controller.getFilePath())) {// load from File
                return requests.load(controller.getFilePath());
            } else if (controller.getResId() > 0) {
                return requests.load(controller.getResId());
            } else {//url 或其他
                return requests.load(getUrl());
            }
        }
    }

    private GlideRequest setPlaceHolder(ImageController controller, GlideRequest request) {
        if (controller.getPlaceHolderResId() > 0) {
            return request.placeholder(controller.getPlaceHolderResId());
        } else if (controller.getPlaceHolderColorDrawable() != null) {
            return request.placeholder(controller.getPlaceHolderColorDrawable());
        }
        return request;
    }

    private GlideRequest setError(ImageController controller, GlideRequest request) {
        if (controller.getErrorResId() > 0) {
            return request.error(controller.getErrorResId());
        } else if (controller.getErrorColorDrawable() != null) {
            return request.error(controller.getErrorColorDrawable());
        }
        return request;
    }

    /**
     * 设置图片滤镜和形状
     *
     * @param controller
     * @param request
     */
    private GlideRequest setOptions(ImageController controller, GlideRequest request) {

        GlideOptions options = new GlideOptions();
        int count = 0;

        Transformation[] transformations = new Transformation[getOptionCount(controller)];

        if (transformations.length == 0) {
            return request;
        }
        if (controller.isNeedBlur()) {
            transformations[count] = new BlurTransformation(controller.getBlurRadius(), controller.getBlurSample());
            count++;
        }

        switch (controller.getShapeMode()) {
            case ShapeMode.RECT:

                break;
            case ShapeMode.RECT_ROUND:
                transformations[count] = new RoundedCorners(controller.getRectRoundRadius());
                count++;
                break;
            case ShapeMode.OVAL:
                transformations[count] = new CircleCrop();
                count++;
                break;
            case ShapeMode.SQUARE:
                transformations[count] = new CropSquareTransformation();
                count++;
                break;
        }
//        return request.apply(options.transforms(new CircleCrop()));
        return request.apply(options.transforms(transformations));
    }


    private int getOptionCount(ImageController controller) {
        int count = 0;
        if (controller.getShapeMode() == ShapeMode.OVAL
                || controller.getShapeMode() == ShapeMode.RECT_ROUND
                || controller.getShapeMode() == ShapeMode.SQUARE) {
            count++;
        }

        if (controller.isNeedBlur()) {
            count++;
        }

        return count;
    }

    @Override
    public void resume(Context context) {
        GlideApp.with(context).resumeRequestsRecursive();
    }

    @Override
    public void pause(Context context) {
        GlideApp.with(context).pauseRequestsRecursive();
    }

    @Override
    public void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }

    @Override
    public void clearMemory(Context context) {
        GlideApp.get(context).clearMemory();
    }

    public String getUrl() {
        return controller.getUrl();
    }

    private class GlideImageViewTarget extends DrawableImageViewTarget {
        GlideImageViewTarget(ImageView view) {
            super(view);
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
            super.onLoadStarted(placeholder);
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            OnProgressListener onProgressListener = ProgressManager.getProgressListener(getUrl());
            if (onProgressListener != null) {
                onProgressListener.onProgress(true, 100, 0, 0);
                ProgressManager.removeListener(getUrl());
            }
            super.onLoadFailed(errorDrawable);
        }

        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            OnProgressListener onProgressListener = ProgressManager.getProgressListener(getUrl());
            if (onProgressListener != null) {
                onProgressListener.onProgress(true, 100, 0, 0);
                ProgressManager.removeListener(getUrl());
            }
            super.onResourceReady(resource, transition);
        }
    }
}
