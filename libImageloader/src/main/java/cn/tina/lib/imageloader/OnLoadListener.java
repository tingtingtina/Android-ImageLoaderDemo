package cn.tina.lib.imageloader;

public interface OnLoadListener {
    void onLoadStart();

    void onLoadFailed();

    void onLoadFinish();
}
