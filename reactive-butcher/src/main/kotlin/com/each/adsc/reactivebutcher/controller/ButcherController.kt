package com.each.adsc.reactivebutcher.controller

import com.each.adsc.reactivebutcher.model.dto.MeatDTO
import com.each.adsc.reactivebutcher.service.ButcherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/butcher/reactive")
class ButcherController {

    @Autowired
    private lateinit var butcherService : ButcherService

    @GetMapping("/current_meat_info")
    fun getMeatList() : Flux<MeatDTO> {
        return butcherService.getAllMeat()
    }

    @GetMapping("/current_meat_info/{meat_name}")
    fun getCurrentMeatInfo(@PathVariable("meat_name") meatName: String) : Mono<MeatDTO> {
        return butcherService.getByMeatName(meatName)
    }
}