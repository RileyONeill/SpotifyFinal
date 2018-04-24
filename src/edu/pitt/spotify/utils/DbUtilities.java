package edu.pitt.spotify.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * Provides methods for: 
 * 1. Retrieving data sets from MySQL databases. 
 * 2. For executing UPDATE, INSERT, DELETE queries 
 * 3. For building tables to populate data grids (JTable)
 * @author Dmitriy Babichenko
 * @version 1.1
 */
public class DbUtilities {

    private Connection conn = null; // connection object
    private String hostName = "sis-teach-01.sis.pitt.edu"; // server address + port number
    private String dbName = "spotify_knockoff"; // default database name
    private String dbUserName = "spotifyUser"; // login name for the database server
    private String dbPassword = "spotifyUser123"; // password for the database server

    /**
     * Default constructor creates a connection to database at the time of instantiation.
     */
    public DbUtilities() {
        createDbConnection();
    }
    
    /**
     * Alternate constructor - use it to connect to any MySQL database
     * @param hostName - server address/name of MySQL database
     * @param dbName - name of the database to connect to
     * @param dbUserName - user name for MySQL database
     * @param dbPassword - password that matches dbUserName for MySQL database
     */
    public DbUtilities(String hostName, String dbName, String dbUserName, String dbPassword) {
    	// Set class-level (instance) variables
        this.hostName = hostName;
        this.dbName = dbName;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        // Create new database connection
        createDbConnection();
    }
    
    
    /**
     * Creates database connection using credentials stored in class variables.  
     * Connection to database is the most resource-consuming part of the database transaction. 
     * That's why we create a connection once when the object is instantiated and keep it alive through the life of this object.
     * Note that this is a private method and cannot be accessed from outside of this class.
     */
    private void createDbConnection(){
        try {
        	// Build connection string
            String mySqlConn = "jdbc:mysql://" + hostName + "/" + dbName + "?user=" + dbUserName + "&password=" + dbPassword;
            // Instantiate the MySQL database connector driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Connect to the database
            conn = DriverManager.getConnection(mySqlConn);
        } catch (Exception e) {
            System.err.print(e.toString());
            System.err.println("Unable to connect to database");
        }
    }
    
    public void closeDbConnection(){
    	try {
            if(conn != null){ // Check if connection object already exists
                conn.close();
                conn = null;
            }
            
        } catch (Exception e) {
        	e.printStackTrace(); // debug
            
        }
    }
    


    /**
     * Get SQL result set (data set) based on an SQL query
     * @param sql - SQL SELECT query
     * @return - ResultSet - java.sql.ResultSet object, contains results from SQL query argument
     * @throws SQLException
     */
    public ResultSet getResultSet(String sql) throws SQLException {  
        try {
            if(conn == null){ // Check if connection object already exists
                createDbConnection(); // If does not exist, create new connection
            }
            Statement statement = conn.createStatement();
            return statement.executeQuery(sql); // Return ResultSet
        } catch (Exception e) {
        	e.printStackTrace(); // debug
            
        }
        return null;
    }
    
    /**
     * Executes INSERT, UPDATE, DELETE queries
     * @param sql - SQL statement - a well-formed INSERT, UPDATE, or DELETE query
     * @return true if execution succeeded, false if failed 
     */
    public boolean executeQuery(String sql){
        try {
            if(conn == null){
                createDbConnection();
            }
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql); // execute query
            return true;
        } catch (Exception e) {
        	e.printStackTrace(); // debug
            
        }
        return false;
    }
    
    
    /**
     * Creates a model for JTable using default database table column names as table headers
     * @param sql - SQL SELECT query
     * @return a model for JTable
     * @throws SQLException
     */
    public DefaultTableModel getDataTable(String sql) throws SQLException{
    	ResultSet rs = getResultSet(sql);
    	
    	/* Metadata object contains additional information about a ResulSet, 
    	 * such as database column names, data types, etc...
    	 */
		ResultSetMetaData metaData = rs.getMetaData();
		
		// Get column names from the metadata object and store them in a Vector variable
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for(int column = 1; column <= columnCount; column++){
			columnNames.add(metaData.getColumnName(column));
		}
		
		// Create a nested Vector containing an entire table from the ResultSet
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while(rs.next()){
			Vector<Object> vector = new Vector<Object>();
			for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columnNames);
	}
    
    /**
     * Creates a model for JTable using custom column names
     * @param sqlQuery - SQL SELECT query
     * @param customColumnNames - an array containing custom column names for table headers
     * @return a model for JTable
     * @throws SQLException
     */
    public DefaultTableModel getDataTable(String sqlQuery, String[] customColumnNames) throws SQLException{
    	ResultSet rs = getResultSet(sqlQuery);
    	/* Metadata object contains additional information about a ResulSet, 
    	 * such as database column names, data types, etc...
    	 */
    	ResultSetMetaData metaData = rs.getMetaData();
    	
    	// Get column names from the metadata object and store them in a Vector variable
		Vector<String> columnNames = new Vector<String>();
		for(int column = 0; column < customColumnNames.length; column++){
			columnNames.add(customColumnNames[column]);
		}
		
		// Create a nested Vector containing an entire table from the ResultSet
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while(rs.next()){
			Vector<Object> vector = new Vector<Object>();
			for(int columnIndex = 1; columnIndex <= metaData.getColumnCount(); columnIndex++){
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columnNames);
	}

	public Connection getConn() {
		return conn;
	}
    
}
