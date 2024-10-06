import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/servlet_decrease_number")
public class servlet_decrease_number extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        
        if (id == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
            return;
        }

        try (Connection connection = createConnection()) {
        	 decreaseQuantity(connection, id);
            // Kiểm tra số lượng hiện tại
            if (if0Left(connection, id)) {
                deleteProduct(connection, id);
            } 
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }

        response.sendRedirect("cart.jsp");
    }

    private void decreaseQuantity(Connection connection, String id) throws SQLException {
        String sql = "UPDATE Cart SET number = number - 1 WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(id));
            statement.executeUpdate();
        }
    }

    private boolean if0Left(Connection connection, String id) throws SQLException {
        String cmd = "SELECT number FROM Cart WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("number") <= 0;
            }
        }
        return false;
    }

    private void deleteProduct(Connection connection, String id) throws SQLException {
        String cmd = "DELETE FROM Cart WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Connection createConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/BookStore";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(dbURL, username, password);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng tới doGet
    	String isDelete = request.getParameter("isdelete");
    	
    	if (isDelete == null) {
    		 doGet(request, response);
    		 return;
    	}
    	
    	String id = request.getParameter("id");
    	try {
			Connection connection = createConnection();
			deleteProduct(connection, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally {
    		response.sendRedirect("index.jsp");
    		
		}
    }
}
