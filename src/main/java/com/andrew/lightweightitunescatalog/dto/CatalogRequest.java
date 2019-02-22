package com.andrew.lightweightitunescatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogRequest {

    List<MetaRequest> results;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MetaRequest {
        private String kind;
        private Integer trackId;
        private String trackName;
        private String artworkUrl30;
        private String primaryGenreName;
        private String trackViewUrl;
    }

    @Data
    @AllArgsConstructor
    public static class MetaResponse {

        public MetaResponse(MetaRequest metaRequest) {
            this.id = metaRequest.trackId;
            this.name = metaRequest.trackName;
            this.artwork = metaRequest.artworkUrl30;
            this.genre = metaRequest.primaryGenreName;
            this.url = metaRequest.getTrackViewUrl();
        }

        private Integer id;
        private String name;
        private String artwork;
        private String genre;
        private String url;
    }

}







