package ederson_cardoso_exercise2;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class RowSetTest {
	// Connection from HOME
	private static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
	// Connection from COLLEGE (Secured WIFI)
	// private static final String DATABASE_URL = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
	private static final String USERNAME = "COMP214_F19_zor_61";
	private static final String PASSWORD = "password";

	public static void main(String[] args) {
		System.out.println("***Using RowSet***");
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
		try (JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet()) {

			// specify JdbcRowSet properties
			rowSet.setUrl(DATABASE_URL);
			rowSet.setUsername(USERNAME);
			rowSet.setPassword(PASSWORD);
			rowSet.setCommand(query); // set query
			rowSet.execute(); // execute query

			// get Metadata
			ResultSetMetaData metaData = rowSet.getMetaData();
			int numberOfColumns = metaData.getColumnCount();

			// display column header
			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.printf("%-8s\t", metaData.getColumnName(i).toUpperCase());
			}
			System.out.println();

			// display result
			while (rowSet.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.printf("%-8s\t", rowSet.getObject(i));
				}
				System.out.println();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.exit(1);
		}
	} // end executeQuery method
} // end class
