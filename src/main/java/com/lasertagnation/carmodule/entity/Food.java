package com.lasertagnation.carmodule.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * CarModule catalog row (table {@code car_foods}) — distinct from YachtModule {@code foods}.
 * <p>
 * Inverse side of {@link Car#getFoods()}: no {@code CascadeType.ALL} on {@code ManyToMany} so linking cars does not
 * accidentally create/remove shared catalog rows across unrelated aggregates.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "CarCatalogFood")
@Table(name = "car_foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    @ManyToMany(mappedBy = "foods", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Car> cars = new HashSet<>();
}
