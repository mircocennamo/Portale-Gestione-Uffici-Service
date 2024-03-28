package it.interno.gestioneuffici.dto.divitech;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LinkList {

    @JsonProperty("LinkDetails")
    private List<LinkDetails> linkDetails;

}
