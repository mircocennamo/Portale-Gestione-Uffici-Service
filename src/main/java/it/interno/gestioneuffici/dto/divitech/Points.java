package it.interno.gestioneuffici.dto.divitech;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Points {

    @JsonProperty("PointF")
    private List<PointF> pointF;

}
