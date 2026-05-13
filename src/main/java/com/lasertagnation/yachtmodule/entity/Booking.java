package com.lasertagnation.yachtmodule.entity;

import com.lasertagnation.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Owning side of {@code User ↔ Booking} via {@code user_id} on {@code bookings}.
 * <p>
 * {@code ManyToOne(..., cascade = CascadeType.ALL)} is intentionally wrong for most domains: merging/persisting a
 * booking can cascade to the entire {@link User} graph (roles, yacht, sibling bookings depending on flush order),
 * causing accidental updates or constraint violations — the core “cascade side effects” lab.
 * <p>
 * N+1: listing bookings and reading {@code user} for each row triggers one SELECT per booking (no join fetch).
 * <p>
 * JSON: {@code booking → user → bookings → …} participates in infinite recursion when entities are returned raw.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Intentionally mirrors the bidirectional {@code User#bookings} with dangerous cascade settings.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
