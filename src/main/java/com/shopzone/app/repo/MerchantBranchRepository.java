package com.shopzone.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopzone.app.entity.MerchantBranch;

public interface MerchantBranchRepository
        extends JpaRepository<MerchantBranch, Long> {

    List<MerchantBranch> findByMerchantId(Long merchantId);

    List<MerchantBranch> findByMerchantIdAndIsActive(
            Long merchantId,
            String isActive
    );

    List<MerchantBranch> findByIsActive(String isActive);

    boolean existsByBranchCode(String branchCode);
}