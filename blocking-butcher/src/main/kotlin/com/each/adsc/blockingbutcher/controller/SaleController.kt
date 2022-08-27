package com.each.adsc.blockingbutcher.controller

import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.model.dto.PurchaseDTO
import com.each.adsc.blockingbutcher.model.dto.SaleDTO
import com.each.adsc.blockingbutcher.service.SaleService
import com.each.adsc.blockingbutcher.utils.ErrorListAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/butcher/blocking")
class SaleController {

    @Autowired
    private lateinit var saleService: SaleService

    @PutMapping("/sale_meat")
    fun insertNewMeat(@RequestBody saleDTO: SaleDTO, result: BindingResult) : ResponseEntity<Any> {
        println("a")
        if (result.hasErrors()) return ErrorListAssembler.generateErrorList(result)
        return saleService.saleMeat(saleDTO)
    }
}