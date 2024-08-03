package io.camp.campsite.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import io.camp.reservation.model.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Campsite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private int contentId;

    private String facltNm;

    private String lineIntro;

    @Column(columnDefinition = "TEXT")
    private String intro;

    private int allar;

    private String trsagntNo;

    private String bizrno;

    @Column(columnDefinition = "TEXT")
    private String featureNm;

    private String induty;

    private String lctCl;

    private String doNm;

    private String sigunguNm;

    private String zipcode;

    private String addr1;

    private String addr2;

    private String mapX;

    private String mapY;

    private String direction;

    private String tel;

    private String homepage;

    private String resveUrl;

    private int glampSiteCo;

    private int caravSiteCo;

    private String siteBottomCl1;

    private String siteBottomCl2;

    private String siteBottomCl3;

    private String siteBottomCl4;

    private String siteBottomCl5;

    private String tooltip;

    private String glampInnerFclty;

    private String caravInnerFclty;

    private String operPdCl;

    private String operDeCl;

    private int toiletCo;

    private int swrmCo;

    private int wtrplCo;

    private String brazierCl;

    private String sbrsCl;

    private String sbrsEtc;

    private String posblFcltyCl;

    private String posblFcltyEtc;

    private String themaEnyrnCl;

    private String animalCmgCl;

    private String firstImageUrl;

    private String createdtime;

    private String modifiedtime;

    @OneToMany(mappedBy = "campsite" , fetch = FetchType.EAGER)
    private List<Zone> zones;
}
