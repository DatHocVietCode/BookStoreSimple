
import java.net.URLEncoder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
/**
 * Servlet implementation class servlet_payment
 */
public class servlet_payment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet_payment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private Connection createConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/BookStore";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(dbURL, username, password);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = createConnection(); // Tạo kết nối tới cơ sở dữ liệu
            String cmd = "DELETE FROM Cart;"; // Câu lệnh SQL để xóa toàn bộ dữ liệu trong bảng Cart
            PreparedStatement statement = connection.prepareStatement(cmd);
            statement.executeUpdate(); // Thực thi câu lệnh

         // Gửi thông báo để hiển thị trên trang cart.jsp
        

         // Trong servlet của bạn
         String message = "Đã thanh toán thành công!";
         String encodedMessage = URLEncoder.encode(message, "UTF-8");
         response.sendRedirect("cart.jsp?message=" + encodedMessage);

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console nếu có
        } finally {
            // Đóng kết nối để tránh rò rỉ tài nguyên
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
