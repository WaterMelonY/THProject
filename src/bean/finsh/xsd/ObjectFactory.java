//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.8-b130911.1802 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2018.05.07 ʱ�� 08:52:47 AM CST 
//


package bean.finsh.xsd;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the finsh.xsd package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: finsh.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FinshInterfaceFile }
     * 
     */
    public FinshInterfaceFile createInterfaceFile() {
        return new FinshInterfaceFile();
    }

    /**
     * Create an instance of {@link FinshInterfaceFile.FileBody }
     * 
     */
    public FinshInterfaceFile.FileBody createInterfaceFileFileBody() {
        return new FinshInterfaceFile.FileBody();
    }

    /**
     * Create an instance of {@link FinshInterfaceFile.FileHead }
     * 
     */
    public FinshInterfaceFile.FileHead createInterfaceFileFileHead() {
        return new FinshInterfaceFile.FileHead();
    }

    /**
     * Create an instance of {@link FinshInterfaceFile.FileBody.FredInfo }
     * 
     */
    public FinshInterfaceFile.FileBody.FredInfo createInterfaceFileFileBodyFredInfo() {
        return new FinshInterfaceFile.FileBody.FredInfo();
    }

    /**
     * Create an instance of {@link FinshInterfaceFile.FileBody.SdrtuInfo }
     * 
     */
    public FinshInterfaceFile.FileBody.SdrtuInfo createInterfaceFileFileBodySdrtuInfo() {
        return new FinshInterfaceFile.FileBody.SdrtuInfo();
    }

}
