package wlsconntest;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.sql.DataSource;

@WebServlet(name = "conn_test", urlPatterns = { "/conn_test" })
public class conn_test extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**Process the HTTP doGet request.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String message = "No Errors";
        try {

            
            //Statement stmt = conn.createStatement();
            //stmt.execute("select * from brian1");
            //ResultSet rs = stmt.getResultSet();
            //conn.close();
        } catch (Exception e) {
            
            message = e.getMessage();
            
        }

        String query =
              "select thedate, msg from conn_test";
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
            
            
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        
        // Nobody said that this code was GOING TO LOOK GOOD!
        out.println("<html>");
        out.println("<head><title>GetEmpInfo</title></head>");
        out.println("<body>");
        
        try {
            
              InitialContext ctx = new InitialContext();
              DataSource ds = (DataSource) ctx.lookup("jdbc/connection3");
              Connection conn = ds.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(query);

                out.println("<table border=1 width=50%>");
                out.println("<tr><th width=75%>Date</th><th width=25%>Message</th></tr>");

        /* Loop through the results. Use the ResultSet getString() and       */
        /* getInt() methods to retrieve the individual data items.           */
                int count=0;
                while (rs.next()) {
                 count++;
                 out.println("<tr><td>" + df.format(rs.getDate(1)) + "</td><td>" +rs.getString(2) +
                            "</td></tr>");
               
                }
                 out.println("</table>"); 
                 out.println("<h3>" + count + " rows retrieved</h3>");
                 
              rs.close();
              stmt.close();
            } catch (SQLException se) {
              se.printStackTrace(out);
              } catch (Exception e) {
                  e.printStackTrace(out);
              }

            out.println("</body></html>");

        
        out.close();
    }
}
