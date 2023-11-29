package psa.api_proyectos.application.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecursosService {

    public String getRecursos() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos", String.class);
        return response;
    }
}
