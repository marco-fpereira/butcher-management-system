package com.each.adsc.blockingbutcher.model

import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.model.dto.SaleDTO
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


    }
}