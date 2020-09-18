package com.egar.exercise.dto.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class TableStringCreateDTO {

    @NotBlank(message = "Название компании должно быть заполнено!")
    private String companyName;

    @Positive(message = "Цена акции не может быть меньше 0!")
    private Double sharePrice;

    @NotNull(message = "Дата должна быть указана")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Дата должна быть не позднее текущей")
    private LocalDate priceDate;

    public TableStringCreateDTO() {
    }

    public TableStringCreateDTO(
            @NotBlank(message = "Название компании должно быть заполнено!") String companyName,
            @NotNull(message = "Дата должна быть указана")
            @PastOrPresent(message = "Дата должна быть не позднее текущей")
                    LocalDate priceDate,
            @Positive(message = "Цена акции не может быть меньше 0!") Double sharePrice
            ) {
        this.companyName = companyName;
        this.sharePrice = sharePrice;
        this.priceDate = priceDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Double sharePrice) {
        this.sharePrice = sharePrice;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }
}
