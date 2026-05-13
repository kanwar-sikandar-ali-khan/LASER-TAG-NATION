package com.lasertagnation.carmodule.service;

import com.lasertagnation.carmodule.dto.CarBookingDto;
import com.lasertagnation.carmodule.dto.CreateCarBookingRequest;
import com.lasertagnation.carmodule.entity.Booking;
import com.lasertagnation.carmodule.mapper.CarBookingMapper;
import com.lasertagnation.carmodule.repository.CarBookingRepository;
import com.lasertagnation.exception.RecordNotFoundException;
import com.lasertagnation.model.User;
import com.lasertagnation.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Writes run with {@code @Transactional} so bidirectional updates to {@link User#getCarBookings()} and the
 * {@link Booking} row stay consistent within one flush cycle.
 */
@Service
public class CarModuleBookingService {

    private final CarBookingRepository carBookingRepository;
    private final UserRepository userRepository;

    public CarModuleBookingService(CarBookingRepository carBookingRepository, UserRepository userRepository) {
        this.carBookingRepository = carBookingRepository;
        this.userRepository = userRepository;
    }

    /**
     * EntityGraph + page on {@code CarBooking}: {@code ManyToOne} only — acceptable limited case (no collection join).
     */
    @Transactional(readOnly = true)
    public Page<CarBookingDto> findBookingsPageWithUserEntityGraph(Pageable pageable) {
        return carBookingRepository.findAllPagedWithUserGraph(pageable).map(CarBookingMapper::toDto);
    }

    @Transactional(readOnly = true)
    public CarBookingDto getByIdWithUserJoinFetch(Long id) {
        Booking booking = carBookingRepository.findWithUserJoinFetchById(id)
                .orElseThrow(() -> new RecordNotFoundException("Car booking not found: " + id));
        return CarBookingMapper.toDto(booking);
    }

    @Transactional
    public CarBookingDto create(CreateCarBookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RecordNotFoundException("User not found: " + request.getUserId()));
        Booking booking = Booking.builder()
                .user(user)
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        user.getCarBookings().add(booking);
        Booking saved = carBookingRepository.save(booking);
        return CarBookingMapper.toDto(saved);
    }
}
