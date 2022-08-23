package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ButcherService {

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    fun getByMeatName(meatName: String) : ResponseEntity<Meat> {
        val meatByName = butcherRepository.getMeatByName(meatName)
        return if (meatByName.isPresent) ResponseEntity.ok(meatByName.get())
        else ResponseEntity.notFound().build()
    }
}