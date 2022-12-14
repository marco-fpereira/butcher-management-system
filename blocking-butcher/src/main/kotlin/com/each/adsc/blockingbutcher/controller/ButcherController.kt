package com.each.adsc.blockingbutcher.controller

import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.service.ButcherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/butcher/blocking")
class ButcherController {

    @Autowired
    private lateinit var butcherService : ButcherService

    @GetMapping("/current_meat_info")
    fun getMeatList() : ResponseEntity<List<MeatDTO>>{
        return ResponseEntity(butcherService.getAllMeat(), HttpStatus.OK)
    }

    @GetMapping("/current_meat_info/{meat_name}")
    fun getCurrentMeatInfo(@PathVariable("meat_name") meatName: String) : ResponseEntity<MeatDTO> {
        return butcherService.getByMeatName(meatName)
    }


}