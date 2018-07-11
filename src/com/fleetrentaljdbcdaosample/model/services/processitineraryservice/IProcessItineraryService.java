package com.fleetrentaljdbcdaosample.model.services.processitineraryservice;

import com.fleetrentaljdbcdaosample.model.domain.RentalComposite;
import com.fleetrentaljdbcdaosample.model.services.IService;

/**
 * @author mike.prasad
 *
 */
public interface IProcessItineraryService extends IService {

  public final String NAME = "IProcessItineraryService";

  public boolean processItinerary(RentalComposite rentalComposite);
}
