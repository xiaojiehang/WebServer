package cn.xjh3.Net.WebServer02;

import java.io.IOException;

public class loginServlet implements Servlet{
/*登录小脚本*/
    @Override
    public void service(Request r1, Response r2) throws IOException {
        r2.print("<html>");
        r2.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        r2.print("<head>");
        r2.print("<title>");
        r2.print("服务器响应成功");
        r2.print("服务器小脚本1");
        r2.print("</title>");
        r2.print("</head>");
        r2.print("<body>");
        r2.print("欢迎回来:"+r1.getParameterSingleValues("uname"));
        r2.print("</body>");
        r2.print("</html>");
        System.out.println("-----------响应结束------------"+"\r\n");
    }
}
