package com.shopzone.app.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shopzone.app.dto.MerchantBranchDto;
import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.MerchantBranch;
import com.shopzone.app.repo.MerchantBranchRepository;
import com.shopzone.app.repo.MerchantRepository;

@Service
//@RequiredArgsConstructor
public class MerchantBranchServiceImpl
        implements MerchantBranchService {

    private final MerchantBranchRepository branchRepository;
    private final MerchantRepository merchantRepository;
    private final ModelMapper modelMapper;

    
    
    public MerchantBranchServiceImpl(MerchantBranchRepository branchRepository, MerchantRepository merchantRepository, ModelMapper modelMapper) {
		super();
		this.branchRepository = branchRepository;
		this.merchantRepository = merchantRepository;
		this.modelMapper = modelMapper;
	}

	@Override
    public MerchantBranchDto createBranch(
            Long merchantId,
            MerchantBranchDto branchDto) {

        Merchant merchant = merchantRepository
                .findById(merchantId)
                .orElseThrow(() ->
                        new RuntimeException("Merchant not found"));

        if (branchDto.getBranchCode() != null
                && branchRepository.existsByBranchCode(
                        branchDto.getBranchCode())) {

            throw new RuntimeException(
                    "Branch code already exists");
        }

        MerchantBranch branch =
                modelMapper.map(
                        branchDto,
                        MerchantBranch.class
                );

        branch.setMerchant(merchant);

        if (branch.getIsActive() == null) {
            branch.setIsActive("Y");
        }

        MerchantBranch savedBranch =
                branchRepository.save(branch);

        MerchantBranchDto response =
                modelMapper.map(
                        savedBranch,
                        MerchantBranchDto.class
                );

        response.setMerchantId(merchantId);

        return response;
    }

	@Override
	@Transactional
	public MerchantBranchDto updateBranch(
	        Long merchantId,
	        Long branchId,
	        MerchantBranchDto dto) {

	    MerchantBranch branch = branchRepository
	            .findById(branchId)
	            .orElseThrow(() ->
	                    new RuntimeException("Branch not found"));

	    Merchant merchant = branch.getMerchant();

	    if (merchant == null ||
	        merchant.getId() == null ||
	        !merchant.getId().equals(merchantId)) {

	        throw new RuntimeException(
	                "Branch does not belong to this merchant");
	    }

	    // DO NOT map merchant or id from DTO

	    branch.setBranchName(dto.getBranchName());
	    branch.setBranchCode(dto.getBranchCode());
	    branch.setBranchType(dto.getBranchType());

	    branch.setFranchiseOwnerName(dto.getFranchiseOwnerName());
	    branch.setFranchiseOwnerEmail(dto.getFranchiseOwnerEmail());
	    branch.setFranchiseOwnerMobile(dto.getFranchiseOwnerMobile());

	    branch.setContactPersonName(dto.getContactPersonName());
	    branch.setContactNumber(dto.getContactNumber());
	    branch.setAlternateContactNumber(dto.getAlternateContactNumber());
	    branch.setEmail(dto.getEmail());

	    branch.setAddressLine1(dto.getAddressLine1());
	    branch.setAddressLine2(dto.getAddressLine2());
	    branch.setLandmark(dto.getLandmark());
	    branch.setCity(dto.getCity());
	    branch.setDistrict(dto.getDistrict());
	    branch.setState(dto.getState());
	    branch.setCountry(dto.getCountry());
	    branch.setPincode(dto.getPincode());

	    branch.setLatitude(dto.getLatitude());
	    branch.setLongitude(dto.getLongitude());
	    branch.setDeliveryRadiusKm(dto.getDeliveryRadiusKm());

	    branch.setGstNumber(dto.getGstNumber());
	    branch.setFssaiNumber(dto.getFssaiNumber());
	    branch.setLicenseNumber(dto.getLicenseNumber());

	    branch.setDeliveryAvailable(dto.getDeliveryAvailable());
	    branch.setPickupAvailable(dto.getPickupAvailable());
	    branch.setDineInAvailable(dto.getDineInAvailable());

	    branch.setOpeningTime(dto.getOpeningTime());
	    branch.setClosingTime(dto.getClosingTime());
	    branch.setWorkingDays(dto.getWorkingDays());

	    branch.setIsActive(dto.getIsActive());
//	    branch.setIsDeleted(dto.getIsDeleted());

	    // NEVER change these
	    branch.setId(branchId);
	    branch.setMerchant(merchant);

	    MerchantBranch updatedBranch =
	            branchRepository.save(branch);

	    MerchantBranchDto response =
	            modelMapper.map(
	                    updatedBranch,
	                    MerchantBranchDto.class
	            );

	    response.setMerchantId(merchantId);

	    return response;
	}

    @Override
    public MerchantBranchDto getBranchById(
            Long merchantId,
            Long branchId) {

        MerchantBranch branch =
                branchRepository.findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException("Branch not found"));

        if (!branch.getMerchant()
                .getId()
                .equals(merchantId)) {

            throw new RuntimeException(
                    "Branch does not belong to this merchant");
        }

        MerchantBranchDto response =
                modelMapper.map(
                        branch,
                        MerchantBranchDto.class
                );

        response.setMerchantId(merchantId);

        return response;
    }

    @Override
    public List<MerchantBranchDto> getAllBranchesByMerchant(
            Long merchantId) {

        return branchRepository.findByMerchantId(merchantId)
                .stream()
                .map(branch -> {

                    MerchantBranchDto dto =
                            modelMapper.map(
                                    branch,
                                    MerchantBranchDto.class
                            );

                    dto.setMerchantId(merchantId);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MerchantBranchDto> getActiveBranchesByMerchant(
            Long merchantId) {

        return branchRepository
                .findByMerchantIdAndIsActive(
                        merchantId,
                        "Y"
                )
                .stream()
                .map(branch -> {

                    MerchantBranchDto dto =
                            modelMapper.map(
                                    branch,
                                    MerchantBranchDto.class
                            );

                    dto.setMerchantId(merchantId);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBranch(
            Long merchantId,
            Long branchId) {

        MerchantBranch branch =
                branchRepository.findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException("Branch not found"));

        if (!branch.getMerchant()
                .getId()
                .equals(merchantId)) {

            throw new RuntimeException(
                    "Branch does not belong to this merchant");
        }

        // Soft delete
        branch.setIsActive("N");

        branchRepository.save(branch);
    }
}