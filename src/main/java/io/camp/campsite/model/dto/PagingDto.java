package io.camp.campsite.model.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class PagingDto {
    private String query="";
    private int page=0;
    private int size=6;
    private String type="";
}
