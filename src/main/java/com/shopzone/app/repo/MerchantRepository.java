package com.shopzone.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.MerchantStatus;
import com.shopzone.app.entity.SubscriptionPlan;
import com.shopzone.app.entity.SubscriptionStatus;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Optional<Merchant> findByMerchantCode(String merchantCode);

    Optional<Merchant> findByStoreSlug(String storeSlug);

    Optional<Merchant> findByCustomDomain(String customDomain);

    Optional<Merchant> findByOwnerEmail(String ownerEmail);

    boolean existsByMerchantCode(String merchantCode);

    boolean existsByStoreSlug(String storeSlug);

    boolean existsByCustomDomain(String customDomain);

    List<Merchant> findByStatus(MerchantStatus status);

    List<Merchant> findBySubscriptionPlan(SubscriptionPlan plan);

    List<Merchant> findBySubscriptionStatus(
            SubscriptionStatus subscriptionStatus
    );

	List<Merchant> findAllByActive(Boolean active);
}
