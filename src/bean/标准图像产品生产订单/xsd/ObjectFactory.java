package bean.标准图像产品生产订单.xsd;//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2018.05.07 ʱ�� 08:52:43 AM CST 
//



import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ��׼ͼ���Ʒ��������.xsd package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ��׼ͼ���Ʒ��������.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProductProcessOrder }
     * 
     */
    public ProductProcessOrder createProcessOrder() {
        return new ProductProcessOrder();
    }

    /**
     * Create an instance of {@link ProductProcessOrder.Task }
     * 
     */
    public ProductProcessOrder.Task createProcessOrderTask() {
        return new ProductProcessOrder.Task();
    }

    /**
     * Create an instance of {@link ProductProcessOrder.Task.Params }
     * 
     */
    public ProductProcessOrder.Task.Params createProcessOrderTaskParams() {
        return new ProductProcessOrder.Task.Params();
    }

    /**
     * Create an instance of {@link ProductProcessOrder.Task.Inputfilelist }
     * 
     */
    public ProductProcessOrder.Task.Inputfilelist createProcessOrderTaskInputfilelist() {
        return new ProductProcessOrder.Task.Inputfilelist();
    }

    /**
     * Create an instance of {@link NewDataSet }
     * 
     */
    public NewDataSet createNewDataSet() {
        return new NewDataSet();
    }

    /**
     * Create an instance of {@link ProductProcessOrder.Task.Outputfilelist }
     * 
     */
    public ProductProcessOrder.Task.Outputfilelist createProcessOrderTaskOutputfilelist() {
        return new ProductProcessOrder.Task.Outputfilelist();
    }

    /**
     * Create an instance of {@link ProductProcessOrder.Task.Params.ProdParams }
     * 
     */
    public ProductProcessOrder.Task.Params.ProdParams createProcessOrderTaskParamsProdParams() {
        return new ProductProcessOrder.Task.Params.ProdParams();
    }

    /**
     * Create an instance of {@link ProductProcessOrder.Task.Inputfilelist.MasterScene }
     * 
     */
    public ProductProcessOrder.Task.Inputfilelist.MasterScene createProcessOrderTaskInputfilelistMasterScene() {
        return new ProductProcessOrder.Task.Inputfilelist.MasterScene();
    }

}
