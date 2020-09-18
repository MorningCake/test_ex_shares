package com.egar.exercise.dto.pojo;

import java.time.LocalDate;

public class GraphPointDTO {
    private LocalDate x;
    private Double y;

    public GraphPointDTO() {

    }

    public GraphPointDTO(LocalDate x, Double y) {
        this.x = x;
        this.y = y;
    }

    public LocalDate getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
