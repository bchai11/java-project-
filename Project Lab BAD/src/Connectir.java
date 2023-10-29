import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Connectir {
	public static String currentUser;
	public static String userRole;
	public static String username;
	
	
	 public static boolean checkUser(String username, String password) {
	    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
	     String query = "SELECT * FROM `user` WHERE username='" + username + "' AND password='" + password + "'";
	     	Statement statement = connection.createStatement();
	      ResultSet resultSet = statement.executeQuery(query);
	      if (resultSet.next()) {
	    	  currentUser = resultSet.getString("userID");
	    	  userRole = resultSet.getString("role");
	    	  Connectir.username = username;
	    	  return true;
	      }
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	    return false;
	 }
	 
	 public static String getEligibleUserID() {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "SELECT * FROM `user` ORDER BY `user`.`userID` DESC";
		     Statement statement = connection.createStatement();
		     ResultSet resultSet = statement.executeQuery(query);
		      if (resultSet.next()) {
		    	  String userID = resultSet.getString("userID");
		    	  int max = Integer.parseInt(userID.substring(2)) + 1;
		    	  return String.format("US%03d", max);
		      }
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 return "No Internet";
	 }
	 
	 public static void registerUser(String userID, String username, String password, String gender, String email, String phoneNumber, int age, String role) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "INSERT INTO `user` (userID, username, password, gender, email, phoneNumber, age, role) VALUES (?,?,?,?,?,?,?,?)";
		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.setString(1, userID);
		     statement.setString(2, username);
		     statement.setString(3, password);
		     statement.setString(4, gender);
		     statement.setString(5, email);
		     statement.setString(6, phoneNumber);
		     statement.setInt(7, age);
		     statement.setString(8, role);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
	 }
	 
	 public static ObservableList<User> fetchUser() {
		 ObservableList<User> userList = FXCollections.observableArrayList();
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "SELECT * FROM `user`";
		     Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery(query);
		     while (rs.next()) {
		    	 userList.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7)));
		     }
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 return userList;
	 }
	 
	 public static void updateUser(String userID, String gender, String email, String phoneNumber, int age) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "UPDATE `user` SET gender ='"+gender+"', email = '"+email+"', phoneNumber = '"+phoneNumber+"', age = '"+age+"' WHERE userID = '" + userID + "'";
		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
	 }
	 
	 public static void deleteUser(String userID) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "DELETE FROM `user` WHERE `user`.`userID` = '"+userID+"'";
		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 
	 }
	 
	 public static String getEligibleItemId() {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "SELECT * FROM `item` ORDER BY `item`.`itemID` DESC";
		     Statement statement = connection.createStatement();
		     ResultSet resultSet = statement.executeQuery(query);
		      if (resultSet.next()) {
		    	  String userID = resultSet.getString("itemID");
		    	  int max = Integer.parseInt(userID.substring(2)) + 1;
		    	  return String.format("IT%03d", max);
		      }
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 return "No Internet";
	 }
	 
	 public static ObservableList<Item> fetchItem() {
		 ObservableList<Item> itemList = FXCollections.observableArrayList();
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "SELECT * FROM `item`";
		     Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery(query);
		     while (rs.next()) {
		    	 itemList.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
		     }
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 return itemList;
	 }
	 
	 public static void registerItem(String itemID, String ItemName, String ItemDesc, int price, int qty) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "INSERT INTO `item` (itemID, itemName, itemDescription, price, quantity) VALUES (?,?,?,?,?)";
		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.setString(1, itemID);
		     statement.setString(2, ItemName);
		     statement.setString(3, ItemDesc);
		     statement.setInt(4, price);
		     statement.setInt(5, qty);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
	 }
	 public static void deleteItem(String userID) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "DELETE FROM `item` WHERE `item`.`itemID` = '"+userID+"'";
		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 
	 }
	 public static void updateItem(String itemID, String itemName, String desc, int price, int qty) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "UPDATE `item` SET itemName ='"+itemName+"', itemDescription = '"+desc+"', price = '"+price+"', quantity = '"+qty+"' WHERE itemID = '" + itemID + "'";
		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
	 }
	 
	 public static ObservableList<Transaction> fetchTransaction() {
		 ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "SELECT * FROM `transaction`";
		     Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery(query);
		     while (rs.next()) {
		    	 Statement statement1 = connection.createStatement();
		    	 Statement statement2 = connection.createStatement();
		    	 Statement statement3 = connection.createStatement();
		    	 String transactionQuery = "SELECT * FROM `transactiondetail` WHERE transactionID = '"+rs.getString(1)+"'";
		    	 String userQuery = "SELECT * FROM `user` WHERE userID = '"+rs.getString(2)+"'";
		    	 ResultSet transaction = statement1.executeQuery(transactionQuery);
		    	 ResultSet user = statement2.executeQuery(userQuery);
		    	 
		    	 if (transaction.next() && user.next()) {
		    		 String itemQuery = "SELECT * FROM `item` WHERE itemID = '"+transaction.getString(2)+"'";
			    	 ResultSet item = statement3.executeQuery(itemQuery);
			    	 if (item.next()) {
			    		 int totalPrice = item.getInt(4) * item.getInt(5);
			    		 transactionList.add(new Transaction(
			    				 transaction.getString(1),
			    				 user.getString(2), 
			    				 item.getString(2), 
			    				 item.getInt(4),
			    				 item.getInt(5),
			    				 totalPrice,
			    				 rs.getString(3),
			    				 item.getString(3)));
			    	 }
			    	 
		    	 }
		    	 
		     }
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 return transactionList;
	 }
	 
	 public static void addToCart(String itemID, int qty) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "INSERT INTO `cart` (itemID, userID, quantity) VALUES (?,?,?)";
		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.setString(1, itemID);
		     statement.setString(2, Connectir.currentUser);
		     statement.setInt(3, qty);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
	 }
	 
	 public static ObservableList<Item> fetchCart() {
		 ObservableList<Item> userList = FXCollections.observableArrayList();
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "SELECT * FROM `cart` WHERE userID = '"+Connectir.currentUser+"'";
		     Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery(query);
		     while (rs.next()) {
		    	 Statement statement1 = connection.createStatement();
		    	 String itemQuery = "SELECT * FROM `item` WHERE itemID='"+rs.getString(2)+"'";
		    	 ResultSet itemResultSet = statement1.executeQuery(itemQuery);
		    	 if (itemResultSet.next()) {
		    		 userList.add(new Item(itemResultSet.getString(1), itemResultSet.getString(2), itemResultSet.getString(3), itemResultSet.getInt(4), rs.getInt(3)));
		    	 }
		    	
		     }
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 return userList;
	 }
	 
	 public static void removeFromCart(String itemID) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "DELETE FROM `cart` WHERE `cart`.`itemID` = '"+itemID+"' AND userID = '"+Connectir.currentUser+"'";
		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 
	 }
	 
	 public static int getItemQuantity(String itemID) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "SELECT * FROM `item` WHERE itemID = '"+itemID+"'";
		     Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery(query);
		     if (rs.next()) {
		    	 return rs.getInt(5);
		     }
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 return 0;
	 }
	 
	 public static void reduceQty(int reducedQty, String itemID) {
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "UPDATE `item` SET quantity = quantity - '"+reducedQty+"' WHERE itemID = '"+itemID+"'";		     PreparedStatement statement = connection.prepareStatement(query);
		     statement.executeUpdate();
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
	 }
	 public static ObservableList<Transaction> fetchTransactionHistory() {
		 ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
		 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fteam", "root", "")) {
		     String query = "SELECT * FROM `transaction` WHERE userID = '"+Connectir.currentUser+"'";
		     Statement statement = connection.createStatement();
		     ResultSet rs = statement.executeQuery(query);
		     while (rs.next()) {
		    	 Statement statement1 = connection.createStatement();
		    	 Statement statement2 = connection.createStatement();
		    	 Statement statement3 = connection.createStatement();
		    	 String transactionQuery = "SELECT * FROM `transactiondetail` WHERE transactionID = '"+rs.getString(1)+"'";
		    	 String userQuery = "SELECT * FROM `user` WHERE userID = '"+rs.getString(2)+"'";
		    	 ResultSet transaction = statement1.executeQuery(transactionQuery);
		    	 ResultSet user = statement2.executeQuery(userQuery);
		    	 
		    	 if (transaction.next() && user.next()) {
		    		 String itemQuery = "SELECT * FROM `item` WHERE itemID = '"+transaction.getString(2)+"'";
			    	 ResultSet item = statement3.executeQuery(itemQuery);
			    	 if (item.next()) {
			    		 int totalPrice = item.getInt(4) * item.getInt(5);
			    		 transactionList.add(new Transaction(
			    				 transaction.getString(1),
			    				 user.getString(2), 
			    				 item.getString(2), 
			    				 item.getInt(4),
			    				 item.getInt(5),
			    				 totalPrice,
			    				 rs.getString(3),
			    				 item.getString(3)));
			    	 }
			    	 
		    	 }
		    	 
		     }
		    } catch (SQLException e) {
		    	System.out.println(e.getMessage());
		    }
		 return transactionList;
	 }
}
