package com.shopzone.app.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopzone.app.controller.UserController;
import com.shopzone.app.entity.Address;
import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.Seller;
import com.shopzone.app.repo.AddressRepository;
import com.shopzone.app.repo.GeneralParameterRepo;
import com.shopzone.app.repo.MerchantRepository;
import com.shopzone.app.repo.SellerRepository;

@Service
public class CheckoutService {

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private GeneralParameterService generateParamService;

	@Autowired
	private MerchantRepository merchantRepository;

	private static final Logger log = LoggerFactory.getLogger(CheckoutService.class);

	public Map<String, LocalDate> getDeliveryExpDate(Long addressId, String merchantSlug) {
		log.info("In get expected delivery date service for address id " + addressId);
		try {
			// fetch user addrees long lat by id
			Optional<Address> address = addressRepo.findById(addressId);

			log.info("User address fetched " + address.toString());
			double lat1 = address.get().getLatitude();
			double lon1 = address.get().getLongitude();

			// fetch seller addrees long lat
//			Optional<Seller> seller = sellerRepository.findById(1L);
//			double lat2 = seller.get().getLatitude();
//			double lon2 = seller.get().getLongitude();
			
			//fetch meerchant address long lat by merchant id-for only single merchant products
			//not applicable for multiple branches of same merchant
			Optional<Merchant> seller = merchantRepository.findByStoreSlug(merchantSlug);
			double lat2 = seller.get().getLatitude();
			double lon2 = seller.get().getLongitude();
			

			// get distn
			double dist = getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2);
			log.info("Distance from seller to user address is " + dist);

			Map<String, LocalDate> resp = new HashMap<String, LocalDate>();
			LocalDate today = LocalDate.now();
			// log.info("Today: " + today);
			int daysToAdd = 7;
			// expected dates to day from general parameter expected_delivery

			String result = generateParamService.fetchParameterValue("expected_delivery");

			String[] rules = result.split(",");

			for (String rule : rules) {
				rule = rule.trim();
				String[] parts = rule.split("\\|");

				if (parts.length == 2) {
					String range = parts[0].trim();
					String daysStr = parts[1].trim();

					String[] rangeArr = range.split("-");
					if (rangeArr.length == 2) {
						String rangeFrom = rangeArr[0].trim();
						String rangeTo = rangeArr[1].trim();

						double from = Double.parseDouble(rangeFrom);
						double to = Double.parseDouble(rangeTo);
						int day = Integer.parseInt(daysStr);
						if (dist >= from && dist < to) {
							daysToAdd = day;
							log.info("Delivery address Range: " + from + " To " + to + ", Days: " + daysToAdd);
							break;
						} 
//						else {
//							daysToAdd = 7;
//						}

					}
				}
			}

			log.info("Days added in today: " + daysToAdd);
			LocalDate deliveryExpectedDate = today.plusDays(daysToAdd);

			resp.put("estimatedDate", deliveryExpectedDate);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}

	public static double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // Earths radius in kms

		double dLat = Math.toRadians(lat2 - lat1); // lat in radians
		double dLon = Math.toRadians(lon2 - lon1); // lon in radians

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c; // Distance in kms
	}

}
