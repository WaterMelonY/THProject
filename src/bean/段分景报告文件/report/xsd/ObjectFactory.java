package bean.段分景报告文件.report.xsd;//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2018.05.07 ʱ�� 08:52:44 AM CST 
//


import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the �η־������ļ�.report.xsd package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: �η־������ļ�.report.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SceneReportProcessOrder }
     * 
     */
    public SceneReportProcessOrder createProcessOrder() {
        return new SceneReportProcessOrder();
    }

    /**
     * Create an instance of {@link SceneReportProcessOrder.Task }
     * 
     */
    public SceneReportProcessOrder.Task createProcessOrderTask() {
        return new SceneReportProcessOrder.Task();
    }

    /**
     * Create an instance of {@link SceneReportProcessOrder.Task.Scenes }
     * 
     */
    public SceneReportProcessOrder.Task.Scenes createProcessOrderTaskScenes() {
        return new SceneReportProcessOrder.Task.Scenes();
    }

    /**
     * Create an instance of {@link NewDataSet }
     * 
     */
    public NewDataSet createNewDataSet() {
        return new NewDataSet();
    }

    /**
     * Create an instance of {@link SceneReportProcessOrder.Task.Scenes.Scene }
     * 
     */
    public SceneReportProcessOrder.Task.Scenes.Scene createProcessOrderTaskScenesScene() {
        return new SceneReportProcessOrder.Task.Scenes.Scene();
    }

}
