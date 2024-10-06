

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
/**
 * Servlet implementation class servlet_update_number
 */
public class servlet_update_number extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet_update_number() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Kết nối cơ sở dữ liệu
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/BookStore";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(dbURL, username, password);

            // Câu lệnh SQL để tăng số lượng
            String sql = "UPDATE Cart SET number = number + 1 WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(id));
            
            // Thực thi câu lệnh
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            if (statement != null) try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        // Sau khi cập nhật, chuyển hướng trở lại trang hiển thị giỏ hàng
        response.sendRedirect("cart.jsp");
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
