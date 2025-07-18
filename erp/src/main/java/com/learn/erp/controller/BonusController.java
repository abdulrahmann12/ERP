package com.learn.erp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.BonusCreateDTO;
import com.learn.erp.dto.BonusResponseDTO;
import com.learn.erp.service.BonusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Bonus Controller",
	    description = "API for managing employee bonuses (create, update, view bonuses)."
	)
@RestController
@RequestMapping("/api/bonuses")
@RequiredArgsConstructor
public class BonusController {

	private final BonusService bonusService;
	
    @Operation(
            summary = "Create bonus",
            description = "HR can create a bonus for a specific employee."
    )
    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<BasicResponse> createBonus(@RequestBody BonusCreateDTO dto) {
        BonusResponseDTO response = bonusService.createBonus(dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_BONUS, response));
    }
    
    @Operation(
            summary = "Get bonuses for user",
            description = "Retrieve a list of bonuses for a specific employee, optionally filtered by month and year."
    )
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<BonusResponseDTO>> getBonusesForUser(
    		@PathVariable Long userId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        int targetMonth = (month != null) ? month : LocalDate.now().getMonthValue();
        int targetYear = (year != null) ? year : LocalDate.now().getYear();

        List<BonusResponseDTO> bonuses = bonusService.getBonusesForUser(userId, targetMonth, targetYear);
        return ResponseEntity.ok(bonuses);
    }
    
    @Operation(
            summary = "Delete bonus",
            description = "Delete a specific bonus by its ID."
    )
    @DeleteMapping("/{bonusId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<BasicResponse> deleteBonus(@PathVariable Long bonusId) {
        bonusService.deleteBonus(bonusId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_BONUS, null));
    }
}
