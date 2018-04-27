package util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * Created by WaterMelon on 2018/4/27.
 */
public class XmlToBean<T> {
    private T var;
    public XmlToBean(T var){
        this.var = var;
    }

    public T getObjByOrderName(String orderName){
        String xmlStr = PublicUtil.getXMLByOrderName(orderName);
        ByteArrayInputStream stream = new ByteArrayInputStream(xmlStr.getBytes());
        JAXBContext jaxbContext = null;
        Object a= null;
        try {
            jaxbContext = JAXBContext.newInstance(var.getClass());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            a = jaxbUnmarshaller.unmarshal(stream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return (T)a;
    }

}
