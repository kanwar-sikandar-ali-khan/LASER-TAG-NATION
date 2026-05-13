package com.lasertagnation.carmodule.entity;

import com.lasertagnation.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * CarModule vehicle (table {@code cars}).
 * <p>
 * {@code ManyToMany} with {@link Food}: join table {@code car_food}, {@code LAZY} by default for the bag/set.
 * Deliberately <strong>no</strong> {@code CascadeType.ALL}: cascading on {@code ManyToMany} can persist/remove
 * {@link Food} instances you intended as shared reference data, or duplicate rows when merge order surprises you.
 * <p>
 * {@link BatchSize} on {@link #foods}: when N cars are loaded and {@code foods} is accessed, Hibernate can batch-load
 * several collections in fewer round-trips than strict per-collection selects (reduces classic N+1 fan-out).
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "CarRoot")
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;

    private String model;

    /**
     * Owning side of {@code car_food}. No cascade ALL — see class Javadoc.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "car_food",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    @BatchSize(size = 16)
    @Builder.Default
    private Set<Food> foods = new HashSet<>();

    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY)
    private User user;
}
