package org.craftedsw.tripservicekata.service;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.model.Trip;
import org.craftedsw.tripservicekata.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

	@Mock
	private TripService tripService;

	private User loggedInUser = new User();
	private User friendUser = new User();

	public void setupTestData() {
		friendUser.addFriend(loggedInUser);
		friendUser.addTrip(new Trip());
	}

	@Before
	public void setUp() throws UserNotLoggedInException {
		setupTestData();
		when(tripService.getTripsByUser((User) anyObject(), (User) anyObject())).thenCallRealMethod();
		when(tripService.findTripsByUser((User) anyObject())).thenReturn(friendUser.trips());
	}

	/**
	 * @see TripService#getTripsByUser(User)
	 * @verifies _throw_exception_when_user_not_logged_in
	 */
	@Test(expected = UserNotLoggedInException.class)
	public void getTripsByUser_should_throw_exception_when_user_not_logged_in() throws Exception {
//		 when(tripService.getLoggedInUser()).thenReturn(null);
		tripService.getTripsByUser(new User(), null);
	}

	/**
	 * @see TripService#getTripsByUser(User)
	 * @verifies _return_empty_trip_when_user_is_not_a_friend
	 */
	@Test
	public void getTripsByUser_should_return_empty_trip_when_user_is_not_a_friend() throws Exception {
//		 when(tripService.getLoggedInUser()).thenReturn(new User());
		List<Trip> trips = tripService.getTripsByUser(new User(), new User());
		Assert.assertEquals(0, trips.size());
	}

	/**
	 * @see TripService#getTripsByUser(User)
	 * @verifies _return_trip_list_when_user_is_a_friend
	 */
	@Test
	public void getTripsByUser_should_return_trip_list_when_user_is_a_friend() throws Exception {

//		when(tripService.getLoggedInUser()).thenReturn(loggedInUser);
		List<Trip> trips = tripService.getTripsByUser(friendUser, loggedInUser);
		Assert.assertEquals(1, trips.size());
	}

}