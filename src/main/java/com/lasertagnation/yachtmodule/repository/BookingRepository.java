package com.lasertagnation.yachtmodule.repository;

import com.lasertagnation.yachtmodule.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Spring Data derived query — still one query for the list, but any follow-up lazy loads on {@code user} are
     * separate selects (N+1) when callers touch the association outside a fetch strategy.
     */
    List<Booking> findByUser_Id(Long userId);
}
