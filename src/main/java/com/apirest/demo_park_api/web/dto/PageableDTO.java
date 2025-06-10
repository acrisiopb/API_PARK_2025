package com.apirest.demo_park_api.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
public class PageableDTO {
 
    private List content = new ArrayList<>();

    private boolean first;

    private boolean last;

    @JsonProperty("page")
    private Integer number;

    @JsonProperty("pageElements")
    private Integer numberOfElementes;

    private Integer totalPages;

    private Integer totalElements;


}
