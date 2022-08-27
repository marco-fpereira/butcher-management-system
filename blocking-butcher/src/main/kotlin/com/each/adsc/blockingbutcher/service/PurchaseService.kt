package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.model.dto.PurchaseDTO
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import com.each.adsc.blockingbutcher.repository.PurchaseRepository
import com.each.adsc.blockingbutcher.utils.ObjectParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class PurchaseService {

    @Autowired
    private lateinit var purchaseRepository: PurchaseRepository

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    fun purchaseMeat(purchaseDTO: PurchaseDTO) : ResponseEntity<Any> {
        val optionalMeat: Optional<Meat> = butcherRepository.findById(purchaseDTO.meatName)
        return if(optionalMeat.isPresent) {
            val meat = optionalMeat.get()
            meat.availableAmountInKilograms = meat.availableAmountInKilograms + purchaseDTO.amountBought
            meat.price = purchaseDTO.purchasePriceInKilograms

            butcherRepository.save(meat)
            purchaseRepository.save(ObjectParser.purchaseDTOToPurchase(purchaseDTO))
            ResponseEntity(ObjectParser.meatToMeatDTO(meat), HttpStatus.CREATED)
        } else {
            val meat = ObjectParser.purchaseDTOToMeat(purchaseDTO)
            butcherRepository.save(meat)
            purchaseRepository.save(ObjectParser.purchaseDTOToPurchase(purchaseDTO))
            ResponseEntity(ObjectParser.meatToMeatDTO(meat), HttpStatus.CREATED)
        }
    }
}