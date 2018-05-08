package bean.数据分段订单文件.xsd;//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2018.05.07 ʱ�� 08:52:46 AM CST 
//


import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ���ݷֶζ����ļ�.xsd package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ���ݷֶζ����ļ�.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SegmentProcessOrder }
     * 
     */
    public SegmentProcessOrder createProcessOrder() {
        return new SegmentProcessOrder();
    }

    /**
     * Create an instance of {@link SegmentProcessOrder.Task }
     * 
     */
    public SegmentProcessOrder.Task createProcessOrderTask() {
        return new SegmentProcessOrder.Task();
    }

    /**
     * Create an instance of {@link NewDataSet }
     * 
     */
    public NewDataSet createNewDataSet() {
        return new NewDataSet();
    }

    /**
     * Create an instance of {@link SegmentProcessOrder.Task.Inputfilelist }
     * 
     */
    public SegmentProcessOrder.Task.Inputfilelist createProcessOrderTaskInputfilelist() {
        return new SegmentProcessOrder.Task.Inputfilelist();
    }

    /**
     * Create an instance of {@link SegmentProcessOrder.Task.Outputfilelist }
     * 
     */
    public SegmentProcessOrder.Task.Outputfilelist createProcessOrderTaskOutputfilelist() {
        return new SegmentProcessOrder.Task.Outputfilelist();
    }

    /**
     * Create an instance of {@link SegmentProcessOrder.Task.Params }
     * 
     */
    public SegmentProcessOrder.Task.Params createProcessOrderTaskParams() {
        return new SegmentProcessOrder.Task.Params();
    }

}
