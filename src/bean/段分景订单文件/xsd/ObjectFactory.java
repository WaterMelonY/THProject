package bean.段分景订单文件.xsd;//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2018.05.07 ʱ�� 08:52:44 AM CST 
//



import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the �η־������ļ�.xsd package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: �η־������ļ�.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SceneProcessOrder }
     * 
     */
    public SceneProcessOrder createProcessOrder() {
        return new SceneProcessOrder();
    }

    /**
     * Create an instance of {@link SceneProcessOrder.Task }
     * 
     */
    public SceneProcessOrder.Task createProcessOrderTask() {
        return new SceneProcessOrder.Task();
    }

    /**
     * Create an instance of {@link NewDataSet }
     * 
     */
    public NewDataSet createNewDataSet() {
        return new NewDataSet();
    }

    /**
     * Create an instance of {@link SceneProcessOrder.Task.Inputfilelist }
     * 
     */
    public SceneProcessOrder.Task.Inputfilelist createProcessOrderTaskInputfilelist() {
        return new SceneProcessOrder.Task.Inputfilelist();
    }

    /**
     * Create an instance of {@link SceneProcessOrder.Task.Outputfilelist }
     * 
     */
    public SceneProcessOrder.Task.Outputfilelist createProcessOrderTaskOutputfilelist() {
        return new SceneProcessOrder.Task.Outputfilelist();
    }

    /**
     * Create an instance of {@link SceneProcessOrder.Task.Params }
     * 
     */
    public SceneProcessOrder.Task.Params createProcessOrderTaskParams() {
        return new SceneProcessOrder.Task.Params();
    }

}
