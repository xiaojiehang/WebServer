package cn.xjh3.Net.WebServer02;

import java.io.IOException;

/*服务器小脚本接口，解耦了业务代码（登录/注册等业务）*/
public interface Servlet {
    void service(Request r1,Response r2) throws IOException;
}
