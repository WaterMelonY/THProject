package bean;

import bean.finsh.xsd.FinshInterfaceFile;
import bean.req.xsd.ReqInterfaceFile;
import bean.产品元数据订单文件.xsd.OriginDataProcessOrder;
import bean.数据分段报告文件.report.xsd.SegmentReportProcessOrder;
import bean.数据分段订单文件.xsd.SegmentProcessOrder;
import bean.景元数据订单文件.xsd.SceneDataProcessOrder;
import bean.景浏览图生成订单文件.xsd.SceneImageProcessOrder;
import bean.标准图像产品生产订单.xsd.ProductProcessOrder;
import bean.段元数据订单文件.xsd.SegmentDataProcessOrder;
import bean.段分景报告文件.report.xsd.SceneReportProcessOrder;
import bean.段分景订单文件.xsd.SceneProcessOrder;

/**
 * Created by WaterMelon on 2018/5/8.
 */
public class ProcessBean {
    //传输完成通知
    private FinshInterfaceFile finshInterfaceFile;
    //传输申请
    private ReqInterfaceFile reqInterfaceFile;
    //数据分段订单
    private SegmentProcessOrder segmentProcessOrder;
    //数据分段报告文件
    private SegmentReportProcessOrder segmentReportProcessOrder;
    //段分景订单
    private SceneProcessOrder sceneProcessOrder;
    //段分景报告文件
    private SceneReportProcessOrder sceneReportProcessOrder;
    //景浏览图生成订单
    private SceneImageProcessOrder sceneImageProcessOrder;
    //产品生产订单
    private ProductProcessOrder productProcessOrder;

    //段元数据入库
    private SegmentDataProcessOrder segmentDataProcessOrder;
    //景元数据入库
    private SceneDataProcessOrder sceneDataProcessOrder;
    //产品元数据入库
    private OriginDataProcessOrder originDataProcessOrder;


    public SceneReportProcessOrder getSceneReportProcessOrder() {
        return sceneReportProcessOrder;
    }

    public void setSceneReportProcessOrder(SceneReportProcessOrder sceneReportProcessOrder) {
        this.sceneReportProcessOrder = sceneReportProcessOrder;
    }

    public FinshInterfaceFile getFinshInterfaceFile() {
        return finshInterfaceFile;
    }

    public void setFinshInterfaceFile(FinshInterfaceFile finshInterfaceFile) {
        this.finshInterfaceFile = finshInterfaceFile;
    }

    public ReqInterfaceFile getReqInterfaceFile() {
        return reqInterfaceFile;
    }

    public void setReqInterfaceFile(ReqInterfaceFile reqInterfaceFile) {
        this.reqInterfaceFile = reqInterfaceFile;
    }

    public OriginDataProcessOrder getOriginDataProcessOrder() {
        return originDataProcessOrder;
    }

    public void setOriginDataProcessOrder(OriginDataProcessOrder originDataProcessOrder) {
        this.originDataProcessOrder = originDataProcessOrder;
    }

    public SegmentReportProcessOrder getSegmentReportProcessOrder() {
        return segmentReportProcessOrder;
    }

    public void setSegmentReportProcessOrder(SegmentReportProcessOrder segmentReportProcessOrder) {
        this.segmentReportProcessOrder = segmentReportProcessOrder;
    }

    public SegmentProcessOrder getSegmentProcessOrder() {
        return segmentProcessOrder;
    }

    public void setSegmentProcessOrder(SegmentProcessOrder segmentProcessOrder) {
        this.segmentProcessOrder = segmentProcessOrder;
    }

    public SceneDataProcessOrder getSceneDataProcessOrder() {
        return sceneDataProcessOrder;
    }

    public void setSceneDataProcessOrder(SceneDataProcessOrder sceneDataProcessOrder) {
        this.sceneDataProcessOrder = sceneDataProcessOrder;
    }

    public SceneImageProcessOrder getSceneImageProcessOrder() {
        return sceneImageProcessOrder;
    }

    public void setSceneImageProcessOrder(SceneImageProcessOrder sceneImageProcessOrder) {
        this.sceneImageProcessOrder = sceneImageProcessOrder;
    }

    public ProductProcessOrder getProductProcessOrder() {
        return productProcessOrder;
    }

    public void setProductProcessOrder(ProductProcessOrder productProcessOrder) {
        this.productProcessOrder = productProcessOrder;
    }

    public SegmentDataProcessOrder getSegmentDataProcessOrder() {
        return segmentDataProcessOrder;
    }

    public void setSegmentDataProcessOrder(SegmentDataProcessOrder segmentDataProcessOrder) {
        this.segmentDataProcessOrder = segmentDataProcessOrder;
    }

    public SceneProcessOrder getSceneProcessOrder() {
        return sceneProcessOrder;
    }

    public void setSceneProcessOrder(SceneProcessOrder sceneProcessOrder) {
        this.sceneProcessOrder = sceneProcessOrder;
    }
}
