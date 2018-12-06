package com.utils.library.box;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.util.List;

@SuppressWarnings("deprecation")
public class NetWordUtils {
    public static final String NET_TYPE_WIFI = "wifi";
    public static final String NET_TYPE_2G = "2g";
    public static final String NET_TYPE_3G = "3g";
    public static final String NET_TYPE_4G = "4g";

    /**
     * <b>isNetAvailable。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 网络是否可用。
     *
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isAvailable());
    }

    /**
     * <b>isConnect。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 是否联网。
     *
     * @param context
     * @return
     */
    public static boolean isConnectNet(Context context) {
        return !getCurrentNetType(context).equals("");
    }

    /**
     * <b>getActiveNetworkInfo。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 无。
     *
     * @param context
     * @return
     */
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity != null) {
            return connectivity.getActiveNetworkInfo();
        } else {
            return null;
        }
    }

    /**
     * <b>isMobileNetwork。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 是否连接的是移动通信网。
     *
     * @param context
     * @return
     */
    public static boolean isMobileNetwork(Context context) {
        if (context != null) {
            NetworkInfo info = getActiveNetworkInfo(context);
            if (info == null) {
                return false;
            }
            return info.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }

    /**
     * <b>isWifi。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 是否处于WIFI网络
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        return getCurrentNetType(context).equals(NET_TYPE_WIFI);
    }

    /**
     * <b>isUsingProxy。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 是否使用了代理。
     *
     * @return
     */
    public static boolean isUsingProxy() {
        String host = android.net.Proxy.getDefaultHost();
        if (host == null || host.trim().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * <b>getCurrentNetType。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 获取网络类型。
     *
     * @param context
     * @return
     */
    public static String getCurrentNetType(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                type = NET_TYPE_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = info.getSubtype();
                if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                        || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                    type = NET_TYPE_2G;
                } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                    type = NET_TYPE_3G;
                } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                    type = NET_TYPE_4G;
                }
            }
        }
        return type;
    }

    /**
     * <b>getWifiState。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 获取wifi状态。
     *
     * @param context
     * @return
     */
    public static int getWifiState(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService("wifi");
        if (wifi == null) {
            return 4;
        }
        return wifi.getWifiState();
    }

    /**
     * <b>getWifiConnectivityState。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 获取wifi连接状态。
     *
     * @param context
     * @return
     */
    public static NetworkInfo.DetailedState getWifiConnectivityState(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return networkInfo == null ? NetworkInfo.DetailedState.FAILED : networkInfo.getDetailedState();
    }

    /**
     * <b>wifiConnection。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 用密码连接WIFI。
     *
     * @param context
     * @param wifiSSID
     * @param password
     * @return
     */
    public static boolean connectWifiWithPassWord(Context context, String wifiSSID, String password) {
        boolean isConnection = false;
        WifiManager wifi = (WifiManager) context.getSystemService("wifi");
        String strQuotationSSID = "\"" + wifiSSID + "\"";
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        if ((wifiInfo != null) && ((wifiSSID.equals(wifiInfo.getSSID())) || (strQuotationSSID.equals(wifiInfo.getSSID())))) {
            isConnection = true;
        } else {
            List<ScanResult> scanResults = wifi.getScanResults();
            if ((scanResults != null) && (scanResults.size() != 0)) {
                for (int nAllIndex = scanResults.size() - 1; nAllIndex >= 0; nAllIndex--) {
                    String strScanSSID = ((ScanResult) scanResults.get(nAllIndex)).SSID;
                    if ((wifiSSID.equals(strScanSSID)) || (strQuotationSSID.equals(strScanSSID))) {
                        WifiConfiguration config = new WifiConfiguration();
                        config.SSID = strQuotationSSID;
                        config.preSharedKey = ("\"" + password + "\"");
                        config.status = 2;
                        int nAddWifiId = wifi.addNetwork(config);
                        isConnection = wifi.enableNetwork(nAddWifiId, false);
                        break;
                    }
                }
            }
        }
        return isConnection;
    }
}
