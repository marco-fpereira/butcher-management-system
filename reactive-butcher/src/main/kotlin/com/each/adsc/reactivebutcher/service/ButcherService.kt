package com.each.adsc.reactivebutcher.service


import com.each.adsc.reactivebutcher.exception.ValueNotFoundException
import com.each.adsc.reactivebutcher.model.Meat
import com.each.adsc.reactivebutcher.model.dto.MeatDTO
import com.each.adsc.reactivebutcher.model.dto.error.ErrorResponseDTO
import com.each.adsc.reactivebutcher.repository.ButcherRepository
import com.each.adsc.reactivebutcher.utils.ObjectParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class ButcherService {

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    fun getByMeatName(meatName: String) : Mono<MeatDTO> {
        val meatByName : Mono<Meat> = butcherRepository.findById(meatName)
            .switchIfEmpty{
                Mono.error(ValueNotFoundException(errorResponseDTO = ErrorResponseDTO(message = "There is not any meat with the given name")))
            }
        return meatByName.flatMap { meat ->
            Mono.just(ObjectParser.meatToMeatDTO(meat))
        }
    }

    fun getAllMeat(): Flux<MeatDTO> {
        return butcherRepository.findAll().map { meat ->
            ObjectParser.meatToMeatDTO(meat)
        }
    }
}