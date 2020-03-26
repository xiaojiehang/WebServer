package cn.xjh3.Net.WebServer02;

import cn.xjh3.Net.TCP.Server;
import sun.security.krb5.internal.crypto.Aes128;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.*;

/*封装请求信息
* 获取方法(GET/POST等)，URL以及请求参数*/
public class Request {
    private String requestinfo;
    private String method;//方法
    private String Url;
    private String queryStr;//请求参数
    private Map<String, List<String>> parameterMap;//封装请求参数，因为可能一个key值要有不同的value值，所有value是个容器

    public Request(InputStream is){
        parameterMap=new HashMap<String, List<String>>();
        byte[] datas = new byte[1024 * 10 * 10];
        try {
            is.read(datas);
            this.requestinfo = new String(datas, 0, datas.length);
            System.out.println(requestinfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Request(Socket client) throws IOException {
     this(client.getInputStream());//构造器相互调用
        parseRequest();
    }

    private void parseRequest(){
        System.out.println("获取请求方式-----------------------------");
        int idx1=requestinfo.indexOf("/");
        this.method=requestinfo.substring(0,idx1).trim();
        System.out.println(method);

        System.out.println("获取请求URL-----------------------------");
        int idx2=requestinfo.indexOf("HTTP");
        int idx3=requestinfo.indexOf("?");
       if(idx3>0&&idx3<idx2){
        Url=requestinfo.substring(idx1+1,idx3).trim();}else {Url=requestinfo.substring(idx1+1,idx2).trim();}
        System.out.println(Url);
        System.out.println("获取请求参数-----------------------------");
        if(idx3>0&&idx3<idx2){
            queryStr=requestinfo.substring(idx3+1,idx2).trim();}else{queryStr=null;}
        if(method.equals("POST")){
            queryStr=requestinfo.substring(requestinfo.lastIndexOf("\r\n")).trim();
        }
        System.out.println(queryStr);
      if(queryStr!=null)  convertMap();
    }


    private void convertMap(){//处理请求参数，将其转换为Map
        //请求参数格式：name1=curry&name2=Ayesha
        String[] keyValues=this.queryStr.split("&");//以&符号分割为name1=curry
     for(String kv:keyValues){//以=号分割出key与value
        String kv1[]=kv.split("=");
        String key=kv1[0];//等号前面为key
        String value=kv1[1];//等号后为value
         //System.out.println(key+"----"+value);
         if(!parameterMap.containsKey(key)){
             parameterMap.put(key,new ArrayList<String>());//第一次出现当前key值
         }
         parameterMap.get(key).add(value);//get(key)得到容器v，往里面添值

     }
    }

    public String[] getParameterValues(String key){
        List<String> list=parameterMap.get(key);
        if(list==null){return null;}

        return  list.toArray(new String[0]);
    }

    public String getParameterSingleValues(String key){
       String[] s=getParameterValues(key);
        return s==null?null:s[0];
    }


    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return Url;
    }

    public String getQueryStr() {
        return queryStr;
    }

}
