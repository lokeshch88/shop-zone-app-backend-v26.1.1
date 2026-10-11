package com.shopzone.app.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.shopzone.app.dto.AddressDto;
import com.shopzone.app.entity.Address;
import com.shopzone.app.entity.PincodeResponse;
import com.shopzone.app.entity.PostOffice;
import com.shopzone.app.entity.User;
import com.shopzone.app.repo.AddressRepository;
import com.shopzone.app.repo.UserRepo;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
	private WebClient webClient;

    @Autowired
	private ModelMapper modelMapper;

	private static final Logger log=LoggerFactory.getLogger(AddressService.class);

    public List<AddressDto> getUserAddresses(Long userId) {
        return addressRepo.findByUserId(userId).stream().map(this :: mapToDto).collect(Collectors.toList());
    }

    public AddressDto saveAddress(AddressDto dto) {
    	log.info("Saving address for user ID: {}", dto.getUserId()+" with address : "+dto);
        User user = userRepo.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Address address = modelMapper.map(dto, Address.class);
      
//        Address address = new Address();
//        address.setAddressLine(dto.getAddressLine());
//        address.setAddressLine2(dto.getAddressLine2());
//        address.setCity(dto.getCity());
//        address.setDistrict(dto.getDistrict());
//        address.setState(dto.getState());
//        address.setCountry(dto.getCountry()); 
//        address.setPincode(dto.getPincode());
//        address.setLatitude(dto.getLatitude());
//        address.setLongitude(dto.getLongitude());
        address.setUser(user);
        
//        address = addressRepo.save(address);

        return mapToDto(addressRepo.save(address));
    }

    public void deleteAddress(Long id) {
        addressRepo.deleteById(id);
    }

    private AddressDto mapToDto(Address a) {
//        AddressDto dto = new AddressDto();
//		dto.setId(a.getId());
//        dto.setAddressLine(a.getAddressLine());
//        dto.setCity(a.getCity());
//        dto.setDistrict(a.getDistrict());
//        dto.setState(a.getState());
//        dto.setCountry(a.getCountry());
//        dto.setPincode(a.getPincode());
//        dto.setLatitude(a.getLatitude());
//        dto.setLongitude(a.getLongitude());
//        dto.setUserId(a.getUser().getId());
//        return dto;
    	return modelMapper.map(a, AddressDto.class);
    }

    public AddressDto fetchPincode(AddressDto dto) {

        log.info("Fetching pincode details for pincode: {}",
                dto.getPincode());

        try {

            PincodeResponse[] response = webClient.get()
                    .uri("https://api.postalpincode.in/pincode/{pincode}",
                            dto.getPincode())
                    .retrieve()
                    .bodyToMono(PincodeResponse[].class)
                    .block();

            // Check response
            if (response == null || response.length == 0) {

                log.warn("No response received for pincode: {}",
                        dto.getPincode());

                return dto;
            }

            PincodeResponse pincodeResponse = response[0];

            // Check status
            if (!"Success".equalsIgnoreCase(
                    pincodeResponse.getStatus())) {

                log.warn("Pincode not found: {}",
                        dto.getPincode());

                return dto;
            }

            // Check post office
            if (pincodeResponse.getPostOffice() == null ||
                    pincodeResponse.getPostOffice().isEmpty()) {

                log.warn("No post office found for pincode: {}",
                        dto.getPincode());

                return dto;
            }

            // Get first post office
            PostOffice office =
                    pincodeResponse.getPostOffice().get(0);

            log.info("Pincode details found: {}", office.getName());

            // Set values into AddressDto
            dto.setPincode(office.getPincode());
            dto.setDistrict(office.getDistrict());
            dto.setState(office.getState());
            dto.setCountry(office.getCountry());

            // If you want to use post office as city
            dto.setCity(office.getName());

            return dto;

        } catch (Exception e) {

            log.error(
                    "Error fetching pincode details for {}",
                    dto.getPincode(),
                    e
            );

            return dto;
        }
    }

	public AddressDto updateAddress(Long id, AddressDto dto) {
		try {
	
		 Address address = addressRepo.findById(id)
			        .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));

			    //  Update the fields
			    address.setAddressLine(dto.getAddressLine());
			    address.setAddressLine2(dto.getAddressLine2());
			    address.setCity(dto.getCity());
			    address.setDistrict(dto.getDistrict());
			    address.setState(dto.getState());
			    address.setCountry(dto.getCountry());
			    address.setPincode(dto.getPincode());
			    address.setLatitude(dto.getLatitude());
			    address.setLongitude(dto.getLongitude());
			    address.setName(dto.getName());
			    address.setPhone(dto.getPhone());

			    // 3. Save and return the updated entity
			    Address add= addressRepo.save(address);
			    
			    return modelMapper.map(add, AddressDto.class);
		}
		catch (Exception e) {
			log.error("Error updating address with id: {}", id, e);
			
			throw new RuntimeException("Error updating address with id: " + id, e);
		
		}
	}
}

