package com.lasertagnation.yachtmodule.service.impl;

import com.lasertagnation.model.User;
import com.lasertagnation.repository.UserRepository;
import com.lasertagnation.yachtmodule.entity.Booking;
import com.lasertagnation.yachtmodule.repository.BookingRepository;
import com.lasertagnation.yachtmodule.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Page<Booking> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findByUser_Id(userId);
    }

    /**
     * {@code cascade=ALL} on {@code booking.user}: saving a booking can cascade merge/persist to the attached
     * {@link User} and everything reachable from that user (yacht, other bookings) — watch flush order and SQL.
     */
    @Transactional
    @Override
    public Booking save(Booking booking) {
        if (booking.getUser() != null && booking.getUser().getId() != null) {
            User managed = userRepository.getById(booking.getUser().getId());
            booking.setUser(managed);
        }
        return bookingRepository.save(booking);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }
}
