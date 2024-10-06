

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import beans.*;
/**
 * Servlet implementation class servlet_get_product
 */
public class servlet_get_product extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet_get_product() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        if (session == null) {
            session = request.getSession();
        }
        
        String id = request.getParameter("id");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // Tải lớp Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/BookStore";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(dbURL, username, password);
            
            if (connection != null) {
            	Boolean checkExist = checkExist(connection, id);
            	if (checkExist)
            	{
            		  String cmd = "UPDATE Cart SET number = number + 1 WHERE id = ?";
                      preparedStatement = connection.prepareStatement(cmd);
                      preparedStatement.setString(1, id); // Thiết lập giá trị cho id

                      // Thực hiện câu lệnh cập nhật
                      int rowsAffected = preparedStatement.executeUpdate();
                      //System.out.println("Số bản ghi đã cập nhật: " + rowsAffected);
            	}
            	else {
					String cmd = "Insert into Cart values (?, 1)";
					preparedStatement = connection.prepareStatement(cmd);
					preparedStatement.setString(1, id);
				    int rowsAffected = preparedStatement.executeUpdate();
					
				}
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Chuyển hướng tới trang cart.jsp
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
//        dispatcher.forward(request, response);
        response.sendRedirect("cart.jsp");
    }

	

    private Boolean checkExist(Connection connection, String id) {
        String cmd = "SELECT * FROM Cart WHERE id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exists = false;

        try {
            preparedStatement = connection.prepareStatement(cmd);
            preparedStatement.setString(1, id);  // Truyền tham số id vào câu lệnh SQL

            resultSet = preparedStatement.executeQuery();

            // Kiểm tra nếu ResultSet có kết quả trả về thì id đã tồn tại
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên (ResultSet, PreparedStatement)
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exists;  // Trả về true nếu tồn tại, false nếu không
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
