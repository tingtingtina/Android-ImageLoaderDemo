package cn.tina.lib.imageloader;

/**
 * @author by sunfusheng on 2017/6/14.
 */
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
