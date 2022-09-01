package com.each.adsc.reactivebutcher.controller

import com.each.adsc.reactivebutcher.model.dto.MeatDTO
import com.each.adsc.reactivebutcher.model.dto.PurchaseDTO
import com.each.adsc.reactivebutcher.service.PurchaseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/butcher/reactive")
class PurchaseController {

    @Autowired
    private lateinit var purchaseService: PurchaseService

    @PostMapping("/purchase_meat")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertNewMeat(
        @RequestBody @Valid purchaseDTO: Mono<PurchaseDTO>,
    ) : Mono<MeatDTO> {
        return purchaseService.purchaseMeat(purchaseDTO)
    }
}