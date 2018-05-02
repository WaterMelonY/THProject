package process;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import redis.clients.jedis.Jedis;

import org.dom4j.Document;
import util.PublicUtil;
import util.RedisUtil;
import util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by WaterMelon on 2018/5/2.
 *
 * 待优化 将xml填充直接换成对javabean的操作；
 * 相关字符串和配置 使用配置文件方式进行获取；
 */
public class ProcessXmlCreate {

    //————————————————数据分段--------------------------------------------------Start---------------
    public static void createSegmentOrder(String completeFile){

        Document completeDoc = PublicUtil.readXml(completeFile);
        //将来用javabean来替换completeDoc

        String processName = "THSegmentProcess";
        String taskName = "THSegmentData";
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String time = dateFormat.format(date);

        Document doc = DocumentHelper.createDocument();

        String orderId = processName+time;
        String taskId = taskName+time;

        //填充模板内容
        Element root = doc.addElement("process-order").addAttribute("id", processName+time).addAttribute("name", processName)
                .addAttribute("priority", "1");

        Element task = root.addElement("task");
        task.addAttribute("orderid",processName+time);
        task.addAttribute("priority","1");
        task.addAttribute("id",taskName+time);
        task.addAttribute("name",taskName);

        Element inputfilelist = task.addElement("inputfilelist");
        inputfilelist.addAttribute("num","1").addElement("l0Data").setText("test10Data");
//        inputfilelist.setText("testInputfilelist");

        Element outputfilelist = task.addElement("outputfilelist");
        String year = TimeUtil.getCurYear();
        String month = TimeUtil.getCurMonth();
        outputfilelist.addAttribute("num","2").addElement("reportFile").setText("/DiskArray/iecas/root/dpps/meta/"+ year+"/"+month+"/"+taskId+".report.xml");
        outputfilelist.addElement("resultFile").setText("/DiskArray/iecas/root/dpps/meta/"+ year+"/"+month+"/"+taskId+".result.xml");
//        outputfilelist.setText("");

        Element params = task.addElement("params");
        params.addElement("satelliteId").setText("JB13A-1");
        params.addElement("stationID").setText("1111");
        params.addElement("trplanid").setText("1111");
        params.addElement("pbtaskid").setText("1111");
        params.addElement("sensor").setText("SAT");
        params.addElement("segmentIDPrefix").setText("JB13A-1_SAR_000000368");
        params.addElement("segmentDirRoot").setText("/DiskArray/iecas/root/Output/BM/ZY3-02/BM_4656_Product/ZY3/MUX/");
        params.addElement("server_address").setText("127.0.0.1");
        params.addElement("server_port").setText("11111");

        //此行代码为了测试 正式发布注释掉
        PublicUtil.outputXml(doc,"D:\\CH\\testXml\\Process\\"+taskId+".xml");
        //提交到redis中。
        RedisUtil.submit(doc.asXML());
        //开启监听
        PublicUtil.timerLinster(taskId,taskName);
    }
    //————————————————数据分段--------------------------------------------------End------------------------------


    //-----------------------------------------------段分景处理------------------------------Start-----------------------------
    public static void createSceneOrder(String reportFilePath){
        Document document = PublicUtil.getDocByFilePath(reportFilePath);
        //在循环中拼接xml
        Element rootElement = document.getRootElement();
        List segments = rootElement.element("segments").elements("segment");

        for (Iterator it = segments.iterator(); it.hasNext();) {
            Element segment = (Element) it.next();
            String status = segment.element("status").getStringValue();
            if("SUCCESS".equals(status.toUpperCase())){
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                String time = dateFormat.format(date);
                //拼接xml然后提交到redis //然后提交到redis中
                new Thread(()->{
                    createSceneDoc(document,time);
                });
                new Thread(()->{
                    sceneXmlToData(document,time);
                });

            }
        }
    }

    public static void createSceneDoc(Document document,String time){
        String processName = "THSceneProcess";
        String taskName = "THSceneOrder";

        String orderId = processName+time;
        String taskId = taskName+time;

        Document doc = DocumentHelper.createDocument();

        //填充模板内容
        Element root = doc.addElement("process-order").addAttribute("id", orderId).addAttribute("name", processName)
                .addAttribute("priority", "1");

        Element task = root.addElement("task");
        task.addAttribute("orderid",orderId);
        task.addAttribute("priority","2");
        task.addAttribute("id",taskId);
        task.addAttribute("name",taskName);

        Element inputfilelist = task.addElement("inputfilelist");
        inputfilelist.addAttribute("num","1").addElement("10Data").setText("");
        inputfilelist.setText("");

        Element outputfilelist = task.addElement("outputfilelist");
        String year = TimeUtil.getCurYear();
        String month = TimeUtil.getCurMonth();
        outputfilelist.addAttribute("num","2").addElement("reportFile").setText("/DiskArray/iecas/root/dpps/meta/"+ year+"/"+month+"/"+task+"/"+taskId+".report.xml");
        outputfilelist.addElement("resultFile").setText("/DiskArray/iecas/root/dpps/meta/"+ year+"/"+month+"/"+task+"/"+taskId+".result.xml");
//        outputfilelist.setText("");

        Element params = task.addElement("params");
        params.addElement("satelliteId").setText("JB13A-1");
        params.addElement("stationID").setText("1111");
        params.addElement("trplanid").setText("1111");
        params.addElement("pbtaskid").setText("1111");
        params.addElement("sensor").setText("SAT");
        params.addElement("segmentIDPrefix").setText("JB13A-1_SAR_000000368");
        params.addElement("segmentDirRoot").setText("D:\\ThFileSave\\JB13A-1\\2015\\0729\\");
        params.addElement("server_address").setText("127.0.0.1");
        params.addElement("server_port").setText("11111");

        RedisUtil.submit(doc.asXML());
        PublicUtil.timerLinster(taskId,taskName);
    }

    public static void sceneXmlToData(Document document,String time){
        String processName = "THSceneProcess";
        String taskName = "THSceneOrder";

        String orderId = processName+time;
        String taskId = taskName+time;

        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("process-order").addAttribute("id", orderId).addAttribute("name", processName)
                .addAttribute("priority", "1");

        Element task = root.addElement("task");
        task.addAttribute("orderid",orderId);
        task.addAttribute("priority","1");
        task.addAttribute("id",taskId);
        task.addAttribute("name",taskName);

        Element inputfilelist = task.addElement("inputfilelist").addAttribute("num","5");
        Element outputfilelist = task.addElement("outputfilelist").addAttribute("num","1");
        Element params = task.addElement("params");

        Element segmentmetafile = inputfilelist.addElement("segmentmetafile");
        segmentmetafile.setText("");

        Element sendfin = inputfilelist.addElement("sendfin");
        sendfin.setText("");

        Element auxfile = inputfilelist.addElement("auxfile");
        auxfile.setText("");

        Element fredfile = inputfilelist.addElement("fredfile");
        fredfile.setText("");

        Element filesize = inputfilelist.addElement("filesize");
        filesize.setText("");

        Element resultFile = outputfilelist.addElement("resultFile");
        resultFile.setText("");

        Element trplanid = params.addElement("trplanid");
        trplanid.setText("");

        RedisUtil.submit(doc.asXML());
        //开启监听
        PublicUtil.timerLinster(taskId,taskName);
    }

    //-----------------------------------------------段分景处理------------------------------End------------------------------


    //-----------------------------------------------景图像生成------------------------------Start----------------------------
    public static void createSceneImageOrder(String reportFilePath){
        Document document = PublicUtil.getDocByFilePath(reportFilePath);
        //在循环中拼接xml
        Element rootElement = document.getRootElement();
        List scenes = rootElement.element("scene").elements("scene");

        for (Iterator it = scenes.iterator(); it.hasNext();) {
            Element segment = (Element) it.next();
            String status = segment.element("status").getStringValue();
            if("SUCCESS".equals(status.toUpperCase())){
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //拼接xml然后提交到redis //然后提交到redis中
//                        createDoc(document);
//                    }
//                });
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                String time = dateFormat.format(date);
                //拼接xml然后提交到redis //然后提交到redis中
                new Thread(()->{
                    createSceneImageDoc(document,time);
                });
                new Thread(()->{
                    sceneImageXmlToData(document,time);
                });

            }
        }
    }

    public static void createSceneImageDoc(Document document,String time){
        String processName = "THSceneImageProcess";
        String taskName = "THSceneImageOrder";

        String orderId = processName+time;
        String taskId = taskName+time;

        Document doc = DocumentHelper.createDocument();

        //填充模板内容
        Element root = doc.addElement("process-order").addAttribute("id", orderId).addAttribute("name", processName)
                .addAttribute("priority", "1");

        Element task = root.addElement("task");
        task.addAttribute("orderid",orderId);
        task.addAttribute("priority","2");
        task.addAttribute("id",taskId);
        task.addAttribute("name",taskName);

        Element inputfilelist = task.addElement("inputfilelist");
        inputfilelist.addAttribute("num","1").addElement("10Data").setText("");
        inputfilelist.setText("");

        Element outputfilelist = task.addElement("outputfilelist");
        String year = TimeUtil.getCurYear();
        String month = TimeUtil.getCurMonth();
        outputfilelist.addAttribute("num","2").addElement("reportFile").setText("/DiskArray/iecas/root/dpps/meta/"+ year+"/"+month+"/"+task+"/"+taskId+".report.xml");
        outputfilelist.addElement("resultFile").setText("/DiskArray/iecas/root/dpps/meta/"+ year+"/"+month+"/"+task+"/"+taskId+".result.xml");
//        outputfilelist.setText("");

        Element params = task.addElement("params");
        params.addElement("satelliteId").setText("JB13A-1");
        params.addElement("stationID").setText("1111");
        params.addElement("trplanid").setText("1111");
        params.addElement("pbtaskid").setText("1111");
        params.addElement("sensor").setText("SAT");
        params.addElement("segmentIDPrefix").setText("JB13A-1_SAR_000000368");
        params.addElement("segmentDirRoot").setText("D:\\ThFileSave\\JB13A-1\\2015\\0729\\");
        params.addElement("server_address").setText("127.0.0.1");
        params.addElement("server_port").setText("11111");

        RedisUtil.submit(doc.asXML());

        PublicUtil.timerLinster(taskId,taskName);
    }

    public static void sceneImageXmlToData(Document document,String time){
        String processName = "THSceneImageProcess";
        String taskName = "THSceneData";

        String orderId = processName+time;
        String taskId = taskName+time;

        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("process-order").addAttribute("id", orderId).addAttribute("name", processName)
                .addAttribute("priority", "1");

        Element task = root.addElement("task");
        task.addAttribute("orderid",orderId);
        task.addAttribute("priority","1");
        task.addAttribute("id",taskId);
        task.addAttribute("name",taskName);

        Element inputfilelist = task.addElement("inputfilelist").addAttribute("num","5");
        Element outputfilelist = task.addElement("outputfilelist").addAttribute("num","1");
        Element params = task.addElement("params");

        Element segmentmetafile = inputfilelist.addElement("segmentmetafile");
        segmentmetafile.setText("");

        Element sendfin = inputfilelist.addElement("sendfin");
        sendfin.setText("");

        Element auxfile = inputfilelist.addElement("auxfile");
        auxfile.setText("");

        Element fredfile = inputfilelist.addElement("fredfile");
        fredfile.setText("");

        Element filesize = inputfilelist.addElement("filesize");
        filesize.setText("");

        Element resultFile = outputfilelist.addElement("resultFile");
        resultFile.setText("");

        Element trplanid = params.addElement("trplanid");
        trplanid.setText("");

        RedisUtil.submit(doc.asXML());

        PublicUtil.timerLinster(taskId,taskName);
    }
    //-----------------------------------------------景图像生成------------------------------End------------------------------
}
