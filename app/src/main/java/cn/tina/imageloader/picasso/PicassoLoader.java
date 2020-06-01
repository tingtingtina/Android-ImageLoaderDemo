package cn.tina.imageloader.picasso;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import cn.tina.lib.imageloader.ILoader;
import cn.tina.lib.imageloader.ImageController;

/*
 * Create by Tina
 * Date: 2019/6/12
 * Description：
 */
public class PicassoLoader implements ILoader {
    ImageController controller;

    @Override
    public void load(ImageController controller) {
        this.controller = controller;
//        Picasso.with(controller.getContext())
//                .load(controller.getUrl())
//                .into((ImageView) controller.getTargetView());

        Picasso picasso = Picasso.with(controller.getContext());


        RequestCreator requestCreator = picasso.load(controller.getUrl());

        setPlaceHolder(controller, requestCreator);

        setError(controller, requestCreator);

        if (controller.isDontTransform()){
            requestCreator.noFade();
        }

        if (controller.getOWidth() != 0 && controller.getOHeight() != 0) {
            requestCreator.resize(controller.getOWidth(), controller.getOHeight());
        }

        if (controller.getTargetView() instanceof ImageView) {
            requestCreator.into((ImageView) controller.getTargetView());
        }
    }


    private RequestCreator setPlaceHolder(ImageController controller, RequestCreator request) {
        if (controller.getPlaceHolderResId() > 0) {
            return request.placeholder(controller.getPlaceHolderResId());
        } else if (controller.getPlaceHolderColorDrawable() != null) {
            return request.placeholder(controller.getPlaceHolderColorDrawable());
        }
        return request;
    }

    private RequestCreator setError(ImageController controller, RequestCreator request) {
        if (controller.getErrorResId() > 0) {
            return request.error(controller.getErrorResId());
        } else if (controller.getErrorColorDrawable() != null) {
            return request.error(controller.getErrorColorDrawable());
        }
        return request;
    }

    @Override
    public void pause(Context context) {

    }

    @Override
    public void resume(Context context) {

    }

    @Override
    public void clearDiskCache(Context context) {

    }

    @Override
    public void clearMemory(Context context) {

    }
}
