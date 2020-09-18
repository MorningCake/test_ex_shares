package com.egar.exercise.dto.mappers;

import com.egar.exercise.dto.pojo.TableStringReadUpdateDTO;
import com.egar.exercise.persistance.SharePrice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TableStringReadUpdateMapper {

    public TableStringReadUpdateMapper() {
    }

    public List<TableStringReadUpdateDTO> toDtoList (List<SharePrice> list) {
        return list.stream().map(str -> new TableStringReadUpdateDTO(
                str.getCompany().getName(),
                str.getId(),
                str.getPrice(),
                str.getDate()
        )).collect(Collectors.toList());
    }
}
