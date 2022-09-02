package com.each.adsc.reactivebutcher.utils

import com.each.adsc.reactivebutcher.model.Meat
import com.each.adsc.reactivebutcher.model.Purchase
import com.each.adsc.reactivebutcher.model.dto.MeatDTO
import com.each.adsc.reactivebutcher.model.dto.PurchaseDTO
import java.util.*

class ObjectParser {
    companion object Parser {
        fun meatToMeatDTO(meat: Meat)
            = MeatDTO(
                name = meat.meatName,
                animalOfOrigin = meat.animalOfOrigin,
                price = meat.price,
                availableAmountInKilograms = meat.availableAmountInKilograms
            )

        fun purchaseDTOToPurchase(purchaseDTO: PurchaseDTO) =
            Purchase(
                purchaseId = purchaseDTO.purchaseId ?: UUID.randomUUID().toString(),
                meatName = purchaseDTO.meatName,
                animalOfOrigin = purchaseDTO.animalOfOrigin,
                purchasePriceInKilograms = purchaseDTO.purchasePriceInKilograms,
                amountBought = purchaseDTO.amountBought,
                purchaseTimestamp = purchaseDTO.purchaseTimestamp.toString()
            )

        fun purchaseDTOToMeat(
            purchaseDTO: PurchaseDTO,
            defaultMeat : Boolean = false
        ) :Meat {
            val availableAmount = if(defaultMeat) 0.0 else purchaseDTO.amountBought
                    return Meat(
                meatName = purchaseDTO.meatName,
                animalOfOrigin = purchaseDTO.animalOfOrigin,
                price = purchaseDTO.purchasePriceInKilograms*1.35,
                availableAmountInKilograms = availableAmount
            )
        }

    }
}