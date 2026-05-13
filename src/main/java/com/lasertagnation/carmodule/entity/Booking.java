package com.lasertagnation.carmodule.entity;

import com.lasertagnation.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * CarModule booking (table {@code car_bookings}) — distinct from YachtModule {@code bookings}.
 * <p>
 * Owning side of {@code User} ↔ booking via {@code user_id} on {@code car_bookings}.
 * <p>
 * <strong>No {@code CascadeType.ALL}</strong> on {@link #user}: persisting or merging a booking must not cascade to the
 * entire {@link User} graph (roles, yacht, sibling bookings). That pattern can cause accidental updates, constraint
 * violations, or deletes depending on flush order — especially dangerous when bookings are created from API DTOs
 * that attach a detached user reference.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "CarBooking")
@Table(name = "car_bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
