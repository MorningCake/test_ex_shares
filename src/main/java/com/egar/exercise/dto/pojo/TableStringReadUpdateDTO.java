package com.egar.exercise.dto.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class TableStringReadUpdateDTO {

    @NotBlank(message = "Название компании должно быть заполнено!")
    private String companyName;

    @NotNull(message = "ID цены должен быть указан")
    private Long priceId;

    @Positive(message = "Цена акции не может быть меньше 0!")
    private Double sharePrice;

    @NotNull(message = "Дата должна быть указана")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent(message = "Дата должна быть не позднее текущей")
    private LocalDate priceDate;

    public TableStringReadUpdateDTO() {
    }

    public TableStringReadUpdateDTO(
            @NotBlank(message = "Название компании должно быть заполнено!") String companyName,
            @NotNull(message = "ID цены должен быть указан") Long priceId,
            @Positive(message = "Цена акции не может быть меньше 0!") Double sharePrice,
            @NotNull(message = "Дата должна быть указана")
            @PastOrPresent(message = "Дата должна быть не позднее текущей")
                    LocalDate priceDate) {
        this.companyName = companyName;
        this.priceId = priceId;
        this.sharePrice = sharePrice;
        this.priceDate = priceDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
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
