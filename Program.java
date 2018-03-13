import java.sql.*;
import java.util.Scanner;


public class Program 
{
	final String CONNECTION_STRING = 
				"jdbc:sqlite:data/phones.db";
	Scanner scanner;
	Connection co;
	
	public static void main(String[] args) {
		Program program = new Program();
		program.start();
		program.showMenu();
		program.addPhone();
		program.showPhones();
		
	}
		
	void start() {
			scanner = new Scanner(System.in);
			while (true)
			{
				showMenu();
				int item = scanner.nextInt(); scanner.nextLine();
				switch (item)
				{
				case 1 : addPhone(); break;
				case 2 : showPhones(); break;
				case 0 : System.out.println("Bye-bye!"); return;
				}
			}
	}
		
	void showMenu()
		{
			System.out.println("=== PHONE BOOK ===");
			
			System.out.println(" 1. Add new Record");
			System.out.println(" 2. Show all Records");
			System.out.println(" 0. Exit");
			System.out.println(" > ");
	}
		
	void addPhone()
	{
			System.out.println("ADD NEW RECORD");
			System.out.println("Enter name: ");
			String name = scanner.nextLine();
			System.out.println("Enter phone: ");
			String phone = scanner.nextLine();
			
			connect();
			String query = "INSERT INTO phones (name, phone) VALUES (?, ?)";
			try
			{
				PreparedStatement ps = co.prepareStatement(query);
				ps.setString(1, name);
				ps.setString(2, phone);
				ps.executeUpdate();
			}
			catch (SQLException e)
			{
				System.out.println("QUERY ERROR: " + e.getMessage());
				System.out.println("QUERY: " + query);
				co = null;
			}
			disconnect();			
	}

	void showPhones()
	{
			System.out.println("LIST OF ALL RECORDS");
			connect();
			String query = "SELECT id, name, phone FROM phones ORDER BY name";
			try
			{
				Statement st = co.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next())
				{
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					System.out.println(id + "\t" + name + "\t" + phone);	
				}
				rs.close();
			}
			catch (SQLException e)
			{
				System.out.println("QUERY ERROR: " + e.getMessage());
				System.out.println("QUERY: " + query);
				co = null;
			}
			disconnect();
	}
		
	void connect() {
			System.out.println("Connecting...");
			try {
				co = DriverManager.getConnection(CONNECTION_STRING);
			}
			catch(SQLException e)
			{
				System.out.println("CONNECTION ERROR: " + e.getMessage());
				co = null;
			}
	}
		
	void disconnect()
	{
		System.out.println("Disconnecting...");
		try {
			co.close();
		}
		catch(SQLException e)
		{
			System.out.println("Disconnection error: " + e.getMessage());
		}
	}
}