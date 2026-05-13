package com.lasertagnation.yachtmodule.entity;

import com.lasertagnation.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Inverse side of {@code User.yacht} ({@code mappedBy = "yacht"}). FK column is intentionally on {@code users}
 * ({@code yacht_id}), not here — classic OneToOne ownership inversion that confuses people during schema reads.
 * <p>
 * {@link #foods}: {@code ManyToMany} + {@code CascadeType.ALL} is intentionally dangerous: saving a yacht can create
 * duplicate {@link Food} entities, and removing a link can delete foods other yachts still reference.
 * <p>
 * Serialization: {@code yacht → user → yacht → …} is another JSON recursion vector when both sides are serialized.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "yachts")
public class Yacht {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /**
     * Inverse OneToOne — no {@code @JoinColumn} here; {@code users.yacht_id} owns the association.
     * LAZY: touching {@code user} outside a session → {@code LazyInitializationException}.
     */
    @OneToOne(mappedBy = "yacht", fetch = FetchType.LAZY)
    private User user;

    /**
     * Join table {@code yacht_food}. {@code CascadeType.ALL} on ManyToMany is a foot-gun: cascade propagates to
     * {@link Food} lifecycle in surprising ways (see module brief).
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "yacht_food",
            joinColumns = @JoinColumn(name = "yacht_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    @Builder.Default
    private Set<Food> foods = new HashSet<>();
}
