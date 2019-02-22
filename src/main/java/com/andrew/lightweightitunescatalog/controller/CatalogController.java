package com.andrew.lightweightitunescatalog.controller;

import com.andrew.lightweightitunescatalog.dto.CatalogRequest;
import com.andrew.lightweightitunescatalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/search")
    public Map<String, List<CatalogRequest.MetaResponse>> search(@RequestParam String search) {
        return catalogService.search(search);
    }
}