package com.andrew.lightweightitunescatalog.service;

import com.andrew.lightweightitunescatalog.dto.CatalogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CatalogService {

    private final RestTemplate restTemplate;

    @Autowired
    public CatalogService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, List<CatalogRequest.MetaResponse>> search(String searchParameter) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("term", searchParameter);

       CatalogRequest catalogRequest = this.restTemplate.getForEntity("/search?term={term}", CatalogRequest.class, uriVariables).getBody();

       return this.convertToResponse(catalogRequest);
    }

    private Map<String, List<CatalogRequest.MetaResponse>> convertToResponse(CatalogRequest request) {
        Map<String, List<CatalogRequest.MetaResponse>> response = new HashMap<>();

        request.getResults().stream().filter(r -> !StringUtils.isEmpty(r.getKind())).forEach(r -> {
            response.putIfAbsent(r.getKind(), new ArrayList<>());
            response.get(r.getKind()).add(new CatalogRequest.MetaResponse(r));
        });

        return response;
    }

}
