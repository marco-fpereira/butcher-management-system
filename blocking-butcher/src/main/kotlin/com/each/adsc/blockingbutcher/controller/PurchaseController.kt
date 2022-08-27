package com.each.adsc.blockingbutcher.controller

import com.each.adsc.blockingbutcher.model.dto.PurchaseDTO
import com.each.adsc.blockingbutcher.service.PurchaseService
import com.each.adsc.blockingbutcher.utils.ErrorListAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/butcher/blocking")
class PurchaseController {

    @Autowired
    private lateinit var purchaseService: PurchaseService

    @PostMapping("/purchase_meat")
    fun insertNewMeat(@RequestBody purchaseDTO: PurchaseDTO, result: BindingResult) : ResponseEntity<Any> {
        if (result.hasErrors()) return ErrorListAssembler.generateErrorList(result)
        return purchaseService.purchaseMeat(purchaseDTO)

    }
}