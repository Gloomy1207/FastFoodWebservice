package com.gloomy.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 07/04/2017.
 */
@Builder
@Getter
@Setter
public class LatLng {
    private double lat;
    private double lng;
}
