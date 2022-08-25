package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import com.each.adsc.blockingbutcher.utils.ObjectParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ButcherService {

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    fun getByMeatName(meatName: String) : ResponseEntity<MeatDTO> {
        val meatByName = butcherRepository.findByMeatName(meatName)
        return if (meatByName.isPresent) {
            val meatDTO = ObjectParser.meatToMeatDTO(meatByName.get())
            ResponseEntity.ok(meatDTO)
        }
        else ResponseEntity.notFound().build()
    }

    fun getAllMeat(): List<MeatDTO> {
        val list = mutableListOf<MeatDTO>()
        butcherRepository.findAll().forEach {
            list.add(ObjectParser.meatToMeatDTO(it))
        }
        return list
    }
}