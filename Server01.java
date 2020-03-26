package cn.xjh3.Net.WebServer02;

import cn.xjh3.Net.TCP.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Date;

/*目标：使用ServerSocket建立与浏览器连接，获得请求协议*/
public class Server01 {
    private ServerSocket serverSocket;
    private  boolean isrunning;//分发器控制标志
    public static void main(String[] args) {
        Server01 s = new Server01();
        s.start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            isrunning=true;
            Serverrunning();//建立连接
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
            stop();
        }
    }


    public void Serverrunning() {
        while(isrunning){
        try {
            Socket client = serverSocket.accept();
            System.out.println("一个客户端建立了连接");
            new Thread(new Dispatcher(client)).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端连接失败");
        }
    }}

    public void stop(){
        isrunning=false;
        try {
            serverSocket.close();
            System.out.println("服务器关闭");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




