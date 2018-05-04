package test.socketTest;

import socket.FileUpLoadClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WaterMelon on 2018/4/19.
 */
public class SocketTest {
    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                file("D:\\linux\\[红帽企业Linux.6.4.服务器版].rhel-server-6.4-x86_64-dvd[ED2000.COM].iso");
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                file("D:\\工作相关\\接收资料\\VMwareworkstation_full_12.1.0.2487.1453173744[1].exe");
//            }
//        }).start();


//        List<String> filePaths = new ArrayList<String>();
//        filePaths.add("");
//        filePaths.add("");
//        filePaths.add("");
//        snedFiles(filePaths);

        sendfile("D:\\linux\\[红帽企业Linux.6.4.服务器版].rhel-server-6.4-x86_64-dvd[ED2000.COM].iso");
//        sendfile("");
//        sendfile("");
    }

    public static void sendfile(String filePath){
        FileUpLoadClient client = null;
        try {
            client = new FileUpLoadClient("172.24.10.210",8888);
//            client.statusInfo();
            client.sendFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void snedFiles(List<String> filePaths){
        for (String filePath: filePaths
             ) {
            sendfile(filePath);
        }
    }

    public static String getDatePoor(Date endDate, Date nowDate) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            endDate = sdf.parse(sdf.format(endDate));
            nowDate = sdf.parse(sdf.format(nowDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return "使用时间："+day + "天" + hour + "小时" + min + "分钟";
    }
}
