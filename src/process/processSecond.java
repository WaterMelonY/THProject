package process;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.PublicUtil;
import util.RedisUtil;
import util.TimeUtil;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by WaterMelon on 2018/4/20.
 */
public class processSecond {

    private static Logger log = Logger.getLogger(processSecond.class);

    private final static String PROCESS_SCENE = "THSceneProcess";
    private final static String TASK_SCENE = "THSceneOrder";

    private final static String TASK_PROCESS_SCENE_DATA = "THSegmentData";

    /**
     * 根据分段订单xml启动分景流程
     * @param reportFilePath
     * @return
     */
    public void createSceneOrder(String reportFilePath){
        String returnStr = "";

        //读取分段订单xml中的 segment 中为success的进行分景流程
        Document document = PublicUtil.getDocByFilePath(reportFilePath);
        //在循环中拼接xml
        Element rootElement = document.getRootElement();
        List segments = rootElement.element("segments").elements("segment");

        for (Iterator it = segments.iterator(); it.hasNext();) {
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
                    createDoc(document,time);
                });
                new Thread(()->{
                    xmlToData(document,time);
                });
            }
        }

    }

    public void startSceneImage(String reportFilePath){

    }

    public boolean createDoc(Document document,String time){
        String orderId = PROCESS_SCENE+time;
        String taskId = TASK_SCENE+time;

        Document doc = DocumentHelper.createDocument();

        //填充模板内容
        Element root = doc.addElement("process-order").addAttribute("id", orderId).addAttribute("name", PROCESS_SCENE)
                .addAttribute("priority", "1");

        Element task = root.addElement("task");
        task.addAttribute("orderid",orderId);
        task.addAttribute("priority","2");
        task.addAttribute("id",taskId);
        task.addAttribute("name",TASK_SCENE);

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

        //开启监听
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            int i = 1;

            @Override
            public void run() {

                if(log.isDebugEnabled())
                    log.debug("当前已扫描：" + i + "周");

                //查询数据库 看当前id 对应的状态
                if(PublicUtil.getProcessStatusByOrderId("")) {

                    timer.cancel();

                    String reportFilePath = "";
                    startSceneImage(reportFilePath);
                }

                //如果超过四次没通过 停止
                if(i == 4) {
                    timer.cancel();
//					if(log.isDebugEnabled())
//						log.debug(TimeUtil.getCurDate() + ":" + processParam + "生产时间超，时定制器强制停止");
                }

                i++;

            }
            // 0：延迟次数 即从开启到执行第一次扫描的时间  period：周期 多久扫描一次
        }, 0, 3000);

        return true;
    }

    public boolean xmlToData(Document document,String time){
        Document doc = DocumentHelper.createDocument();
        String orderId = PROCESS_SCENE+time;
        String taskId = TASK_PROCESS_SCENE_DATA+time;

        Element root = doc.addElement("process-order").addAttribute("id", orderId).addAttribute("name", PROCESS_SCENE)
                .addAttribute("priority", "1");

        Element task = root.addElement("task");
        task.addAttribute("orderid",orderId);
        task.addAttribute("priority","1");
        task.addAttribute("id",taskId);
        task.addAttribute("name",TASK_PROCESS_SCENE_DATA);

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

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            int i = 1;

            @Override
            public void run() {

                if(log.isDebugEnabled())
                    log.debug("当前已扫描：" + i + "周");

                //查询数据库 看当前id 对应的状态
                if(PublicUtil.getProcessStatusByOrderId(orderId)) {

                    timer.cancel();

                    String reportFilePath = "";
                    startSceneImage(reportFilePath);
                }

                //如果超过四次没通过 停止
                if(i == 4) {
                    timer.cancel();
//					if(log.isDebugEnabled())
//						log.debug(TimeUtil.getCurDate() + ":" + processParam + "生产时间超，时定制器强制停止");
                }

                i++;

            }
            // 0：延迟次数 即从开启到执行第一次扫描的时间  period：周期 多久扫描一次
        }, 0, 3000);


        return true;
    }
}
