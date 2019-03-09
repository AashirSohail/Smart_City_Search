package lab10;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class readfile {

	static ArrayList<cityRecord> records = new ArrayList <cityRecord>();
	
	public static void main(String[] args) throws SQLException {

		readCsvUsingLoad();
		getUser_Choice();

	}
	
	//Check the database
	private static void readCsvUsingLoad(){
	
	    try (Connection connection = DBconnection.getCon()) {
	    	Statement stmt = connection.createStatement();
	        		
	        String checkQuery = "SELECT * FROM record";
	        ResultSet rs = stmt.executeQuery(checkQuery);
	        		
	        //IF EMPTY
	        if (rs.next() == false) { 
	        			
	        	System.out.println("Database Empty.\nAdding Data..."); 
	        	String loadQuery = "LOAD DATA LOCAL INFILE '" + 
             				   "/home/aashir/Downloads/city.csv" + 
             				   "' INTO TABLE record FIELDS TERMINATED BY ','" + 
             				   "LINES TERMINATED BY '\n' (id, country, region, city, postalCode, latitude, longitude, metroCode, areaCode)";
	        	stmt.execute(loadQuery);
	        	System.out.println("Data added sucessfully.");
	        		
	        }
	        else {
	        	System.out.println("Database already populated."); 	
	        }
	    }
	        catch (Exception e){	
	        e.printStackTrace();
	    }
	
	}

	//get user choice	
	private static void getUser_Choice() throws SQLException {
		
		int lon = 0, lat = 0, choice = 0, total = 0;
		String city,country = "";
		
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("[1] by Coordinate\n[2] by City\nEnter Search Choice: "); 	
		
		choice = input.nextInt();
		
		System.out.print("Enter number of Cities: "); 	
		total = input.nextInt();
		
		if( choice == 1) {
			System.out.print("Enter longitude: "); 	
			lon = input.nextInt();
			System.out.print("Enter latitude: "); 	
			lat = input.nextInt();
			
	        System.out.println("Longitude: "+ lon+ " Latitude: "+lat+"\n");
			
			degreeToDistance(lat,lon,total,country);
		}
		if( choice == 2) {
			System.out.print("Enter City: "); 	
			city = input.next();
			
			try (Connection connection = DBconnection.getCon()) {
		    	Statement stmt = connection.createStatement();

		        String checkQuery = "SELECT country,longitude, latitude FROM record where city = '"+city+"'";
		        ResultSet rs = stmt.executeQuery(checkQuery);
		        
		        if(rs.next()){
		        
		        country = rs.getString("country");	
		        lon = rs.getInt("longitude");
		        lat = rs.getInt("latitude");
		        
		        }
		        System.out.println("Longitude: "+ lon+ " Latitude: "+lat+"\n");
				degreeToDistance(lat,lon,total,country);
			}
			
		}
		input.close();
		
	}
	
	//fetch from database-get distance-make object-add to list
	private static void degreeToDistance(int lat, int lon, int total,String country) throws SQLException {
		
		double lat1 = Math.toRadians(lat);
        double lon1 = Math.toRadians(lon);
        
        try (Connection connection = DBconnection.getCon()) {
	    	Statement stmt = connection.createStatement();
	        		
	        String checkQuery = "SELECT * FROM record WHERE country = '"+country+"'";
	        ResultSet rs = stmt.executeQuery(checkQuery);
	        while(rs.next()){
	        double lon2 = Math.toRadians(rs.getInt("longitude"));
	        double lat2 = Math.toRadians(rs.getInt("latitude"));
	        
	     // great circle distance in radians
	        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
	                      + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

	        // convert back to degrees
	        angle = Math.toDegrees(angle);
	        
	        // each degree on a great circle of Earth is 60 nautical miles
	        double distance = 60 * angle;	        
	        //System.out.println(distance1 + " nautical miles");
	        
	        records.add(new cityRecord(rs,distance));
	        }
	        
	        printArray(records,total);
		}
	}
	
	//Prints sorted array up to the limit
	private static void printArray(ArrayList<cityRecord> records, int limit) {
		
		Collections.sort(records);
		
		System.out.println("\n"+limit+" nearby cities are: ");
		
		int count = 0;
		for(cityRecord str: records){
			if(count>=0 && str.distance != 0) {
			System.out.println(str);
			count++;
			}
			
			if(count == limit) {break;}
	   }
	records.clear();	
	}
	
	

}
