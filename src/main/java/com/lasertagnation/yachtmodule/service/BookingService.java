package com.lasertagnation.yachtmodule.service;

import com.lasertagnation.yachtmodule.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {

    List<Booking> findAll();

    Page<Booking> findAll(Pageable pageable);

    Booking findById(Long id);

    List<Booking> findByUserId(Long userId);

    Booking save(Booking booking);

    void deleteById(Long id);
}
