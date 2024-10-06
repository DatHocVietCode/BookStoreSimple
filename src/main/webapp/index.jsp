<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- Thêm thẻ viewport ở đây -->
    <title>Dat's Book Store</title>
    <link rel="stylesheet" href="./assets/style.css">
</head>
<body>
    <h1 class="header">Dat's Book Store</h1>
    <div class="table_container">
      <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th></th>
        </tr>
        
        <%
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Tải lớp Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String dbURL = "jdbc:mysql://localhost:3306/BookStore";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(dbURL, username, password);

            if (connection != null) {
            	System.out.print("Kết nối thành công");
                // Thực hiện truy vấn
                statement = connection.createStatement();
                String sql = "SELECT * FROM Book";
                resultSet = statement.executeQuery(sql);

                // Hiển thị dữ liệu
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
        %>
                    <tr>
				    <form action="servlet_get_product" method="post">
				        <td>
				            <input type="hidden" name="id" value="<%= id %>"><%= id %>
				        </td>
				        <td>
				            <input type="hidden" name="name" value="<%= name %>"><%= name %>
				        </td>
				        <td>
				            <input type="hidden" name="price" value="<%= price %>"><%= price %>
				        </td>
				        <td>
				            <input type="submit" value="Add To Cart">
				        </td>
				    </form>
				</tr>

        <%
                }
            } else {
                System.out.print("Không kết nối được");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // In lỗi ra console
        } finally {
            // Đóng kết nối và tài nguyên
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        %>
    </table>
    </div>
  
</body>
</html>
