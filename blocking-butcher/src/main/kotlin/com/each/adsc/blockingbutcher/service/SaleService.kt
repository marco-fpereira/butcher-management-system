package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.Sale
import com.each.adsc.blockingbutcher.model.dto.SaleDTO
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import com.each.adsc.blockingbutcher.repository.SaleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class SaleService {

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    @Autowired
    private lateinit var saleRepository: SaleRepository
    fun saleMeat(saleDTO: SaleDTO): ResponseEntity<Any> {
        val optionalMeat: Optional<Meat> = butcherRepository.findById(saleDTO.meatName)
        if(optionalMeat.isPresent) {
            val meat = optionalMeat.get()
            if (meat.availableAmountInKilograms < saleDTO.amount) {
                return ResponseEntity("The amount of meat you requested is not currently available. " +
                        "Current stock is ${meat.availableAmountInKilograms}.", HttpStatus.PRECONDITION_FAILED)
            }
            meat.availableAmountInKilograms -= saleDTO.amount
            butcherRepository.save(meat)
            var sale = generateSale(saleDTO, meat.price)
            sale = saleRepository.save(sale)
            return ResponseEntity(
                updateSaleDTO(
                    baseSaleDTO = saleDTO,
                    saleId = sale.saleId.orEmpty(),
                    totalPrice = sale.totalPrice
                ),
                HttpStatus.CREATED
            )
        }
        else {
            return ResponseEntity(
                "The meat you requested does not currently exist in the Butcher",
                HttpStatus.PRECONDITION_FAILED
            )
        }

    }

    private fun updateSaleDTO(baseSaleDTO: SaleDTO, saleId: String, totalPrice: Double): SaleDTO {
        baseSaleDTO.saleId = saleId
        baseSaleDTO.totalPrice = totalPrice
        return baseSaleDTO
    }

    private fun generateSale(saleDTO: SaleDTO, meatPrice: Double) : Sale =
        Sale(
            amount = saleDTO.amount,
            meatName = saleDTO.meatName,
            totalPrice = saleDTO.amount*meatPrice,
            typeOfCut = saleDTO.typeOfCut.name,
            saleTimestamp = saleDTO.saleTimestamp.toString()
        )


}