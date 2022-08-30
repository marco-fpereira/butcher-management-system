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
                Mono.error { ValueNotFoundException(ErrorResponseDTO(message = "There is not any ")) }
            }
            monoMeat.flatMap { meat ->
                if (meat.availableAmountInKilograms < saleDTO.amount) {
                    Mono.error<PreconditionRequiredException> {
                        PreconditionRequiredException(
                            "The amount of meat you requested is not currently available. " +
                                    "Current stock is ${meat.availableAmountInKilograms}."
                        )
                    }
                }
                meat.availableAmountInKilograms -= saleDTO.amount
                butcherRepository.save(meat)

                val monoSale = saleRepository.save(generateSale(saleDTO, meat.price))
                monoSale.flatMap {savedSale ->
                    Mono.just(updateSaleDTO(
                        baseSaleDTO = saleDTO,
                        saleId = savedSale.saleId.orEmpty(),
                        totalPrice = savedSale.totalPrice
                    ))
                }
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
            amount = saleDTO.amount,
            meatName = saleDTO.meatName,
            totalPrice = saleDTO.amount*meatPrice,
            typeOfCut = saleDTO.typeOfCut,
            saleTimestamp = saleDTO.saleTimestamp.toString()
        )
}