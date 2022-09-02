package com.each.adsc.reactivebutcher.controller

import com.each.adsc.reactivebutcher.model.dto.SaleDTO
import com.each.adsc.reactivebutcher.service.SaleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/butcher/reactive")
class SaleController {

    @Autowired
    private lateinit var saleService: SaleService

    @PutMapping("/sale_meat")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertNewMeat(@RequestBody @Valid saleDTO: Mono<SaleDTO>) : Mono<SaleDTO> {
        return saleService.saleMeat(saleDTO)
    }
}