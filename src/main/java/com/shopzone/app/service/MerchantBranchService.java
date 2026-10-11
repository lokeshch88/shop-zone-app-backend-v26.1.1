package com.shopzone.app.service;

import java.util.List;

import com.shopzone.app.dto.MerchantBranchDto;

public interface MerchantBranchService {

    MerchantBranchDto createBranch(
            Long merchantId,
            MerchantBranchDto branchDto
    );

    MerchantBranchDto updateBranch(
            Long merchantId,
            Long branchId,
            MerchantBranchDto branchDto
    );

    MerchantBranchDto getBranchById(
            Long merchantId,
            Long branchId
    );

    List<MerchantBranchDto> getAllBranchesByMerchant(
            Long merchantId
    );

    List<MerchantBranchDto> getActiveBranchesByMerchant(
            Long merchantId
    );

    void deleteBranch(
            Long merchantId,
            Long branchId
    );
}