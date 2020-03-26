package cn.xjh3.Net.WebServer02;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class Webapp {
    private static Webcontext context;
   static{
        SAXParserFactory factory=SAXParserFactory.newInstance();
        SAXParser Parse= null;
        try {
            Parse = factory.newSAXParser();
            Handler1 handler=new Handler1();
            Parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("cn/xjh3/Net/WebServer02/web.xml"),handler);
             context=new Webcontext(handler.getEntitys(),handler.getMappings());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Servlet getServletfromXML(String url) {//上下文类处理Handler得到的数据
       if(context==null){
           System.out.println("context is null");
       }
        String classname= context.getclz("/"+url);
        Class clz= null;
        try {
            clz = Class.forName(classname);
            Servlet servlet=(Servlet) clz.getConstructor().newInstance();//使用反射得到类名
            return servlet;
        } catch (Exception e) {
        }
        return null;

    }
}



class Handler1 extends DefaultHandler {//继承DefaultHandler类，必要条件

    private List<Entity> Entitys=new ArrayList<>();//定义对象容器
    private List<Mapping> Mappings=new ArrayList<>();
    private Mapping mapping;
    private Entity entity;
    private String tag;
    private boolean ismapping=false;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
   //    System.out.println(qName+"---->解析开始");
        tag=qName;
        if(qName=="servlet"){
            entity=new Entity();
        }else if(qName=="servlet-mapping"){
            mapping=new Mapping();
            ismapping=true;//读取servlet-mapping
        }

    }



    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
      // System.out.println(qName+"---->解析结束");
        if(qName.equals("servlet")){Entitys.add(entity);}//把类加入容器内
        if(qName.equals("servlet-mapping")){Mappings.add(mapping);}
        tag=null;
    }



    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(tag!=null){//处理了，解析完一个标题后空内容的情况
            String contents=new String(ch,start,length).trim();//trim去掉空
            if(ismapping){//操作servlet-mapping
                if(tag=="servlet-name"){mapping.setName(contents);}
                else if(tag=="url-pattern"){mapping.addPatterns(contents);}

            }else {//操作servlet
                if(tag=="servlet-name"){entity.setName(contents);}
                else if(tag=="servlet-class"){entity.setClz(contents);}
            }
        }}

    @Override
    public void startDocument() throws SAXException {
        System.out.println("开始解析xml");
    }

    public List<Entity> getEntitys() {
        return Entitys;
    }

    public List<Mapping> getMappings() {
        return Mappings;
    }
}
