package com.gloomy.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 04/04/2017.
 */
@Entity
@Table(name = "province")
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id")
    @JsonProperty("province_id")
    private int provinceId;

    @Column(name = "province_name")
    @JsonProperty("province_name")
    private String provinceName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties("first_five_provinces")
    private City city;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    @JsonIgnore
    private Set<PlaceAddress> placeAddresses;

    @JsonProperty("number_place_text")
    public String getNumberPlaceText() {
        StringBuilder builder = new StringBuilder(String.valueOf(placeAddresses.size())).append(" address");
        if (placeAddresses.size() > 1) {
            builder.append("es");
        }
        return builder.toString();
    }
}
