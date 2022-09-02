package com.each.adsc.reactivebutcher.service

import com.each.adsc.reactivebutcher.model.dto.MeatDTO
import com.each.adsc.reactivebutcher.model.dto.PurchaseDTO
import com.each.adsc.reactivebutcher.repository.ButcherRepository
import com.each.adsc.reactivebutcher.repository.PurchaseRepository
import com.each.adsc.reactivebutcher.utils.ObjectParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class PurchaseService {

    @Autowired
    private lateinit var purchaseRepository: PurchaseRepository

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    fun purchaseMeat(monoPurchaseDTO: Mono<PurchaseDTO>): Mono<MeatDTO> {
        return monoPurchaseDTO.flatMap { purchaseDTO ->
            val monoMeat = butcherRepository.findByIdWithDefaultValue(
                meatId = purchaseDTO.meatName,
                defaultIfEmpty = ObjectParser.purchaseDTOToMeat(purchaseDTO, defaultMeat = true)
            ).log()

            monoMeat.flatMap { meat ->
                meat.availableAmountInKilograms = meat.availableAmountInKilograms + purchaseDTO.amountBought
                meat.price = purchaseDTO.purchasePriceInKilograms*1.35

                butcherRepository.update(meat)
                Mono.just(meat)
            }.flatMap { meat ->
                purchaseRepository.save(ObjectParser.purchaseDTOToPurchase(purchaseDTO))
                Mono.just(ObjectParser.meatToMeatDTO(meat))
            }
        }
    }
}