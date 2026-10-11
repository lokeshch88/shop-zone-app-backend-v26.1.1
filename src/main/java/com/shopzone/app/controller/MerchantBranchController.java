package com.shopzone.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopzone.app.dto.MerchantBranchDto;
import com.shopzone.app.service.MerchantBranchService;


@RestController
@RequestMapping("/api/merchants/{merchantId}/branches")
//@RequiredArgsConstructor
public class MerchantBranchController {

    private final MerchantBranchService branchService;
    
    

    public MerchantBranchController(MerchantBranchService branchService) {
		super();
		this.branchService = branchService;
	}

	@PostMapping
    public ResponseEntity<MerchantBranchDto> createBranch(
            @PathVariable Long merchantId,
            @RequestBody MerchantBranchDto branchDto) {

        return new ResponseEntity<>(
                branchService.createBranch(
                        merchantId,
                        branchDto
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<MerchantBranchDto> updateBranch(
            @PathVariable Long merchantId,
            @PathVariable Long branchId,
            @RequestBody MerchantBranchDto branchDto) {

        return ResponseEntity.ok(
                branchService.updateBranch(
                        merchantId,
                        branchId,
                        branchDto
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<MerchantBranchDto>> getAllBranches(
            @PathVariable Long merchantId) {

        return ResponseEntity.ok(
                branchService.getAllBranchesByMerchant(
                        merchantId
                )
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<MerchantBranchDto>>
            getActiveBranches(
                    @PathVariable Long merchantId) {

        return ResponseEntity.ok(
                branchService.getActiveBranchesByMerchant(
                        merchantId
                )
        );
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<MerchantBranchDto> getBranchById(
            @PathVariable Long merchantId,
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                branchService.getBranchById(
                        merchantId,
                        branchId
                )
        );
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<String> deleteBranch(
            @PathVariable Long merchantId,
            @PathVariable Long branchId) {

        branchService.deleteBranch(
                merchantId,
                branchId
        );

        return ResponseEntity.ok(
                "Branch deleted successfully"
        );
    }
}