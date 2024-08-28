package it.interno.gestioneuffici.dto.divitech;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PointF {

    @JsonProperty("X")
    private String x;

    @JsonProperty("Y")
    private String y;

}
