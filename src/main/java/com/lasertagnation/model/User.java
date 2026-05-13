package com.lasertagnation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.lasertagnation.yachtmodule.entity.Booking;
import com.lasertagnation.yachtmodule.entity.Yacht;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String cnic;

    private Boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /**
     * YachtModule (intentionally bad): {@code yacht_id} lives on {@code users}.
     * <p>
     * {@code CascadeType.ALL} here propagates persist/remove to the linked {@link Yacht} in ways that are easy to misuse
     * (e.g. deleting or overwriting a user graph and taking the yacht with it, depending on operation order).
     * <p>
     * {@code LAZY} + returning {@code User} from REST without a session → classic {@code LazyInitializationException}
     * when JSON tries to touch {@code yacht} outside a transaction.
     * <p>
     * N+1: listing users and accessing {@code yacht} per row issues one extra select per user (no fetch join by design).
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "yacht_id")
    private Yacht yacht;

    /**
     * Bidirectional with {@link Booking#user}. {@code CascadeType.ALL} on the collection is dangerous: operations on one
     * booking can cascade lifecycle events across siblings sharing the same user graph.
     * <p>
     * {@code orphanRemoval = false} (default) — removing a booking from this list without explicit delete can leave
     * orphan rows or inconsistent state depending on how the app mutates the graph.
     * <p>
     * JSON: {@code user → bookings → user → …} causes infinite recursion if both sides serialize without guards.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();
}

