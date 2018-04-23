package process;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import util.PublicUtil;
import util.RedisUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WaterMelon on 2018/4/19.
 */
public class processFirst {
    private String processName = "ThSegmentProcess";
    private String taskName = "THduanInfo";

    private String processParam = "";

    private static Logger log = Logger.getLogger(processFirst.class);

    public String createSegmentOrder(){
        String returnStr = "";
        //从redis中获取模板

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String time = dateFormat.format(date);

        Document doc = DocumentHelper.createDocument();

        //填充模板内容
        Element root = doc.addElement("process-order").addAttribute("id", processName+time).addAttribute("name", processName)
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
        //提交到redis中。
        RedisUtil.submit(doc.asXML());
        //检测模板完成情况 定时器查询pd_processInfo--->status
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
                    //开启下一个流程 ， 代码后期优化，使用递归模式进行调用。
//                    startSenceProcess(reportFilePath);
                    new processSecond().createSceneOrder(reportFilePath);
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

    public void startSenceProcess(String processName){
        processParam = processName;
        switch(processParam) {
            case "1":
                processParam = "2";
                break;
            case "2":
                break;
        }
    }

    public static void main(String[] args) {
        new processFirst().createSegmentOrder();
    }
}
