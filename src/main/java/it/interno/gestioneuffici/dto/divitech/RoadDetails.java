package it.interno.gestioneuffici.dto.divitech;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoadDetails  {

    @JsonProperty("Before")
    private String before;

    @JsonProperty("Province_Abbreviation")
    private String province_Abbreviation;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("CivicNumber")
    private String civicNumber;

    @JsonProperty("Roadname")
    private String roadname;

    @JsonProperty("AdminName")
    private String adminName;

    @JsonProperty("Iso_Country_Code")
    private String iso_Country_Code;

    @JsonProperty("Parent_Id")
    private String parent_Id;

    @JsonProperty("SmlAdmin")
    private String smlAdmin;

    @JsonProperty("RoadLinkId")
    private String roadLinkId;

    @JsonProperty("DataDescription")
    private String dataDescription;

    @JsonProperty("SmlAdminRoad")
    private String smlAdminRoad;

    @JsonProperty("RoadNameId")
    private String roadNameId;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Attached")
    private String attached;

    @JsonProperty("AdminLevel")
    private String adminLevel;

    @JsonProperty("GovCode")
    private String govCode;

    @JsonProperty("AdminLanguage")
    private String adminLanguage;

    @JsonProperty("Synonym")
    private String synonym;

    @JsonProperty("Route_Type")
    private String route_Type;

    @JsonProperty("Parent_Name")
    private String parent_Name;

    @JsonProperty("LinkList")
    private LinkList linkList;

    @JsonProperty("AdminPlaceID")
    private String adminPlaceID;

}
