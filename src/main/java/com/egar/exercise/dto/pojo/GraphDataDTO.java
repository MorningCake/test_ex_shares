package com.egar.exercise.dto.pojo;

import java.util.List;

public class GraphDataDTO {
    private String companyName;
    private List<GraphPointDTO> points;

    public GraphDataDTO() {
    }

    public GraphDataDTO(String companyName, List<GraphPointDTO> points) {
        this.companyName = companyName;
        this.points = points;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<GraphPointDTO> getPoints() {
        return points;
    }
}
