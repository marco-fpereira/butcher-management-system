package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.exception.ValueNotFoundException
import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.model.dto.error.ErrorResponseDTO
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import com.each.adsc.blockingbutcher.utils.ObjectParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class ButcherService {

    @Autowired
    private lateinit var butcherRepository: ButcherRepository

    fun getByMeatName(meatName: String) : ResponseEntity<MeatDTO> {
        val meatByName: Optional<Meat> = butcherRepository.findById(meatName)
        return if (meatByName.isPresent) {
            val meatDTO = ObjectParser.meatToMeatDTO(meatByName.get())
            ResponseEntity.ok(meatDTO)
        }
        else throw ValueNotFoundException(ex = ErrorResponseDTO(message = "There is not any meat named $meatName"))
    }

    fun getAllMeat(): List<MeatDTO> {
        val list = mutableListOf<MeatDTO>()
        butcherRepository.findAll().forEach {
            list.add(ObjectParser.meatToMeatDTO(it))
        }
        return list
    }
}