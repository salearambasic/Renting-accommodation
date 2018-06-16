package com.project.Rentingaccommodation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Reservation;
import com.project.Rentingaccommodation.model.ReservationStatus;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.ReservationService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value="api/reservations")
public class ReservationController {

	@Autowired
	private ReservationService service;
	
	@Autowired
	private ApartmentService apartmentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtValidator jwtValidator;
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserReservations(@RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				List<Reservation> userReservations = service.findUserReservations(jwtUser.getEmail());
				return new ResponseEntity<>(userReservations, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Reservation>> getAllReservations() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> makeReservation(@RequestBody Reservation reservation, @RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				if (reservation.getStartDate() == null || reservation.getStartDate() == "" ||
					reservation.getEndDate() == null || reservation.getEndDate() == "") {
					return new ResponseEntity<>("All fields are required (start date, end date).", HttpStatus.FORBIDDEN);
				}
				
				User user = userService.findByEmail(jwtUser.getEmail());
				if (user == null) {
					return new ResponseEntity<>("User doesnt exist.", HttpStatus.FORBIDDEN);
				}
				
				Pattern pattern = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
				Matcher startDateMatcher = pattern.matcher(reservation.getStartDate());
				Matcher endDateMatcher = pattern.matcher(reservation.getEndDate());
				
				// Check date formats.
				if (!startDateMatcher.find()) {
					return new ResponseEntity<>("Start date must be in format yyyy-MM-dd.", HttpStatus.FORBIDDEN);
				}
				if (!endDateMatcher.find()) {
					return new ResponseEntity<>("End date must be in format yyyy-MM-dd.", HttpStatus.FORBIDDEN);
				}
				
				// Check if dates are past dates.
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				Date startDate = dateFormatter.parse(reservation.getStartDate());
				Date endDate = dateFormatter.parse(reservation.getEndDate());
				if (startDate.before(new Date()) || endDate.before(new Date())) {
					return new ResponseEntity<>("You must enter future dates.", HttpStatus.FORBIDDEN);
				}
				
				// Check if start date is before end date.
				if (!startDate.before(endDate)) {
					return new ResponseEntity<>("Start date must be before end date.", HttpStatus.FORBIDDEN);
				}
				
				// Make reservation of first apartment.
				Apartment apartment = apartmentService.findOne(1l);
				
				// Check if apartment is available in that period.
				if (!service.isAvailable(apartment, reservation.getStartDate(), reservation.getEndDate())) {
					return new ResponseEntity<>("Apartment is not available at the given period.", HttpStatus.FORBIDDEN);
				}
				
				Reservation newReservation = new Reservation(
					user,
					apartment,
					reservation.getStartDate(),
					reservation.getEndDate(),
					150,
					ReservationStatus.RESERVATION
				);
				service.save(newReservation);
				return new ResponseEntity<>(newReservation, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
		Reservation reservation = service.delete(id);
		if (reservation == null) {
			return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}
}