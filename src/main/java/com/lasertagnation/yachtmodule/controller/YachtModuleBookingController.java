package com.lasertagnation.yachtmodule.controller;

import com.lasertagnation.yachtmodule.entity.Booking;
import com.lasertagnation.yachtmodule.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Returns {@link Booking} entities directly — Jackson + lazy {@code user} + bidirectional graphs → recursion, proxy
 * edge cases, and {@code LazyInitializationException} depending on which getters get invoked.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/yacht-module/bookings")
public class YachtModuleBookingController {

    private final BookingService bookingService;

    public YachtModuleBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.findAll();
    }

    /**
     * Bad pagination: {@code Page<Booking>} without tuning — count query + slice, then N+1 on {@code user} during JSON.
     */
    @GetMapping("/paged")
    public Page<Booking> getBookingsPaged(Pageable pageable) {
        return bookingService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Long id) {
        Booking b = bookingService.findById(id);
        return b == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(b);
    }

    @GetMapping("/by-user/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        return bookingService.findByUserId(userId);
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Long id, @RequestBody Booking booking) {
        if (bookingService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        booking.setId(id);
        return ResponseEntity.ok(bookingService.save(booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
