package com.lasertagnation.carmodule.repository;

import com.lasertagnation.carmodule.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CarModule bookings (bean name {@code carBookingRepository} — avoids clash with YachtModule {@code bookingRepository}).
 * <p>
 * {@link EntityGraph} on a {@code Page} of {@link Booking} with only a {@code ManyToOne} to {@code User} is a limited,
 * acceptable case: Hibernate can usually avoid row multiplication because there is no collection join on the fetched
 * side at the SQL level for this graph shape — unlike {@code join fetch car.foods} + {@code Pageable}.
 */
@Repository
public interface CarBookingRepository extends JpaRepository<Booking, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Query("select b from CarBooking b")
    Page<Booking> findAllPagedWithUserGraph(Pageable pageable);

    @Query("select b from CarBooking b join fetch b.user where b.id = :id")
    java.util.Optional<Booking> findWithUserJoinFetchById(@Param("id") Long id);

    List<Booking> findByUser_IdOrderByIdAsc(Long userId);
}
