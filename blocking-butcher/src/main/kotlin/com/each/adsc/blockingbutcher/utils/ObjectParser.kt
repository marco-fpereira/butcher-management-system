package com.each.adsc.blockingbutcher.utils

import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.Purchase
import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.model.dto.PurchaseDTO

class ObjectParser {
    companion object Parser {
        fun meatToMeatDTO(meat: Meat) : MeatDTO {
            return MeatDTO(
                name = meat.meatName.orEmpty(),
                animalOfOrigin = meat.animalOfOrigin,
                price = meat.price,
                availableAmountInKilograms = meat.availableAmountInKilograms
            )
        }

        fun purchaseDTOToPurchase(purchaseDTO: PurchaseDTO): Purchase {
            return Purchase(
                purchaseId = purchaseDTO.purchaseId,
                meatName = purchaseDTO.meatName,
                animalOfOrigin = purchaseDTO.animalOfOrigin,
                purchasePriceInKilograms = purchaseDTO.purchasePriceInKilograms,
                amountBought = purchaseDTO.amountBought,
                purchaseTimestamp = purchaseDTO.purchaseTimestamp.toString()
            )
        }

        fun purchaseDTOToMeat(purchaseDTO: PurchaseDTO): Meat {
            return Meat(
                meatName = purchaseDTO.meatName,
                animalOfOrigin = purchaseDTO.animalOfOrigin,
                price = purchaseDTO.purchasePriceInKilograms*1.35,
                availableAmountInKilograms = purchaseDTO.amountBought
            )
        }

    }
}