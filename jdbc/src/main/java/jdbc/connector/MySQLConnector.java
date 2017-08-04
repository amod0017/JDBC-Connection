package jdbc.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

class MySQLConnector {
	public static void main(final String args[]) {
		try {
			final Connection con = getConnection();
			// insert
			final Calendar calendar = Calendar.getInstance();
			final java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
			final String insertQuery = " Insert into test.bugs(id,open_date,severity) values (?,?,?)";
			final PreparedStatement preparedStmt = con.prepareStatement(insertQuery);
			preparedStmt.setInt(1, 101);
			preparedStmt.setDate(2, startDate);
			preparedStmt.setString(3, "error");
			preparedStmt.execute();
			// select
			final String query = "select * from test.bugs where id = 101";
			final ResultSet rs = getResult(con, query);
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "  " + rs.getDate(2) + "  " + rs.getDate(3) + " " + rs.getString(4));
			}

			con.close();
		} catch (final Exception e) {
			System.out.println(e);
		}
	}

	private static ResultSet getResult(final Connection con, final String query) throws SQLException {
		final Statement stmt = con.createStatement();
		final ResultSet rs = stmt.executeQuery(query);
		return rs;
	}

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		final Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
		return con;
	}
}