package com.fleetrentaljdbcdaosample.model.dao.jdbc;

import com.fleetrentaljdbcdaosample.model.dao.IFleetRentalDao;
import com.fleetrentaljdbcdaosample.model.domain.AvailableRentals;
import com.fleetrentaljdbcdaosample.model.domain.Car;
import com.fleetrentaljdbcdaosample.model.domain.RentalComposite;
import com.fleetrentaljdbcdaosample.model.services.manager.PropertyManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 * This is where the JDBC Code goes into.
 *
 * To this DAO, need to make sure the data is setup as discussed in the slides.
 *
 * Exceptions are not handled here and are left as a student exercise.
 *
 * Any exception(stmt) that happen here are to be propagated appropriately to
 * the calling tiers.
 *
 * @author Mike.Prasad
 *
 *
 */
public class FleetRentalJDBCDaoImpl implements IFleetRentalDao {

  static Logger log = Logger.getLogger("com.fleetrentaljdbcdaosample");

  /**
   * Using the connection parameters from the property files, loads and
   * registers the JDBC driver and gets the database connection and returns in.
   *
   * @return Connection Connection returned on success otherwise returns null
   * @throws SQLException
   */
  private Connection fetchConnection() {
    log.info("Fetching Database Connection");

    Connection conn = null;
    /*
         * Values come out of the ...JDBCDAOSample\config\application.properties
         * file
     */
    String url = PropertyManager.getPropertyValue("jdbc.url");
    String userid = PropertyManager.getPropertyValue("jdbc.user");
    String password = PropertyManager.getPropertyValue("jdbc.password");

    // load and register JDBC driver then connect to database
    try {
      DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
      conn = DriverManager.getConnection(url, userid, password);
    } catch (SQLException e) {
      log.error("Could not load and register JDBC driver or connect to database.");
      log.error(e.getClass() + ": " + e.getMessage(), e);
    }
    return conn;
  } //end fetchConnection

  /**
   * Retrieve all the vehicles that are available based on criteria in the
   * Itinerary object and populate AvailableRentals
   *
   * @return boolean true if rentals are available, else false
   * @return RentalComposite has availableRentals set if rentals are available
   */
  @Override
  public boolean getAvailableRentals(RentalComposite rentalComposite) {
    boolean status = false;

    log.info("-------------------------------");
    log.info("Using JDBC Implementation");
    log.info("-------------------------------");

    log.info("Inside Get Available Rentals");

    AvailableRentals availableRentals = fetchAvailableCars();

    if (availableRentals != null) {
      // indicate that rentals are available for customer'stmt request
      availableRentals.setAvailable(true);
      // ideally, this should be stored in the db as well    
      availableRentals.setStateTax(6.89f);
      // set available rentals into the rental composite
      rentalComposite.setAvailableRentals(availableRentals);

      // set the return status
      status = true;
    }
    return status;
  } //end getAvailableRentals

  /**
   * Student exercise to add the relevant JDBC code.
   */
  @Override
  public boolean reserveRentalCar(RentalComposite _rentalComposite) {
    boolean status = false;
    log.error("Reservation Implementation not complete");
    return status;
  }

  /**
   * Fetches all available cars for rental
   *
   * @return AvailableRentals containing all available rental cars null, if no
   * connection available to the db
   */
  private AvailableRentals fetchAvailableCars() {
    AvailableRentals availableRentals = null;
    ResultSet rset = null;
    Statement stmt = null;
    Connection conn = null;
    try {
      // Create a connection
      conn = fetchConnection();

      if (conn != null) {
        // Create a statement.
        stmt = conn.createStatement();

        //NOTE: Not using a PreparedStatement since we are not binding any variables
        // Select all cars that are not rented.
        rset = stmt.executeQuery("select c.rate, c.manufacturer, c.model, "
                + "c.miles_included, c.rented from location l, cars c where "
                + "(l.idlocation = c.LOCATION_FK) AND (c.rented='N')");

        availableRentals = buildAvailableRentals(rset);
      }
    } catch (SQLException e) {
      log.error(e.getClass() + ": " + e.getMessage(), e);
    } finally //must close resources in finally block
    {
      try {
        // check for null first before closing resources
        if (stmt != null) {
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {// No need to propagate as availableRentals state conveys										
        log.error(e.getClass() + ": " + e.getMessage(), e);
      }
    }
    return availableRentals;
  } // fetchAvailableCars

  /**
   * Iterate over the result set and build out the AvailableRentals object.
   *
   * @param resultSet
   * @return AvailableRentals populated if resultSet contains data Returns a
   * null AvailableRentals otherwise
   */
  private AvailableRentals buildAvailableRentals(ResultSet resultSet) {
    log.info("Inside buildAvailableRentals");
    AvailableRentals availableRentals = null;

    try {
      // Note: isBeforeFirst: true if the cursor is before the first row; false if 
      // the cursor is at any other position or the result set contains no rows 
      // Create availableRentals, only if resultSet has data.
      if (resultSet.isBeforeFirst()) {
        availableRentals = new AvailableRentals();
      }

      while (resultSet.next()) {
        /* Car CTOR mapping:
            Car(float rate,
                String manufacturer,
                String model,
                String milesIncluded,
                String rented)
         */

        Car car = new Car(resultSet.getFloat(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5));
        availableRentals.addRental(car);
      }
    } catch (SQLException e) {// No need to propagate as availableRentals state conveys								
      log.error(e.getClass() + ": " + e.getMessage(), e);
    }
    return availableRentals;
  } // end buildAvailableRentals

} // end class FleetRentalJDBCDaoImpl
