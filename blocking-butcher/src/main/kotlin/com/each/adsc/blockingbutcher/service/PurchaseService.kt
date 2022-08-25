package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.dto.PurchaseDTO
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import com.each.adsc.blockingbutcher.repository.PurchaseRepository
import com.each.adsc.blockingbutcher.utils.ObjectParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PurchaseService {

    @Autowired
    private lateinit var purchaseRepository: PurchaseRepository

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    fun purchaseMeat(purchaseDTO: PurchaseDTO) {
        val optionalMeat: Optional<Meat> = butcherRepository.findByMeatName(purchaseDTO.meatName)
        if(optionalMeat.isPresent) {
            val meat = optionalMeat.get()
            meat.availableAmountInKilograms = meat.availableAmountInKilograms + purchaseDTO.amountBought
            meat.price = purchaseDTO.purchasePriceInKilograms

            butcherRepository.save(meat)
        } else {
            val meat = ObjectParser.purchaseDTOToMeat(purchaseDTO)
            butcherRepository.save(meat)
        }

        purchaseRepository.save(ObjectParser.purchaseDTOToPurchase(purchaseDTO))
    }
}