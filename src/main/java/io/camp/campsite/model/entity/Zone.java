package io.camp.campsite.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String title;

    @Column
    private String intro;

    @Column
    private LocalTime checkin;

    @Column
    private LocalTime checkout;

    @Column
    private int offSeasonPrice;

    @Column
    private int peakSeasonPrice;

    @Column
    private int bestPeakSeasonPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    private Campsite campsite;
}
