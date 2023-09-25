/**
 * 
 */
package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ch.qos.logback.core.CoreConstants;
import com.flipkart.bean.Customer;
import com.flipkart.bean.Gym;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.User;
import com.flipkart.constants.SQLConstants;
import com.flipkart.utils.DatabaseConnector;
import com.flipkart.utils.OutputFormatter;

/**
 * @author msaikalyan.yadav
 * 
 */
public class GymOwnerDao implements GymOwnerDaoInterface {
	

	@Override
	public GymOwner getGymOwnerFromUserId(int userId) {
		// TODO Auto-generated method stub
		// Connect to the database and fetch the list of all gyms
		// get customer object
		// Handle any exceptions that occur

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DatabaseConnector.getConnection();
			stmt = conn.prepareStatement(SQLConstants.SQL_FETCH_GYMOWNER_USING_USERID_QUERY);
			stmt.setInt(1, userId);

			ResultSet output = stmt.executeQuery();
			if (output.next()) {
				GymOwner gymOwner = new GymOwner(output.getInt(1), output.getString(2),output.getString(3), output.getString(4), output.getString(5),output.getString(6),userId,output.getString(8));
				return gymOwner;
			}

		} catch (SQLException sqlExcep) {
//		       System.out.println(sqlExcep);
		} catch (Exception excep) {
			excep.printStackTrace();
		}
		return null;
	}
	@Override
	public int registerGymOwner(GymOwner owner, User user) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// Getting customer Id
			conn = DatabaseConnector.getConnection();

//		    user.setUserName(Integer.toString(count));

			// Registering in Customer schema

			stmt = conn.prepareStatement(SQLConstants.SQL_REGISTER_GYMOWNER_USER_QUERY);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getRole());
			stmt.executeUpdate();

			stmt = conn.prepareStatement(SQLConstants.SQL_REGISTER_GYMOWNER_QUERY);
			stmt.setString(1, owner.getName());
			stmt.setString(2, owner.getStatus());
			stmt.setString(3, owner.getAadharNumber());
			stmt.setString(4, owner.getContactNumber());
			stmt.setString(5, owner.getAddress());
			UserDao userDao = new UserDao();
			User user1 = userDao.authenticateUser(user.getName(), user.getPassword());
			stmt.setInt(6, user1.getUserID());
			stmt.setString(7, owner.getPassword());

			int status=stmt.executeUpdate();
			return status;
		} catch (SQLException sqlExcep) {
			System.out.println(sqlExcep);
		} catch (Exception excep) {
			excep.printStackTrace();
		}
		return 0;
	}
	
	public GymOwner viewProfile(int ownerId) {
//		// Connect to the database and fetch the list of all gyms
		// Print Customer profile
		// Handle any exceptions that occur

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DatabaseConnector.getConnection();
			stmt = conn.prepareStatement(SQLConstants.SQL_FETCH_GYMOWNER_QUERY);
			stmt.setInt(1, ownerId);
			ResultSet output = stmt.executeQuery();
			if(output.next()) {
				System.out.println();
				GymOwner gymOwner = new GymOwner(output.getInt(1), output.getString(2), output.getString(3), output.getString(4), output.getString(5), output.getString(6), output.getInt(7), output.getString(8));
				return gymOwner;
			}
//			OutputFormatter.outputData(headers, data);
		} catch (SQLException sqlExcep) {
			System.out.println(sqlExcep);
		} catch (Exception excep) {
			excep.printStackTrace();
		}
		return new GymOwner();
	}
	
	public void updateProfile(GymOwner owner) {
		// Connect to the database and fetch the list of all gyms
		// Print Customer profile
		// Handle any exceptions that occur

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DatabaseConnector.getConnection();
			stmt = conn.prepareStatement(SQLConstants.SQL_UPDATE_GYMOWNER_QUERY);
			stmt.setString(1, owner.getName());
            stmt.setString(2, owner.getAadharNumber());
            stmt.setString(3, owner.getContactNumber());
            stmt.setString(4, owner.getAddress());
            stmt.setInt(5, owner.getUserId());
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("User details updated successfully.");
            } else {
                System.out.println("User not found or no changes made.");
            }
		} catch (SQLException sqlExcep) {
			System.out.println(sqlExcep);
		} catch (Exception excep) {
			excep.printStackTrace();
		}
	}
	@Override
	public List<Gym> viewGyms(GymOwner owner) {
		// TODO Auto-generated method stub
		
		List<Gym> gyms = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DatabaseConnector.getConnection();
			stmt = conn.prepareStatement(SQLConstants.SQL_FETCH_OWNER_GYM_DET_QUERY);
			stmt.setInt(1, owner.getId());
			ResultSet resultSet = stmt.executeQuery();
			int cntGym = 0;
			 while (resultSet.next()) {
				 
				    cntGym++;
	                int gymID = resultSet.getInt("GymID");
	                String gymName = resultSet.getString("Name");
	                String registerStatus = resultSet.getString("Approved");
	                int gymOwnerId = resultSet.getInt("Owner");
					String address = resultSet.getString("address");
	                
	                // Create a Gym object and add it to the list
	                Gym gym = new Gym(gymID, gymName,address, registerStatus, gymOwnerId);
	                gyms.add(gym);
	        }
			 
		} catch (SQLException sqlExcep) {
			System.out.println(sqlExcep);
		} catch (Exception excep) {
			excep.printStackTrace();
		}
		return gyms;
		
	}
	@Override
	public int registerGym(Gym gym) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
//
//		try {
//			conn = DatabaseConnector.getConnection();
//			stmt = conn.prepareStatement(SQLConstants.SQL_INSERTING_GYM);
//			stmt.setString(1, );
//			stmt.setString(2, gymAddress);
//			stmt.setInt(3, gymOwnerId);
//			stmt.executeUpdate();
//			stmt = conn.prepareStatement(SQLConstants.SQL_FETCHING_INSERTING_GYM);
//			stmt.setInt(1, gymOwnerId);
//			stmt.setString(2, gymName);
//			ResultSet resultSet = stmt.executeQuery();
//			resultSet.next();
//			gymID = resultSet.getInt(1);
//			return gymID;
//
//
//		} catch (SQLException sqlExcep) {
//			System.out.println(sqlExcep);
//		} catch (Exception excep) {
//			excep.printStackTrace();
//		}
//
//		return 0;
		
	}
	
	
}
		
