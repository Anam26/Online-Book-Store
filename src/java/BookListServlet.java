import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        String sql="SELECT * FROM books";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/booksdata";
            Connection con = DriverManager.getConnection(url, "root", "root");
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            ResultSetMetaData rsmd=rs.getMetaData();
            int n=rsmd.getColumnCount();
            out.println("<html>");
            out.println("<body>");
            out.println("<h3>Book-List</h3>");
            out.println("<hr>");
            out.println("<table border=2>");
            out.println("<tr>");
            for(int i=1;i<=n;i++){
                out.println("<th>");
                out.println(rsmd.getColumnName(i).toUpperCase());
                out.println("</th>");
            }
            out.println("</tr>");
            while(rs.next()){
                out.println("<tr>");
                for(int i=1; i<=n; i++){
                    out.println("<td>");
                    out.println(rs.getString(i));
                    out.println("</td>");
                }
                String id=rs.getString(1);
                out.println("<td><a href=RemoveBook?code="+id+">remove</a></td>");
                out.println("<td><a href=UpdateForm?code="+id+">update</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<hr>");
            out.println("<a href=adminpage.jsp>Admin-Page</a>");
            out.println("</body>");
            out.println("</html>");
            
            con.close();
        }catch(Exception e){
            out.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
