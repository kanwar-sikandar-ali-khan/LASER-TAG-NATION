package com.lasertagnation.carmodule.controller;

import com.lasertagnation.carmodule.dto.CarBatchLoadRowDto;
import com.lasertagnation.carmodule.dto.CarBookingDto;
import com.lasertagnation.carmodule.dto.CarDetailDto;
import com.lasertagnation.carmodule.dto.CarSummaryDto;
import com.lasertagnation.carmodule.dto.CreateCarBookingRequest;
import com.lasertagnation.carmodule.service.CarModuleBookingService;
import com.lasertagnation.carmodule.service.CarModuleCarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * CarModule HTTP API — responses are DTOs only (no JPA entities on the wire).
 */
@RestController
@CrossOrigin
@RequestMapping("/api/carmodule")
public class CarModuleController {

    private final CarModuleCarService carModuleCarService;
    private final CarModuleBookingService carModuleBookingService;

    public CarModuleController(CarModuleCarService carModuleCarService, CarModuleBookingService carModuleBookingService) {
        this.carModuleCarService = carModuleCarService;
        this.carModuleBookingService = carModuleBookingService;
    }

    @GetMapping("/cars/{id}/join-fetch")
    public ResponseEntity<CarDetailDto> carDetailJoinFetch(@PathVariable Long id) {
        return ResponseEntity.ok(carModuleCarService.getByIdWithFoodsJoinFetch(id));
    }

    @GetMapping("/cars/{id}/entity-graph")
    public ResponseEntity<CarDetailDto> carDetailEntityGraph(@PathVariable Long id) {
        return ResponseEntity.ok(carModuleCarService.getByIdWithFoodsEntityGraph(id));
    }

    @GetMapping("/cars/paged/projection")
    public ResponseEntity<Page<CarSummaryDto>> carsSummaryPage(Pageable pageable) {
        return ResponseEntity.ok(carModuleCarService.findCarsSummaryPage(pageable));
    }

    @GetMapping("/cars/paged/two-step")
    public ResponseEntity<Page<CarDetailDto>> carsDetailTwoStep(Pageable pageable) {
        return ResponseEntity.ok(carModuleCarService.findCarsDetailPageTwoStep(pageable));
    }

    @GetMapping("/cars/paged/batch-demo")
    public ResponseEntity<Page<CarBatchLoadRowDto>> carsBatchDemo(Pageable pageable) {
        return ResponseEntity.ok(carModuleCarService.findCarsBatchDemoPage(pageable));
    }

    @GetMapping("/bookings/paged/entity-graph")
    public ResponseEntity<Page<CarBookingDto>> bookingsPageEntityGraph(Pageable pageable) {
        return ResponseEntity.ok(carModuleBookingService.findBookingsPageWithUserEntityGraph(pageable));
    }

    @GetMapping("/bookings/{id}/join-fetch")
    public ResponseEntity<CarBookingDto> bookingDetailJoinFetch(@PathVariable Long id) {
        return ResponseEntity.ok(carModuleBookingService.getByIdWithUserJoinFetch(id));
    }

    @PostMapping("/bookings")
    public ResponseEntity<CarBookingDto> createBooking(@Valid @RequestBody CreateCarBookingRequest request) {
        return ResponseEntity.ok(carModuleBookingService.create(request));
    }
}
