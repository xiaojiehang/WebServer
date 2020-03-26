package cn.xjh3.Net.WebServer02;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable {
    private Socket client;
    private  Request r1;
    private  Response r2;
    public Dispatcher(Socket client){//传入服务器接收的插座
        this.client=client;
        try {
            r1=new Request(client);
            r2=new Response(client);//文件正文
        } catch (IOException e) {
            e.printStackTrace();
            release();//如果创建请求协议失败，直接释放资源
        }

    }
    @Override
    public void run() {
        try {
            Servlet servlet = Webapp.getServletfromXML(r1.getUrl());
            if (servlet != null) {//如果找到xml文件的类
                servlet.service(r1, r2);//通过反射得到的servlet方法，调用指定函数，构建响应正文
                r2.pushToBrowser(200);//构建响应协议成功
            } else {
                r2.pushToBrowser(404);//若在xml文件里找不到类，则404错误}
            }
        } catch (IOException e) {
            try {
                r2.pushToBrowser(505);//服务器错误
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        release();//响应一次就释放一次资源(短连接),否则Webapp类被一个线程一直占用，无法连续接收客户端请求
    }

    private void release(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
