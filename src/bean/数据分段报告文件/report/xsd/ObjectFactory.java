package bean.数据分段报告文件.report.xsd;//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2018.05.07 ʱ�� 08:52:46 AM CST 
//
import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ���ݷֶα����ļ�.report.xsd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ���ݷֶα����ļ�.report.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SegmentReportProcessOrder }
     * 
     */
    public SegmentReportProcessOrder createProcessOrder() {
        return new SegmentReportProcessOrder();
    }

    /**
     * Create an instance of {@link SegmentReportProcessOrder.Task }
     * 
     */
    public SegmentReportProcessOrder.Task createProcessOrderTask() {
        return new SegmentReportProcessOrder.Task();
    }

    /**
     * Create an instance of {@link SegmentReportProcessOrder.Task.Segments }
     * 
     */
    public SegmentReportProcessOrder.Task.Segments createProcessOrderTaskSegments() {
        return new SegmentReportProcessOrder.Task.Segments();
    }

    /**
     * Create an instance of {@link SegmentReportProcessOrder.Task.Segments.Segment }
     * 
     */
    public SegmentReportProcessOrder.Task.Segments.Segment createProcessOrderTaskSegmentsSegment() {
        return new SegmentReportProcessOrder.Task.Segments.Segment();
    }

    /**
     * Create an instance of {@link NewDataSet }
     * 
     */
    public NewDataSet createNewDataSet() {
        return new NewDataSet();
    }

    /**
     * Create an instance of {@link SegmentReportProcessOrder.Task.Segments.Segment.FredData }
     * 
     */
    public SegmentReportProcessOrder.Task.Segments.Segment.FredData createProcessOrderTaskSegmentsSegmentFredData() {
        return new SegmentReportProcessOrder.Task.Segments.Segment.FredData();
    }

    /**
     * Create an instance of {@link SegmentReportProcessOrder.Task.Segments.Segment.Incalibdata }
     * 
     */
    public SegmentReportProcessOrder.Task.Segments.Segment.Incalibdata createProcessOrderTaskSegmentsSegmentIncalibdata() {
        return new SegmentReportProcessOrder.Task.Segments.Segment.Incalibdata();
    }

}
