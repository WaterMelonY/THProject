package util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import process.ProcessXmlCreate;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by WaterMelon on 2018/4/20.
 * 各类公共方法类
 */
public class PublicUtil {
    private static Logger log = Logger.getLogger(PublicUtil.class);
    /**
     * 获取指定格式的当前系统时间
     *
     * @param format
     *            时间格式
     * @return
     */
    public static String getFormatTime(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String time = dateFormat.format(date);
        return time;
    }

    /**
     * 将xml保存到指定路径
     *
     * @param doc
     * @param fileName
     */
    public static void outputXml(Document doc, String fileName) {
        XMLWriter xmlWrite = null;
        try {
            // 创建一个FileWriter对象，指定目标文件
            FileWriter fw = new FileWriter(fileName);
            // 指定xml文件的输出格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 定义编码格式
            format.setEncoding("UTF-8");
            // 写出xml文件到操作系统
            xmlWrite = new XMLWriter(fw, format);
            // 写出文档对象
            xmlWrite.write(doc);
            xmlWrite.close();
        } catch (Exception e) {
        } finally {
            if (xmlWrite != null) {
                try {
                    xmlWrite.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 读取xml文件
    public static Document readXml(String fileName) {
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(new File(fileName));
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        return document;
    }

    /**
     * 读取配置文件
     *
     * @return
     */
    public static Properties loadProperty(String fileName) {
        Properties pro = new Properties();
        InputStream is;
        try {
            is = PublicUtil.class.getClassLoader().getResourceAsStream(fileName);
            pro.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pro;
    }

    /**
     * 将集合数据插入到指定表中，要求集合数据个数与表字段个数一样，切顺序一致
     *
     * @param cols
     *            字段集合（例：c1,c2,c3）
     * @param data
     *            数据集合
     * @param tableName
     *            表名
     */
    public static void insertTable(String cols, List<String> data, String tableName) {
        if (null == data || data.isEmpty()) {
            System.out.println("insertTable 参数格式有误！");
            return;
        }
        StringBuffer sqlBuffer = new StringBuffer("INSERT INTO " + tableName);
        if (null != cols && !"".equals(cols.trim())) {
            sqlBuffer.append('(' + cols + ')');
        }
        sqlBuffer.append(" VALUES (");
        for (String v : data) {
            sqlBuffer.append("'");
            sqlBuffer.append(v);
            sqlBuffer.append("',");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        sqlBuffer.append(')');
//		System.out.println(sqlBuffer.toString());
//        LoggerUtil.getLogger().info(sqlBuffer.toString());
        Logger.getLogger(PublicUtil.class).info(sqlBuffer.toString());
        if(!executeSql(sqlBuffer.toString())){
            System.out.println("表" + tableName + "数据插入失败！");
        }
    }

    /**
     * 更新表
     *
     * @param data（key：字段名，value：字段值）
     * @param tableName 表名
     * @param where where条件，如果没有条件，可设为null或者空字符串
     */
    public static void updateTable(String tableName, Map<String, String> data, String where) {
        if (null == data || data.isEmpty()) {
            return;
        }
        StringBuffer sqlBuffer = new StringBuffer("UPDATE " + tableName + " SET ");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sqlBuffer.append(entry.getKey());
            sqlBuffer.append("='");
            sqlBuffer.append(entry.getValue());
            sqlBuffer.append("',");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        if (null != where && !where.isEmpty()) {
            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(where);
        }
        System.out.println(sqlBuffer.toString());
        if(!executeSql(sqlBuffer.toString())){
            System.out.println("表" + tableName + "数据更新失败！");
        }
    }

    /**
     * 执行没有占位符，没有返回值的sql语句
     *
     * @return
     */
    public static boolean executeSql(String sql) {
        Connection conn = PoolConnection.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            PoolConnection.closeDB(null, pst, conn);
        }
        return true;
    }

    public static boolean getProcessStatusByOrderId(String orderId){
        String sql = "select status from PD_ProcessInfo where orderId = '" + orderId+"'";
        Connection conn = PoolConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String status = null;
        boolean flag = false;
        try {
            System.out.println(sql);
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                status = rs.getString("status");
            }

            //如果不是这两种状态则为false
            if(status!=null&&("Completed".contains(status) || "Aborted".contains(status))) {
                flag = true;
            } else {
                flag = false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PoolConnection.closeDB(rs, pst, conn);
            return flag;
        }
    }

    /**
     * 根据插件名称取出订单模板
     * @param orderName
     * @return
     */
    public static String getXMLByOrderName(String orderName){
        String xmlStr = null;

        String sql = "select TemplateXml from PluginInfo where PluginName = '"+orderName+"'";

        Connection conn = PoolConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                xmlStr = rs.getString("TemplateXml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return xmlStr;
    }


    public static Document getDocByFilePath(String filePath){
        SAXReader reader = new SAXReader();
        // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
        Document document = null;
        try {
            document = reader.read(new File(filePath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Document getDocByFile(File file){
        SAXReader reader = new SAXReader();
        // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void timerLinster(String orderId,String orderName){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            int i = 1;

            @Override
            public void run() {

                if(log.isInfoEnabled())
                    log.info("当前已扫描：" + i + "周");

                //查询数据库 看当前id 对应的状态
                if(PublicUtil.getProcessStatusByOrderId(orderId)) {
                    timer.cancel();
                    String reportFilePath = "";
                    if("THSegmentProcess".equals(orderName)){
                        ProcessXmlCreate.createSceneOrder(reportFilePath);
                    }else if("THSceneProcess".equals(orderName)){
                        ProcessXmlCreate.createSceneImageOrder(reportFilePath);
                    }
                }

                //如果超过四次没通过 停止
                if(i == 4) {
                    timer.cancel();
					if(log.isDebugEnabled())
						log.debug(TimeUtil.getCurDate() + ":" + orderId + "生产时间超，时定制器强制停止");
                }

                i++;

            }
            // 0：延迟次数 即从开启到执行第一次扫描的时间  period：周期 多久扫描一次
        }, 0, 3000);

    }
}
