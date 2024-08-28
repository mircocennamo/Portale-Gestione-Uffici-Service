package it.interno.gestioneuffici.dto.divitech;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {

    @JsonProperty("ListRoad")
    private ListRoad listRoad;

    @JsonProperty("xmlns")
    private String xmlns;

    @JsonProperty("ListGeom")
    private String listGeom;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Result")
    private String result;

}
