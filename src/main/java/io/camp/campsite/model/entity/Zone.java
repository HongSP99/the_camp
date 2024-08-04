package io.camp.campsite.model.entity;

import io.camp.campsite.model.dto.SiteDto;
import io.camp.campsite.model.dto.ZoneDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

    @ManyToOne
    private Campsite campsite;

    @OneToMany(mappedBy = "zone" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private List<Site> sites;


    public ZoneDto toDto(){
        List<SiteDto> siteDtos =sites.stream().map(Site::toDto).toList();

      return  ZoneDto.builder()
                .seq(seq)
                .title(title)
                .intro(intro)
                .checkin(checkin)
                .checkout(checkout)
                .offSeasonPrice(offSeasonPrice)
                .peakSeasonPrice(peakSeasonPrice)
                .bestPeakSeasonPrice(bestPeakSeasonPrice)
                .sites(siteDtos).build();
    }
}
