package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.exception.ValueNotFoundException
import com.each.adsc.blockingbutcher.model.Meat
import com.each.adsc.blockingbutcher.model.ModelDataGenerator
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class ButcherServiceTest{

    @InjectMockKs
    private var butcherService: ButcherService = ButcherService()

    @RelaxedMockK
    private lateinit var butcherRepository : ButcherRepository

    private val meat = ModelDataGenerator.generateMeat()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get by meat name when it is found`() {
        val meat = ModelDataGenerator.generateMeat()
        val optionalMeat = Optional.of(meat)

        every { butcherRepository.findById(meat.meatName) } returns (optionalMeat)

        val result = butcherService.getByMeatName(meat.meatName)
        assertEquals(result.statusCodeValue, 200)
        assertEquals(result.body!!.name, meat.meatName)
    }

    @Test
    fun `get by meat name when it is not found`() {
        val optionalMeat = Optional.empty<Meat>()

        every { butcherRepository.findById(meat.meatName) } returns (optionalMeat)
        val assertThrows = assertThrows<ValueNotFoundException> { butcherService.getByMeatName(meat.meatName) }
        assertEquals("There is not any meat named ${meat.meatName}", assertThrows.errorResponseDTO.message)
    }

    @Test
    fun `get all meat`() {
        val meatList = listOf(meat, meat)

        every { butcherRepository.findAll() } returns (meatList)
        val allMeat = butcherService.getAllMeat()
        assertEquals( 2, allMeat.size)
    }
}