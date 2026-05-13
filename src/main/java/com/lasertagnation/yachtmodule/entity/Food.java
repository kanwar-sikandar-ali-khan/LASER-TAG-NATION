package com.lasertagnation.yachtmodule.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Simple menu/catalog row. Used from {@link Yacht}'s {@code ManyToMany} with {@code CascadeType.ALL} so that
 * associating a {@link Food} with a yacht can accidentally persist/remove {@code Food} rows you thought were shared
 * catalog entries (duplication + unintended deletes when clearing a yacht's menu).
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;
}
