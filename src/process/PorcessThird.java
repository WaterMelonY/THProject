package process;

import org.dom4j.Document;
import util.PublicUtil;

/**
 * Created by WaterMelon on 2018/4/20.
 */
public class PorcessThird {

    public String createSceneImageOrder(String reportXml){
        String returnStr = "";
        //获取段分景报告文件
        Document document = PublicUtil.getDocByFilePath(reportXml);
        //获取xml然后 遍历 scene 为success的进行下单。

        //生成xml然后提交到redis中。

        //开启监听判断任务是否完成。

        //数据入库xml

        return returnStr;
    }
}
