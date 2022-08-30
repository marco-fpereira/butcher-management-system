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
            var monoMeat = butcherRepository.findById(purchaseDTO.meatName)
            monoMeat = monoMeat.switchIfEmpty {
                val meat = ObjectParser.purchaseDTOToMeat(purchaseDTO)
                butcherRepository.save(meat)
                purchaseRepository.save(ObjectParser.purchaseDTOToPurchase(purchaseDTO))
                Mono.just(meat)
            }

            monoMeat.flatMap { meat ->
                meat.availableAmountInKilograms = meat.availableAmountInKilograms + purchaseDTO.amountBought
                meat.price = purchaseDTO.purchasePriceInKilograms

                butcherRepository.save(meat)
                purchaseRepository.save(ObjectParser.purchaseDTOToPurchase(purchaseDTO))
                Mono.just(meat)
            }
            monoMeat.flatMap { Mono.just(ObjectParser.meatToMeatDTO(it)) }
        }

    }
}