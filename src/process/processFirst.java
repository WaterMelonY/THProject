package process;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import redis.clients.jedis.Jedis;
import util.PublicUtil;
import util.RedisUtil;
import util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WaterMelon on 2018/4/19.
 */
public class processFirst {
    private String processName = "THSegmentProcess";
    private String taskName = "THSegmentOrder";

    private String processParam = "";

    private static Logger log = Logger.getLogger(processFirst.class);

    public String createSegmentOrder(){
        String returnStr = "";
        //从redis中获取模板

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
        String month = TimeUtil.getCurNyDate();
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
//        System.out.println(doc.asXML());
        PublicUtil.outputXml(doc,"D:\\CH\\testXml\\Process\\"+taskId+".xml");
        //提交到redis中。
        String redisIP = "172.24.10.161";
        int redisPort = 8715;
        int intervalTime = 6000;
        Jedis redis = new Jedis(redisIP, redisPort, intervalTime);
        redis.rpush("dpps:queue:order", doc.asXML());
        redis.close();

        //将传输结束通知信息入库。

        //检测模板完成情况 定时器查询pd_processInfo--->status
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            int i = 1;

            @Override
            public void run() {

                if(log.isInfoEnabled())
                    log.debug("当前已扫描：" + i + "周");

                //查询数据库 看当前id 对应的状态
                if(PublicUtil.getProcessStatusByOrderId(orderId)) {

                    timer.cancel();

                    String reportFilePath = "";
                    //开启下一个流程 ， 代码后期优化，使用递归模式进行调用。
//                    startSenceProcess(reportFilePath);
//                    new processSecond().createSceneOrder(reportFilePath);
//                    System.out.println("我要开启下一个流程");
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
