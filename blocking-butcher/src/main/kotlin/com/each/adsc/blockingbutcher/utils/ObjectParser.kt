package com.each.adsc.blockingbutcher.utils

import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.Purchase
import com.each.adsc.blockingbutcher.model.Sale
import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.model.dto.PurchaseDTO
import com.each.adsc.blockingbutcher.model.dto.SaleDTO

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
                purchaseId = purchaseDTO.purchaseId,
                meatName = purchaseDTO.meatName,
                animalOfOrigin = purchaseDTO.animalOfOrigin,
                purchasePriceInKilograms = purchaseDTO.purchasePriceInKilograms,
                amountBought = purchaseDTO.amountBought,
                purchaseTimestamp = purchaseDTO.purchaseTimestamp.toString()
            )

        fun purchaseDTOToMeat(purchaseDTO: PurchaseDTO) =
            Meat(
                meatName = purchaseDTO.meatName,
                animalOfOrigin = purchaseDTO.animalOfOrigin,
                price = purchaseDTO.purchasePriceInKilograms*1.35,
                availableAmountInKilograms = purchaseDTO.amountBought
            )

    }
}