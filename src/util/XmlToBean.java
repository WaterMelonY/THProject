package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParser;
import java.io.*;

/**
 * Created by WaterMelon on 2018/4/27.
 */
public class XmlToBean<T> {
    private T var;
    public XmlToBean(T var){
        this.var = var;
    }

    /**
     * 将xml转换成JavaBean
     * @param xmlStr
     * @return
     */
    public T getObjByOrderName(String xmlStr){
//        String xmlStr = PublicUtil.getXMLByOrderName(orderName);
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

    public T getObjByOrderName(File file){
        JAXBContext jaxbContext = null;
        Object a= null;
        try {
            jaxbContext = JAXBContext.newInstance(var.getClass());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            a = jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return (T)a;
    }


    /**
     * JavaBean转换成xml 默认utf-8
     * @param obj
     * @return
     *    
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    /**
     * JavaBean转换成xml
     * @param obj
     * @param encoding
     * @return
     *    
     */
    public static String convertToXml(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            //是否开启自动换行还是一行xml
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将JavaBean转为Document对象
     * @param obj
     * @return
     */
    public static Document objToDoc(Object obj){
        String xmlStr = convertToXml(obj);
        Document doc =null;
        try {
            doc = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 将JavaBean 按照指定编码格式转为Document对象
     * @param obj
     * @param encoding
     * @return
     */
    public static Document objToDoc(Object obj,String encoding){
        String xmlStr = convertToXml(obj,encoding);
        Document doc =null;
        try {
            doc = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
