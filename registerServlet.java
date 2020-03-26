package cn.xjh3.Net.WebServer02;

public class registerServlet implements Servlet {
    @Override
    public void service(Request r1, Response r2) {

        r2.print("注册成功");


    }
}