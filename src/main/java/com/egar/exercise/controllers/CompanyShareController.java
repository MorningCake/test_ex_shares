package com.egar.exercise.controllers;

import com.egar.exercise.dto.pojo.GraphDataDTO;
import com.egar.exercise.dto.pojo.TableStringCreateDTO;
import com.egar.exercise.dto.pojo.TableStringReadUpdateDTO;
import com.egar.exercise.exceptions.IncorrectTableStringDataException;
import com.egar.exercise.services.CompanyShareService;
import com.egar.exercise.utils.AppUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shares")
@Api("/shares")
public class CompanyShareController {

    private CompanyShareService companyShareService;

    @Autowired
    public void setCompanyShareService(CompanyShareService companyShareService) {
        this.companyShareService = companyShareService;
    }

    /**
     * Получить таблицу цен из БД
     */
    @GetMapping("/all")
    @ApiOperation("Получить таблицу цен из БД")
    public List<TableStringReadUpdateDTO> initializeTable () {
        return companyShareService.initializeTableByAllData();
    }

    /**
     * Создание новой записи в таблице
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Создание новой записи в таблице")
    public void createTableString (
            @ApiParam("Новая запись в таблице") @Validated @RequestBody
                    TableStringCreateDTO dto,
            BindingResult bindingResult) {
        try {
            AppUtils.receiveValidationResult(bindingResult);
        } catch (Exception e) {
            throw new IncorrectTableStringDataException(e.getMessage());
        }
        companyShareService.createNewStringIntoTable(dto);
    }

    /**
     * Изменить строку в таблице
     */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("Изменить строку в таблице")
    public void updateTableString (
            @ApiParam("Строка с изменениями") @Validated @RequestBody
                    TableStringReadUpdateDTO dto,
            BindingResult bindingResult) {
        try {
            AppUtils.receiveValidationResult(bindingResult);
        } catch (Exception e) {
            throw new IncorrectTableStringDataException(e.getMessage());
        }
        companyShareService.updateStringIntoTable(dto);
    }

    /**
     * Получить точки для графика
     */
    @ApiOperation("Получить точки для графика")
    @GetMapping("/points")
    public List<GraphDataDTO> getGraphPoints () {
        return companyShareService.getGraphPoints();
    }


}
