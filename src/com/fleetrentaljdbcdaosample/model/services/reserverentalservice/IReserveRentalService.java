package com.fleetrentaljdbcdaosample.model.services.reserverentalservice;

import com.fleetrentaljdbcdaosample.model.domain.RentalComposite;
import com.fleetrentaljdbcdaosample.model.services.IService;

/**
 * @author mike.prasad
 *
 */
public interface IReserveRentalService extends IService {

    public final String NAME = "IReserveRentalService";

    /**
     * Reserves the car by delegating to a DAO
     *
     */
    public boolean reserveRentalCar(RentalComposite rentalComposite);

}
