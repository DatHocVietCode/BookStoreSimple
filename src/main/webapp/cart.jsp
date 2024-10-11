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
	<title>Insert title here</title>
	 <link rel="stylesheet" href="./assets/style.css">
	 <script>
		// Hàm hiển thị alert nếu có thông báo
		function showAlert() {
		    var message = "<%= request.getParameter("message") != null ? request.getParameter("message") : "" %>";
		    if (message) {
		        alert(message);
		        var newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
	            window.history.replaceState({}, document.title, newUrl); // Cập nhật URL mà không tải lại trang
		    }
		}

	</script>
</head>
<body>
<body onload="showAlert()"> <!-- Gọi hàm khi trang được tải -->
	<h1 class="header">Cart</h1>
	<div class="table_container">
		<table>
			<tr>
	            <th>ID</th>
	            <th>Name</th>
	            <th>Price</th>
	            <th>Number</th>
	            <th>Price per product</th>
	            <th>Increase</th>
	            <th>Decrease</th>
	            <th>Remove product</th>
		    </tr>
	        <%
	        Connection connection = null;
	        Statement statement = null;
	        ResultSet resultSet = null;
	
	        try 
	        {
	            // Tải lớp Driver
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            
	            String dbURL = "jdbc:mysql://localhost:3306/BookStore";
	            String username = "root";
	            String password = "root";
	            connection = DriverManager.getConnection(dbURL, username, password);
	
	            if (connection != null) 
	            {
	            	System.out.print("Kết nối thành công");
	                // Thực hiện truy vấn
	                statement = connection.createStatement();
	                String sql = "SELECT * FROM Book join Cart on Book.id = Cart.id";
	                resultSet = statement.executeQuery(sql);
					while (resultSet.next())
					{
						  int id = resultSet.getInt("id");
		                    String name = resultSet.getString("name");
		                    double price = resultSet.getDouble("price");
		                    int number = resultSet.getInt("number");
		                    double pricepnumber = number * price;
		             	%>
		             		 <tr>
							        <td><%= id %></td>
							        <td><%= name %></td>
							        <td><%= price %></td>
							        <td><%= number %></td>
							        <td><%= pricepnumber %></td>
							         <td>
						            <!-- Tạo form với nút tăng số lượng -->
						            <form action="servlet_update_number" method="post">
						                <input type="hidden" name="id" value="<%= id %>">
						                <input type="submit" value="+">
						            </form>
	        						</td>			
	        						<td>
	        						<form action="servlet_decrease_number" method="post">
	        							<input type="hidden" name="id" value="<%= id %>">
						                <input type="submit" value="-">
	        						</form>
	        						</td>	
	        						<td>
	        							<form action="servlet_decrease_number" method="post">
										    <input type="hidden" name="isdelete" value="true"> <!-- Thay "isdelete" bằng "true" hoặc giá trị logic -->
										    <input type="hidden" name="id" value="<%= id %>">
										    <input type="submit" value="Remove this product">
										</form>
	        						</td>	
	        					
							    </tr>
		             	<% 
					}
	            }
	        }
	        catch (SQLException e) {
	            e.printStackTrace(); // In lỗi ra console
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace(); // In lỗi ra console
	        }
	        finally {
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
	<div class="table_container">
			<form action="servlet_back_shop" method="post">
				<input type="submit" value = "Back to Shop">
			</form>
			<form action = "servlet_payment" method="post">
				<input type="submit" value = "Pay">
			</form>
	</div>
</body>
</html>