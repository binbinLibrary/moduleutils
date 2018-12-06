package com.utils.library.box;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class DetectionUtils {
    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWxInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (!JavaUtils.isListEmpty(packageInfoList)) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                String pn = packageInfoList.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    public static boolean isAliPayInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (!JavaUtils.isListEmpty(packageInfoList)) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                String pn = packageInfoList.get(i).packageName;
                if (pn.equals("com.eg.android.AlipayGphone")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测是否安装微博
     *
     * @param context
     * @return
     */
    public static boolean isWeiBoInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (!JavaUtils.isListEmpty(packageInfoList)) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                String pn = packageInfoList.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测是否安装QQ
     *
     * @param context
     * @return
     */
    public static boolean isQQInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (!JavaUtils.isListEmpty(packageInfoList)) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                String pn = packageInfoList.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
