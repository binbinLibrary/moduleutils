package com.module.linrary.box;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;

    /**
     * 解决Toast重复弹出 长时间不消失的问题
     *
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = new Toast(context);
        }
        // 设置新的消息提示
        toast.setText(content);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public static void showCenterToast(Context context, String content) {
        if (toast == null) {
            toast = new Toast(context);
        }
        // 设置新的消息提示
        toast.setText(content);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showCenterImageToast(Context context, Bitmap bitmap, String content) {
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);
        toastView.addView(imageView, 0);
        toast.show();
    }

    public static Toast getCenterImageToast(Context context, View view) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        return toast;
    }

    /**
     * <b>showCoverToast。</b>
     * <p>
     * <b>详细说明：</b>
     * <b>覆盖之前的Toast</b>
     * </p>
     * <!-- 在此添加详细说明 --> 无。
     *
     * @param context
     * @param message
     */
    public static void showCoverToast(Context context, String message) {
        if (context == null || TextUtils.isEmpty(message))
            return;
        int duration;
        if (message.length() > 10) {
            duration = Toast.LENGTH_LONG; //如果字符串比较长，那么显示的时间也长一些。
        } else {
            duration = Toast.LENGTH_SHORT;
        }
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
