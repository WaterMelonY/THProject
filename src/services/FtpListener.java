package services;

import bean.ProcessBean;
import bean.finsh.xsd.FinshInterfaceFile;
import process.ProcessXmlCreate;
import socket.FileUpLoadServer;
import util.XmlToBean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * Created by WaterMelon on 2018/4/20.
 * ftp监听，监听ftp路径，然后根据文件后缀名，确定进行的操作
 */
public class FtpListener {
    private WatchService watcher;

    private Path path;

    public FtpListener(Path path) throws IOException {
        this.path = path;
        watcher = FileSystems.getDefault().newWatchService();
        this.path.register(watcher, OVERFLOW, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    }

    public void handleEvents() throws InterruptedException {
        // start to process the data files
        while (true) {
            // start to handle the file change event
            final WatchKey key = watcher.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                // get event type
                final WatchEvent.Kind<?> kind = event.kind();

                // get file name
                @SuppressWarnings("unchecked") final WatchEvent<Path> pathWatchEvent = (WatchEvent<Path>) event;
                final Path fileName = pathWatchEvent.context();

                if (kind == ENTRY_CREATE) {
                    System.out.println("create");
                    System.out.println(fileName);
                    //判断文件类型 然后根据文件类型 判断是数据传输申请 还是传输结束通知。
                    //如果是传输申请通知，开启一个socket接收文件
                    // 说明点1
                    //这种通过判断 文件大小 和 文件最后修改时间 处理方式只限于 Unix 操作系统，原因在上面已经说过了。
                    //对于 windows 系统，应该在产生 ENTRY_CREATE 这个事件后，继续监听，直到产了一个该文件的“ENTRY_MODIFY”事件，或者 ENTRY_DELETE 事件，才说明该文件是传输完毕或者被取消传输了。
                    //内嵌的 Thread 最好另建一个 类，这样看起来会比较容易理解。
                    // create a new thread to monitor the new file
                    new Thread(new Runnable() {
                        public void run() {
                            String fileAllPath = path.toFile().getAbsolutePath() + "/" + fileName;
                            File file = new File(path.toFile().getAbsolutePath() + "/" + fileName);
                            boolean exist;
                            long size = 0;
                            long lastModified = 0;
                            int sameCount = 0;
                            while (exist = file.exists()) {
                                //如果文件大小三秒钟没有发生变化，认为文件传输成功。
                                if (size == file.length() && lastModified == file.lastModified()) {
                                    if (++sameCount >= 3) {
                                        break;
                                    }
                                } else {
                                    size = file.length();
                                    lastModified = file.lastModified();
                                }
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }
                            // if the new file was cancelled or deleted
                            if (!exist) {
                                return;
                            } else {
                                // do something ...
                                //判断类型 是传输申请文件还是传输完毕文件
                                if(file.isFile()&&file.getName().endsWith(".xml")){
                                    FileUpLoadServer fileUpLoadServer = null;
                                    if(file.getName().indexOf("1")!=-1){
                                        System.out.println("开始传输文件");
                                        try {
                                            //传输申请文件入库
                                            fileUpLoadServer = new FileUpLoadServer(8888);
                                            fileUpLoadServer.load();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }else if(file.getName().indexOf("2")!=-1){
                                        //将文件入库
                                        ProcessBean processBean = new ProcessBean();
                                        FinshInterfaceFile finshInterfaceFile = new XmlToBean<FinshInterfaceFile>(new FinshInterfaceFile()).getObjByOrderName(file);
                                        processBean.setFinshInterfaceFile(finshInterfaceFile);
                                        //从数据库中查询出传输申请
                                        //将传输申请和传输完成通知赋值给processBean
                                        //因目前是文件传输会判断文件是否接收完毕，所以不在此处进行关闭socket 如果传输文件返回的是success直接开启任务流程
                                        new Thread(()->{
                                            ProcessXmlCreate.createSegmentOrder(processBean);
                                        });
                                    }else {
                                        System.out.println("非xml文件不做处理");
                                    }
                                }
                            }
                        }
                    }).start();
                } else if (kind == ENTRY_DELETE) {
                    System.out.println("delete");
                    // todo
                } else if (kind == ENTRY_MODIFY) {
//                    System.out.println("modify");
                    // todo
                } else if (kind == OVERFLOW) {
                    System.out.println("OVERFLOW");
                    // todo
                }
            }

            // IMPORTANT: the key must be reset after processed
            if (!key.reset()) {
                return;
            }
        }
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        File file = new File("C:\\Users\\WaterMelon\\Desktop\\xmlFile\\finsh.xml");
        FinshInterfaceFile finshInterfaceFile = new XmlToBean<FinshInterfaceFile>(new FinshInterfaceFile()).getObjByOrderName(file);
        List<Object> a = finshInterfaceFile.getFileHeadOrFileBody();

//        FinshInterfaceFile.FileHead fileHead = (FinshInterfaceFile.FileHead)a.get(0);
//        FinshInterfaceFile.FileBody fileBody = (FinshInterfaceFile.FileBody)a.get(1);
//        System.out.println(fileBody.getTrPlanID());
//        System.out.println(fileHead.getCreationTime());
        Field[] fields = finshInterfaceFile.getClass().getDeclaredFields();
        for (Field field: fields) {
            System.out.println(field);
            String fieldType = field.getAnnotatedType().getType().getTypeName();

            System.out.println(fieldType);
            if(fieldType.indexOf("java.util.List")!=-1){
                System.out.println("islist");
                //获取字段上的注解
                boolean fieldHasAnno = field.isAnnotationPresent(XmlElements.class);
                if(fieldHasAnno){
                    XmlElements xmlElements = field.getAnnotation(XmlElements.class);
                    //输出注解属性
                    XmlElement[] xmlElements1 = xmlElements.value();
                    for (XmlElement xmlElement : xmlElements1){
                        String name = xmlElement.name();
                        Class type = xmlElement.type();
                        System.out.println(field.getName() + " name = " + name + ", type = " + type);
                    }
                }
            }
        }

    }
}
