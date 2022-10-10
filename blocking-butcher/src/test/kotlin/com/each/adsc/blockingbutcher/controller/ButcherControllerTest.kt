package com.each.adsc.blockingbutcher.controller

import com.each.adsc.blockingbutcher.model.ModelDataGenerator
import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.service.ButcherService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ButcherControllerTest{

    @InjectMockKs
    private var butcherController: ButcherController = ButcherController()

    @MockK
    private lateinit var butcherService: ButcherService

    private val meatDTO: MeatDTO = ModelDataGenerator.generateMeatDTO()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get meat list when it is ok`() {
        val meatList = listOf(meatDTO)
        every { butcherService.getAllMeat() } returns (meatList)

        val result = butcherController.getMeatList()
        assertEquals(result.statusCodeValue, 200)
    }

    @Test
    fun `get current meat info when it is ok`(){
        every { butcherService.getByMeatName(meatDTO.name) } returns (ResponseEntity(meatDTO, HttpStatus.OK))

        val result = butcherController.getCurrentMeatInfo(meatDTO.name)
        assertEquals(result.statusCodeValue, 200)
    }

}