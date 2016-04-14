package org.craftedsw.tripservicekata.service;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.dao.TripDAO;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.model.Trip;
import org.craftedsw.tripservicekata.model.User;

public class TripService {

	/**
	 * 
	 * @param user
	 * @return
	 * @throws UserNotLoggedInException
	 * @should _throw_exception_when_user_not_logged_in
	 * @should _return_empty_trip_when_user_is_not_a_friend
	 * @should _return_trip_list_when_user_is_a_friend
	 */
	public List<Trip> getTripsByUser(User user, User loggedInUser) throws UserNotLoggedInException {

		isUserLoggedIn(loggedInUser);

		if (user.isFriend(loggedInUser)) {
			return findTripsByUser(user);
		} else {
			return emptyTrips();
		}

	}

	private ArrayList<Trip> emptyTrips() {
		return new ArrayList<Trip>();
	}

	// DAO class should be injected by the underlying framework
	protected List<Trip> findTripsByUser(User user) {
		return TripDAO.findTripsByUser(user);
	}

	private boolean isUserLoggedIn(User loggedInUser) throws UserNotLoggedInException {
		if (loggedInUser != null) {
			return true;
		}
		throw new UserNotLoggedInException();
	}

}
