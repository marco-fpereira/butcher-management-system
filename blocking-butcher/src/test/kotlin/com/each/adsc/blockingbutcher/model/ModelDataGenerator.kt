package com.each.adsc.blockingbutcher.model

import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.model.dto.PurchaseDTO
import com.each.adsc.blockingbutcher.model.dto.SaleDTO
import org.springframework.validation.FieldError
import java.time.LocalDateTime
import java.util.*

class ModelDataGenerator {
    companion object {
        fun generateMeat() = Meat(
            meatName = "meat",
            animalOfOrigin = "ox",
            price = 10.0,
            availableAmountInKilograms = 1.0
        )

        fun generateMeatDTO() = MeatDTO(
            name = "meat",
            animalOfOrigin = "ox",
            price = 10.0,
            availableAmountInKilograms = 1.0
        )

        fun generatePurchase() = Purchase(
            purchaseId = null,
            meatName = "meat",
            animalOfOrigin = "ox",
            purchasePriceInKilograms = 30.0,
            amountBought = 10.0
        )

        fun generatePurchaseDTO() = PurchaseDTO(
            purchaseId = null,
            meatName = "meat",
            animalOfOrigin = "ox",
            purchasePriceInKilograms = 30.0,
            amountBought = 10.0
        )

        fun generateInvalidPurchaseDTO() = PurchaseDTO(
            purchaseId = null,
            meatName = "",
            animalOfOrigin = "",
            purchasePriceInKilograms = -30.0,
            amountBought = -10.0
        )


        fun generateSale() = Sale(
            saleId = "cff845bf-0065-4c74-9023-c0555924659e",
            meatName = "rump steak",
            amount = 10.0,
            totalPrice = 10.0*1.35,
            typeOfCut = "whole",
            saleTimestamp = LocalDateTime.now().toString()
        )

        fun generateSaleDTO() = SaleDTO(
            saleId = "cff845bf-0065-4c74-9023-c0555924659e",
            meatName = "rump steak",
            amount = 10.0,
            totalPrice = 10.0*1.35,
            typeOfCut = "whole",
            saleTimestamp = LocalDateTime.now()
        )

        fun generateInvalidSaleDTO() = SaleDTO(
            saleId = null,
            meatName = "",
            amount = -10.0,
            totalPrice = -10.0*1.35,
            typeOfCut = "",
            saleTimestamp = LocalDateTime.now()
        )

        fun generatePurchaseFieldError(field: String) = FieldError(
            "purchaseDTO",
            field,
            "error"
        )

        fun generateSaleFieldError(field: String) = FieldError(
            "saleDTO",
            field,
            "error"
        )
    }
}