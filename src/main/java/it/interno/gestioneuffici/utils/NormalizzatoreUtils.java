package it.interno.gestioneuffici.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.interno.gestioneuffici.dto.divitech.IndirizzoNormalizzatoDto;
import it.interno.gestioneuffici.dto.divitech.MiddlePoint;
import it.interno.gestioneuffici.dto.divitech.Response;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public abstract class NormalizzatoreUtils {

    private NormalizzatoreUtils() {
    }

    private static String url = "http://192.168.21.20:8088/GRPostgres/GRPService.asmx/GetRoadDetailsLimited?";

    private static String extractCivicNumber(String indirizzo) {

        String[] s = indirizzo.split(",");
        return s.length == 2 || s.length > 2 ? s[1].trim().replace(" ", "") : "";
    }

    public static List<IndirizzoNormalizzatoDto> callDivitech(String comune, String indirizzo) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String encodingType = "UTF-8";

        // Codifico le informazione nello standard UTF-8
        String encodedLocation = URLEncoder.encode(comune.toUpperCase(), encodingType);
        String encodedAddress = URLEncoder.encode(indirizzo.toUpperCase(), encodingType);
        String encodedCivicNumber = URLEncoder.encode(extractCivicNumber(indirizzo.toUpperCase()), encodingType);
        String encodedMaxRoadNumber = URLEncoder.encode("4", encodingType);

        Request request = new Request.Builder()
                .url(url + "admiName=" + encodedLocation + "&roadName=" + encodedAddress + "&civNumber=" + encodedCivicNumber + "&roadsMaxNum=" + encodedMaxRoadNumber)
                .get()
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();

        JSONObject jsonObject = XML.toJSONObject(responseBody.string());

        ObjectMapper objectMapper = new ObjectMapper() ;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        Response resDivitech = objectMapper.readValue(jsonObject.toString(), Response.class);

        return processResponse(resDivitech);
    }

    private static List<IndirizzoNormalizzatoDto> processResponse(Response response){

        List<IndirizzoNormalizzatoDto> result = new ArrayList<>();

        if("NO ROAD FOUND".equalsIgnoreCase(response.getMessage()) || response.getListRoad() == null)
            return result;

        response.getListRoad().getRoadDetails().forEach(strada -> {

            IndirizzoNormalizzatoDto temp = new IndirizzoNormalizzatoDto();

            temp.setComune(strada.getAdminName());
            temp.setIndirizzo(StringUtils.isBlank(strada.getCivicNumber()) ? strada.getAddress() : strada.getAddress() + ", " + strada.getCivicNumber());
            MiddlePoint tempMiddlePoint = strada.getLinkList().getLinkDetails().stream().findFirst().get().getMiddlePoint();
            temp.setCoordinataX(tempMiddlePoint.getX());
            temp.setCoordinataY(tempMiddlePoint.getY());

            result.add(temp);
        });

        return result;
    }
}
