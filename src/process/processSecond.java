package process;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.PublicUtil;


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

    /**
     * 根据分段订单xml启动分景流程
     * @param reportFilePath
     * @return
     */
    public String createSceneOrder(String reportFilePath){
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //拼接xml然后提交到redis
                        createDoc(document);
                    }
                });
            }
        }


//<task name="" id="" desc="" orderid="">
//	<inputfilelist num="1">
//		<segmentData>/DiskArray/data/catalog/JB13A-1/2015/0729/JB13A-1_SAR_000000368_001/JB13A-1_SAR_000000368_001.FRED</segmentData>
//		<segmentXML>/DiskArray/data/catalog/JB13A-1/2015/0729/JB13A-1_SAR_000000368_001/JB13A-1_SAR_000000368_001.xml</segmentXML>
//		<GPS>.gps.txt</GPS>
//		<ATT_OULER>.att_ouler.txt</ATT_OULER>
//		<ATT_FOUR>.att_four.txt</ATT_FOUR>
//		<EPHM>.ephm.txt</EPHM>
//	</inputfilelist>
//	<outputfilelist num="2">
//		<reportFile>/DiskArray/data/catalog/JB13A-1/2015/0729/JB13A-1_SAR_000000368_001/JB13A-1_SAR_000000368_001.scenes.report.xml</reportFile>
//		<resultFile>/DiskArray/ars/meta/2015/0729/taskid.result.xml</resultFile>
//	</outputfilelist>
//	<params>
//		<satelliteId>JB13A-1</satelliteId>
//		<trplanid>368</trplanid>
//		<sensor>SAR</sensor>
//		<segmentID>JB13A-1_SAR_000000368_001</segmentID>
//		<imagingMode>strip/spot/top</imagingMode>
//		<segmentDir>/DiskArray/data/catalog/JB13A-1/2015/0729/JB13A-1_SAR_000000368_001</segmentDir>
//	</params>
//</task>

        //然后提交到redis中

        //数据入库xml然后进行

        //开启监听
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            int i = 1;

            @Override
            public void run() {

                if(log.isDebugEnabled())
                    log.debug("当前已扫描：" + i + "周");

                //查询数据库 看当前id 对应的状态
                if(PublicUtil.getProcessStatusByProcessID(1)) {

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

        return returnStr;
    }

    public void startSceneImage(String reportFilePath){

    }

    public Document createDoc(Document document){


        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String time = dateFormat.format(date);

        Document doc = DocumentHelper.createDocument();

        //填充模板内容
        Element root = doc.addElement("process-order").addAttribute("id", time).addAttribute("name", "")
                .addAttribute("priority", "1");

        Element task = root.addElement("task");
        task.addAttribute("orderid","orderid");
        task.addAttribute("priority","2");
        task.addAttribute("id","taskid");
        task.addAttribute("name","TASK");

        Element inputfilelist = task.addElement("inputfilelist");
        inputfilelist.addAttribute("num","1").addElement("10Data").setText("");
        inputfilelist.setText("");

        Element outputfilelist = task.addElement("outputfilelist");
        outputfilelist.addAttribute("num","2").addElement("reportFile").setText("D:\\ThFileSave\\JB13A-1\\2015\\0729\\368_1406050001\\taskid.report.xml");
        outputfilelist.addElement("resultFile").setText("D:\\ThFileSave\\JB13A-1\\2015\\0729\\368_1406050001\\taskid.result.xml");
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

        return doc;
    }
}
