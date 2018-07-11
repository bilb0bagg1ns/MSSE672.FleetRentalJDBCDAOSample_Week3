package com.fleetrentaljdbcdaosample.driver;

import com.fleetrentaljdbcdaosample.model.business.manager.DAOManager;
import com.fleetrentaljdbcdaosample.model.domain.Car;
import com.fleetrentaljdbcdaosample.model.domain.Customer;
import com.fleetrentaljdbcdaosample.model.domain.Itinerary;
import com.fleetrentaljdbcdaosample.model.domain.RentalComposite;
import org.apache.log4j.Logger;

/*-
 * Please note that the FleetRental application can be run either by running
 * the ViewDriver class or this class. This class tests the Controller and Model
 * components.  
 * 
 * Its common to have test drivers such as this class to test applications.
 *  
 * A real world implementation, would of course have a View (either Swing or Web based).
 * 
 * Runtime(VM Options) Setup to load application properties:
 *  
  ///////////////////////////////////////////////////////////
  // Need to pass property file that is loaded by PropertyManager
  // 1. From IDE, set the VM Options under Project Properties
  //     -Dprop_location=<path>\config\application.properties
  // 2. Build.xml: reads off from the target to run the app with the sys property set as :
  //   <sysproperty key="prop_location" value="${config.dir}application.properties"/>

 * Runtime(VM Options) Setup to load log4j properties:
 *  
  ///////////////////////////////////////////////////////////
  // Need to pass log4j configuration file that is loaded by Log4JInit
  // 1. From IDE, set the VM Options under Project Properties
  //     -Dlog4j_prop_location=<path>\config\log4j.properties
  // 2. Build.xml: reads off from the target to run the app with the sys property set as :
  //   <sysproperty key="log4j_prop_location" value="${config.dir}log4j.properties"/>

 * 
 * @author Mike.Prasad
 *
 */
public class TestDriver {

  /*
					* Category set in config/log4j.properties as
					* log4j.category.com.classexercise=DEBUG, A1
   */
  static Logger log = Logger.getLogger("com.fleetrentaljdbcdaosample");

  public static void main(String[] args) {
    Log4JInit.initializeLog4J();

    // In the real world implementation, customer would identify
    // an Itinerary.
    RentalComposite rentalComposite = new RentalComposite();

    // lets create a sample itinerary.
    // Itinerary constructor needs following fields : 
    // (fleetRentalPickUp, fleetRentalDropOff,pickUpMonth,pickUpDay,pickUpYear,pickUpTime,dropOffMonth,dropOffDay, dropOffYear,dropOffTime)		
    rentalComposite.setItinerary(new Itinerary("San Francisco Airport", "San Francisco Airport", "06", "18", "2006", "01:10", "06", "28", "2006", "12:00"));

    log.info("----------------");
    log.info(" Checking Car Availablity for following itinerary: \n\n" + rentalComposite.getItinerary());

    // now that we have an itinerary, lets call into the Model 
    // to see if have any cars available for this itinerary
    DAOManager daoManager = DAOManager.getInstance();
    boolean status = daoManager.performAction("ProcessItinerary", rentalComposite);

    if (status) //if true then request processed successfully
    {
      // Lets check if cars are available if so we can reserve them.
      if (rentalComposite != null) {
        if (rentalComposite.getAvailableRentals() != null) {
          if (rentalComposite.getAvailableRentals().isAvailable()) {
            // Cool, we have a car to rent, lets get Customer info and the Car customer
            // wants to rent.
            log.info("Following cars available for above itinerary: \n\n" + rentalComposite.getAvailableRentals());

            // User enters personal info
            // Customer contructor takes in lastname, firstname, email address, day time phone and evening phone
            rentalComposite.setCustomer(new Customer("Simpson", "Homer", "homer@duff.com", "303-786-1111", "303-786-1111"));

            // User select the car he/she wants to rent
            // Car constructor takes in rate, manufacturer, model, miles included
            rentalComposite.setRentedCar(new Car(25.50f, "Ford", "Focus", "Unlimited"));

            log.info("Calling reserve rental car service with this details: \n\n" + rentalComposite);

            // Ideally the type of the service that needs to be executed
            // is mapped in a properties file. Hardcoded here to
            // illustrate the example.
            daoManager.performAction("ReserveRental", rentalComposite);
          } else {
            // Hopefully this doesn't happen in the real world! :)
            log.info("No car available! Suggest hitchhiking!");
          } //end if
        } else {
          // AvailableRentals is NULL - this due to SQL Exception issue
          log.error("We are facing an issue, please try back later!");
        }//end if								
      } //end if							
    } else {
      log.error("We are facing an issue, please try back later!");
    }

  } //end main

} //end class TestDriver


/*-
You should see the following output upon execution:

08:56:45.872 [mainhread] com.fleetrentaljdbcdaosample.driver.TestDriver.main(TestDriver.java:61) -----------------
08:56:45.873 [mainhread] com.fleetrentaljdbcdaosample.driver.TestDriver.main(TestDriver.java:62) - Checking Car Availablity for following itinerary: 

fleetRentalPickUp Id:null
fleetRentalPickUp City:San Francisco Airport
fleetRentalDropOff Id:null
fleetRentalDropOff City:San Francisco Airport
pickUpMonth :06
pickUpDay :18
pickUpYear :2006
pickUpTime :01:10
dropOffMonth :06
dropOffDay :28
dropOffYear :2006
dropOffTime :12:00
qtyRentalDays :10
08:56:45.902 [mainhread] com.fleetrentaljdbcdaosample.model.dao.jdbc.FleetRentalJDBCDaoImpl.getAvailableRentals(FleetRentalJDBCDaoImpl.java:74) --------------------------------
08:56:45.902 [mainhread] com.fleetrentaljdbcdaosample.model.dao.jdbc.FleetRentalJDBCDaoImpl.getAvailableRentals(FleetRentalJDBCDaoImpl.java:75) -Using JDBC Implementation
08:56:45.902 [mainhread] com.fleetrentaljdbcdaosample.model.dao.jdbc.FleetRentalJDBCDaoImpl.getAvailableRentals(FleetRentalJDBCDaoImpl.java:76) --------------------------------
08:56:45.902 [mainhread] com.fleetrentaljdbcdaosample.model.dao.jdbc.FleetRentalJDBCDaoImpl.getAvailableRentals(FleetRentalJDBCDaoImpl.java:78) -Inside Get Available Rentals
08:56:45.902 [mainhread] com.fleetrentaljdbcdaosample.model.dao.jdbc.FleetRentalJDBCDaoImpl.fetchConnection(FleetRentalJDBCDaoImpl.java:41) -Fetching Database Connection
Wed Jul 11 08:56:46 MDT 2018 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
08:56:46.638 [mainhread] com.fleetrentaljdbcdaosample.model.dao.jdbc.FleetRentalJDBCDaoImpl.buildAvailableRentals(FleetRentalJDBCDaoImpl.java:160) -Inside buildAvailableRentals
08:56:46.642 [mainhread] com.fleetrentaljdbcdaosample.driver.TestDriver.main(TestDriver.java:77) -Following cars available for above itinerary: 

Rental is available
State Tax: 6.89
Available Rentals List: 	

Car[		
carId = null		
manufacturer = Hyundai		
milesIncluded = Unlimited		
model = Accent		
rate = 23.5		
rented = N]
	

Car[		
carId = null		
manufacturer = Toyota		
milesIncluded = Unlimited		
model = Camry		
rate = 23.5		
rented = N]

08:56:46.644 [mainhread] com.fleetrentaljdbcdaosample.driver.TestDriver.main(TestDriver.java:87) -Calling reserve rental car service with this details: 


Customer Info :

lastname :Simpson
firstname :Homer
email address :homer@duff.com
day time phone :303-786-1111
evening Phone :303-786-1111

Available Rentals :
Rental is available
State Tax: 6.89
Available Rentals List: 	

Car[		
carId = null		
manufacturer = Hyundai		
milesIncluded = Unlimited		
model = Accent		
rate = 23.5		
rented = N]
	

Car[		
carId = null		
manufacturer = Toyota		
milesIncluded = Unlimited		
model = Camry		
rate = 23.5		
rented = N]


Itinerary :
fleetRentalPickUp Id:null
fleetRentalPickUp City:San Francisco Airport
fleetRentalDropOff Id:null
fleetRentalDropOff City:San Francisco Airport
pickUpMonth :06
pickUpDay :18
pickUpYear :2006
pickUpTime :01:10
dropOffMonth :06
dropOffDay :28
dropOffYear :2006
dropOffTime :12:00
qtyRentalDays :10

Rented Car :
	

Car[		
carId = null		
manufacturer = Ford		
milesIncluded = Unlimited		
model = Focus		
rate = 25.5		
rented = null]

08:56:46.644 [mainhread] com.fleetrentaljdbcdaosample.model.business.manager.DAOManager.reserveRentalCar(DAOManager.java:106) -Reserving Rental Car
08:56:46.652 [mainhread] com.fleetrentaljdbcdaosample.model.services.reserverentalservice.ReserveRentalServiceImpl.reserveRentalCar(ReserveRentalServiceImpl.java:33) -ReserveRentalServiceImpl:ReserveRentalCar
08:56:46.652 [mainhread] com.fleetrentaljdbcdaosample.model.dao.jdbc.FleetRentalJDBCDaoImpl.reserveRentalCar(FleetRentalJDBCDaoImpl.java:102) -Reservation Implementation not complete


*/