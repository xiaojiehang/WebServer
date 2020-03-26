package cn.xjh3.Net.WebServer02;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
/*封装返回响应协议*/
public class Response {
    private BufferedWriter bw;
    private StringBuilder headInfo;
    private final String BLANK=" ";
    private final String CRLF="\r\n";
    private long size=0;//协议正文大小
    private StringBuilder content;//协议正文
    private Response(){
        headInfo=new StringBuilder();
        content=new StringBuilder();
    }
    public Response(Socket client)  {
        this();//重载无参构造函数
        try {
           bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void createHeadInfo(int code){//构造响应协议头
    headInfo.append("HTTP/1.1").append(BLANK);
    headInfo.append(code).append(BLANK);
    switch (code){
        case 200: headInfo.append("OK").append(CRLF);
        break;
        case 404: headInfo.append("NOT FOUND").append(CRLF);
        break;
        case 505: headInfo.append("SERVER ERROR").append(CRLF);
        break;
    }

    //响应头(最后一行有空行)
    headInfo.append("Date:").append(new Date()).append(CRLF);
    headInfo.append("Server:").append("WebServer02/0.0.1;charset=UTF8").append(CRLF);
    headInfo.append("Content-type:text/html").append(CRLF);
    headInfo.append("Content-length:").append(size).append(CRLF);
    headInfo.append(CRLF);//与正文直接有个空行

}


    public Response print(String info){//传入响应协议正文，流操作，返回自身则可以 print.print反复依次调用
        content.append(info);
        size+=info.getBytes().length;
        return this;//返回本身实例
}
    public Response println(String info){//换行的传入正文操作
        content.append(info).append(CRLF);
        size+=(info+CRLF).getBytes().length;
        return this;//返回本身实例
    }

    public void pushToBrowser(int code) throws IOException {
        createHeadInfo(code);//传入状态码，生产响应协议头
        bw.append(headInfo);//Writer内的方法
        bw.append(content);
        bw.flush();
    }
}
