package com.fleetrentaljdbcdaosample.model.services.reserverentalservice;

import com.fleetrentaljdbcdaosample.model.dao.IFleetRentalDao;
import com.fleetrentaljdbcdaosample.model.domain.RentalComposite;
import com.fleetrentaljdbcdaosample.model.services.exception.DaoLoadException;
import com.fleetrentaljdbcdaosample.model.services.factory.DAOFactory;
import org.apache.log4j.Logger;

/**
 * Reserves the car by delegating to a DAO
 *
 * @author Mike.Prasad
 *
 *
 */
public class ReserveRentalServiceImpl implements IReserveRentalService {

  /*
	 * Category set in config/log4j.properties as
	 * log4j.category.com.classexercise=DEBUG, A1
   */
  static Logger log = Logger.getLogger("com.fleetrentaljdbcdaosample");

  /**
   * Delegates request to the DAO.
   *
   * @param rentalComposite contains reservation information
   */
  @Override
  public boolean reserveRentalCar(RentalComposite rentalComposite) {
    boolean status = false;
    try {
      log.info("Inside ReserveRentalServiceImpl:ReserveRentalCar");
      // Fetch the DAO Implementation
      IFleetRentalDao fleetRentalDao = DAOFactory.getDao();
      // Reserve the car
      status = fleetRentalDao.reserveRentalCar(rentalComposite);
    } //end reserveRentalCar
    catch (DaoLoadException ex) {
      // We are not propagating exception, with the intent that the 
      // RentalComposite will hold the state reflecting(null AvailableRentals
      // for example) an anomaly
      log.error("DAO Load Exception", ex);
    }
    return status;
  } //end reserveRentalCar

} //end ReserveRentalServiceImpl
