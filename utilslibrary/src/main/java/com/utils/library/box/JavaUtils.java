package com.utils.library.box;

import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaUtils {
    public static final int ASCII_DATA_A = 65;

    public static JSONObject mapToJson(Map<String, String> map) {
        JSONObject json = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                json.put(entry.getKey(), entry.getValue().toString());
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 将json格式的字符串解析成Map对象
     */
    public static HashMap<String, String> jsonToMap(String json) {
        HashMap<String, String> map = new HashMap<>();
        try {
            // 将json字符串转换成jsonObject
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (iterator.hasNext()) {
                String key = String.valueOf(iterator.next());
                String value = jsonObject.optString(key);
                map.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    //加密手机号
    public static String phoneEncryption(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } else {
            return "";
        }
    }

    //加密身份证号
    public static String idCardEncryption(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            if (idCard.length() == 15) {
                return idCard.replaceAll("(\\d{4})\\d{7}(\\w{4})", "$1*******$2");
            } else if (idCard.length() == 18) {
                return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1**********$2");
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * <b>getOne。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 无。
     *
     * @param list
     * @return
     */
    public static Object getListFirstItem(List<Object> list) {
        if ((list != null) && !list.isEmpty()) {
            if (list.get(0) != null) {
                return list.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * <b>getListItemById。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> 返回list集合中指定id的对象。
     *
     * @param list
     * @param id
     * @return
     */
    public static Object getListItemById(List<Object> list, int id) {
        if ((list != null) && !list.isEmpty() && id < list.size()) {
            if (list.get(id) != null) {
                return list.get(id);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * <b>isListEmpty。</b>
     * <p>
     * <b>详细说明：</b>
     * </p>
     * <!-- 在此添加详细说明 --> list是否为空。
     *
     * @param l
     * @return
     */
    public static boolean isListEmpty(List<?> l) {
        return (l == null || l.isEmpty());
    }

    public static boolean isStringEmpty(String context) {
        return (context == null || context.trim().equals("") || context.trim().equals("null"));
    }

    public static char ASCII2String(int ascii) {
        return (char) ascii;
    }

    public static int string2ASCII(char chars) {
        return (int) chars;
    }


    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
//        try {
//            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            int i = wifiInfo.getIpAddress();
//            return int2ip(i);
//        } catch (Exception ex) {
//            return "" + ex.getMessage();
//        }
        NetworkInfo info = NetWordUtils.getActiveNetworkInfo(context);
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddress = intf.getInetAddresses(); enumIpAddress.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddress.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = int2ip(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        }
        return "";
    }

    /**
     * 去除重复的字符串
     *
     * @param list
     * @return
     */
    public static List<String> removeDuplicate(List<String> list) {
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> getListDiff(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<String>();
        for (String str : list1) {
            if (!list2.contains(str)) {
                diff.add(str);
            }
        }
        return diff;
    }

    /**
     * 获取两个List的相同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> getListSame(List<String> list1, List<String> list2) {
        List<String> same = new ArrayList<String>();
        for (String str : list1) {
            if (list2.contains(str)) {
                same.add(str);
            }
        }
        return same;
    }

    public static boolean isListContainsString(List<String> list, String str) {
        if (!isListEmpty(list)) {
            if (list.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static void CopyToClipboard(Context context, String text) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //clip.getText(); // 粘贴
        clip.setText(text); // 复制
    }
}
