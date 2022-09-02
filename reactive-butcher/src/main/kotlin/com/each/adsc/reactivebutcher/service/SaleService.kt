package com.each.adsc.reactivebutcher.service

import com.each.adsc.reactivebutcher.exception.PreconditionRequiredException
import com.each.adsc.reactivebutcher.exception.ValueNotFoundException
import com.each.adsc.reactivebutcher.model.Sale
import com.each.adsc.reactivebutcher.model.dto.SaleDTO
import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO
import com.each.adsc.reactivebutcher.repository.ButcherRepository
import com.each.adsc.reactivebutcher.repository.SaleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.util.*

@Service
class SaleService {

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    @Autowired
    private lateinit var saleRepository: SaleRepository

    fun saleMeat(monoSaleDTO: Mono<SaleDTO>): Mono<SaleDTO> {
        return monoSaleDTO.flatMap { saleDTO ->
            val monoMeat = butcherRepository.findById(saleDTO.meatName)
            monoMeat.switchIfEmpty {
                Mono.error { ValueNotFoundException(errorResponseDTO = ErrorResponseDTO(message = "There is not any meat with the given name")) }
            }
            monoMeat.flatMap { meat ->
                if (meat.availableAmountInKilograms < saleDTO.amount)
                    throw PreconditionRequiredException(
                        message = "The amount of meat you requested is not currently available. " +
                                "Current stock is ${meat.availableAmountInKilograms}."
                    )

                meat.availableAmountInKilograms -= saleDTO.amount
                butcherRepository.save(meat)
                Mono.just(meat)
            }.flatMap { meat ->
                val sale = generateSale(saleDTO, meat.price)
                saleRepository.save(sale)
                Mono.just(sale)
            }.flatMap { sale ->
                Mono.just(updateSaleDTO(
                    baseSaleDTO = saleDTO,
                    saleId = sale.saleId.orEmpty(),
                    totalPrice = sale.totalPrice
                ))
            }
        }
    }

    private fun updateSaleDTO(baseSaleDTO: SaleDTO, saleId: String, totalPrice: Double): SaleDTO {
        baseSaleDTO.saleId = saleId
        baseSaleDTO.totalPrice = totalPrice
        return baseSaleDTO
    }

    private fun generateSale(saleDTO: SaleDTO, meatPrice: Double) : Sale =
        Sale(
            saleId = UUID.randomUUID().toString(),
            amount = saleDTO.amount,
            meatName = saleDTO.meatName,
            totalPrice = saleDTO.amount*meatPrice,
            typeOfCut = saleDTO.typeOfCut,
            saleTimestamp = saleDTO.saleTimestamp.toString()
        )
}