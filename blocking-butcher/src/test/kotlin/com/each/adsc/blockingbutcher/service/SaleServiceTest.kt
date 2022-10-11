package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.model.ModelDataGenerator
import com.each.adsc.blockingbutcher.model.dto.SaleDTO
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import com.each.adsc.blockingbutcher.repository.SaleRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class SaleServiceTest {

    @InjectMockKs
    private var saleService: SaleService = SaleService()

    @RelaxedMockK
    private lateinit var saleRepository: SaleRepository

    @RelaxedMockK
    private lateinit var butcherRepository: ButcherRepository

    private val saleDTO = ModelDataGenerator.generateSaleDTO()

    private val meat = ModelDataGenerator.generateMeat()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `sale meat when it is found and available amount is greater than customer wants to buy`() {
        meat.availableAmountInKilograms = 20.0
        every { butcherRepository.findById(saleDTO.meatName) } returns (Optional.of(meat))
        every { butcherRepository.save(any()) } returns ModelDataGenerator.generateMeat()
        every { saleRepository.save(any()) } returns ModelDataGenerator.generateSale()

        val result = saleService.saleMeat(saleDTO)
        assertEquals(result.statusCodeValue, 201)
    }

    @Test
    fun `sale meat when it is found and available amount is lesser than customer wants to buy`() {
        meat.availableAmountInKilograms = 5.0  // saleDTO has 10.0 of amount
        every { butcherRepository.findById(saleDTO.meatName) } returns (Optional.of(meat))

        val result = saleService.saleMeat(saleDTO)
        assertEquals(result.statusCodeValue, 412)
        assertEquals(
        "The amount of meat you requested is not currently available. " +
                "Current stock is ${meat.availableAmountInKilograms}.",
            result.body
        )
    }

    @Test
    fun `sale meat when it is not found`() {
        every { butcherRepository.findById(saleDTO.meatName) } returns (Optional.empty())

        val result = saleService.saleMeat(saleDTO)
        assertEquals(result.statusCodeValue, 412)
    }
}