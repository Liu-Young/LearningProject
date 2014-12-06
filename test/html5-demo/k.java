import java.io.*;
import javax.servlet.*;
        import javax.servlet.http.*;

public class HelloServlet extends HttpServlet
{
    public void service(HttpServletRequest request,
                        HttpServletResponse response)
            throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("学号：XXXXXXXXXXXXXX");
        out.println("姓名：刘子扬");
        out.println("系统时间："+ new java.util.Date() + "");
        out.println("</body>");
        out.println("</html>");
    }
}