package com.andrew.lightweightitunescatalog.service;

import com.andrew.lightweightitunescatalog.dto.CatalogRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CatalogServiceTest {

    private CatalogService subject;

    @Mock
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<Map<String, String>> uriVariablesArgumentCaptor;

    @Test
    public void search_givenParameter_callsApiWithCorrectUrl_andReturnsResult() {
        subject = new CatalogService(restTemplate);

        CatalogRequest.MetaRequest request1 = new CatalogRequest.MetaRequest();
        request1.setKind("movie");
        request1.setTrackId(1);
        CatalogRequest.MetaRequest request2 = new CatalogRequest.MetaRequest();
        request2.setKind("song");
        request2.setTrackId(2);
        CatalogRequest.MetaRequest request3 = new CatalogRequest.MetaRequest();
        request3.setKind("movie");
        request3.setTrackId(3);

        CatalogRequest catalogRequest = new CatalogRequest();
        catalogRequest.setResults(Arrays.asList(request1, request2, request3));
        ResponseEntity<CatalogRequest> entity = new ResponseEntity<>(catalogRequest, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), any(), any(Map.class))).thenReturn(entity);

        Map<String, List<CatalogRequest.MetaResponse>> result = subject.search("jack+johnson");

        verify(restTemplate).getForEntity(eq("/search?term={term}"), eq(CatalogRequest.class), uriVariablesArgumentCaptor.capture());

        Map<String, String> uriVariables = uriVariablesArgumentCaptor.getValue();
        assertThat(uriVariables.keySet().size()).isEqualTo(1);
        assertThat(uriVariables.get("term")).isEqualTo("jack+johnson");

        assertThat(result.keySet().size()).isEqualTo(2);
        assertThat(result.keySet().contains("movie")).isTrue();
        assertThat(result.keySet().contains("song")).isTrue();

        List<CatalogRequest.MetaResponse> movies = result.get("movie");
        assertThat(movies.size()).isEqualTo(2);
        assertThat(movies.get(0).getId()).isEqualTo(1);
        assertThat(movies.get(1).getId()).isEqualTo(3);

        List<CatalogRequest.MetaResponse> songs = result.get("song");
        assertThat(songs.size()).isEqualTo(1);
        assertThat(songs.get(0).getId()).isEqualTo(2);
    }
}