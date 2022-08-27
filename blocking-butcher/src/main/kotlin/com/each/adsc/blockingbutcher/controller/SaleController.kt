package com.each.adsc.blockingbutcher.controller

import com.each.adsc.blockingbutcher.model.dto.SaleDTO
import com.each.adsc.blockingbutcher.service.SaleService
import com.each.adsc.blockingbutcher.utils.ErrorListAssembler
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/butcher/blocking")
class SaleController {

    @Autowired
    private lateinit var saleService: SaleService

    @PutMapping("/sale_meat")
    fun insertNewMeat(@RequestBody @Valid saleDTO: SaleDTO, result: BindingResult) : ResponseEntity<Any> {
        if (result.hasErrors()) return ErrorListAssembler.generateErrorList(result)
        return saleService.saleMeat(saleDTO)
    }
}