package it.interno.gestioneuffici.dto.divitech;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LinkDetails {

    @JsonProperty("Right_House_Number_Format")
    private String right_House_Number_Format;

    @JsonProperty("Left_House_Number_Format")
    private String left_House_Number_Format;

    @JsonProperty("Right_First_Address")
    private String right_First_Address;

    @JsonProperty("Left_Last_Address")
    private String left_Last_Address;

    @JsonProperty("DescAttribute")
    private String descAttribute;

    @JsonProperty("Points")
    private Points points;

    @JsonProperty("Right_Last_Address")
    private String right_Last_Address;

    @JsonProperty("MiddlePoint")
    private MiddlePoint middlePoint;

    @JsonProperty("Right_House_Number_Scheme")
    private String right_House_Number_Scheme;

    @JsonProperty("Left_First_Address")
    private String left_First_Address;

    @JsonProperty("Left_House_Number_Scheme")
    private String left_House_Number_Scheme;

    @JsonProperty("Linkid")
    private String linkid;

    @JsonProperty("Distance")
    private String distance;

}
