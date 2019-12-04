package ederson_cardoso_exercise1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ResultSetTest {
	// Connection from HOME
	private static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
	// Connection from COLLEGE (Secured WIFI)
	// private static final String DATABASE_URL = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";

	private static final String USERNAME = "COMP214_F19_zor_61";
	private static final String PASSWORD = "password";

	public static void main(String args[]) {

		System.out.println("***Using ResultSet***");
		System.out.println("Part (a):");
		executeQuery("select FirstName, LastName from authors where AuthorID > 3 order by FirstName");

		System.out.println("\nPart (b):");
		executeQuery("select Isbn, Title from titles where EditionNumber between 6 and 10 order by Isbn");

	} // end main

	/**
	 * This method is used to execute a query statement
	 * 
	 * @param query
	 */
	private static void executeQuery(String query) {
		// try with resources to handle connection
		try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			// get Metadata
			ResultSetMetaData metaData = resultSet.getMetaData();
			int numberOfColumns = metaData.getColumnCount();

			// display column header
			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.printf("%-8s\t", metaData.getColumnName(i).toUpperCase());
			}
			System.out.println();

			// display result
			while (resultSet.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.printf("%-8s\t", resultSet.getObject(i));
				}
				System.out.println();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	} // end executeQuery method
} // end class
