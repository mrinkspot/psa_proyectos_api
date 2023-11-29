package psa.api_proyectos.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import psa.api_proyectos.application.dtos.ColaboradorDto;
import psa.api_proyectos.application.exceptions.ColaboradorNoEncontradoException;

import java.util.ArrayList;

@Service
public class ColaboradorService {
    private final String COLABORADORES_ENDPOINT = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos";

    public ArrayList<ColaboradorDto> getColaboradores() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(COLABORADORES_ENDPOINT, String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return mapper.readValue(response, new TypeReference<>() {});
    }

    public ColaboradorDto getColaboradorByLegajo(long colaboradorLegajo) throws JsonProcessingException {
        ColaboradorDto colaborador = getColaboradores().stream().filter(c -> c.getLegajo() == colaboradorLegajo).findFirst().orElse(null);
        if (colaborador == null) {
            throw new ColaboradorNoEncontradoException("No existe un Colaborador con legajo " + colaboradorLegajo);
        }
        return colaborador;
    }
}
