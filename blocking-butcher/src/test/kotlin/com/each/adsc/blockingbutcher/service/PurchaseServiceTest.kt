package com.each.adsc.blockingbutcher.service

import com.each.adsc.blockingbutcher.model.ModelDataGenerator
import com.each.adsc.blockingbutcher.model.dto.MeatDTO
import com.each.adsc.blockingbutcher.repository.ButcherRepository
import com.each.adsc.blockingbutcher.repository.PurchaseRepository
import com.each.adsc.blockingbutcher.utils.ObjectParser
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class PurchaseServiceTest {

    @InjectMockKs
    private var purchaseService: PurchaseService = PurchaseService()

    @RelaxedMockK
    private lateinit var purchaseRepository: PurchaseRepository

    @RelaxedMockK
    private lateinit var butcherRepository: ButcherRepository

    private val purchaseDTO = ModelDataGenerator.generatePurchaseDTO()

    private val meat = ModelDataGenerator.generateMeat()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `insert new meat when it does not exist already`() {
        every { butcherRepository.findById(purchaseDTO.meatName) } returns (Optional.empty())
        every { butcherRepository.save(any()) } returns meat
        every { purchaseRepository.save(any()) } returns ModelDataGenerator.generatePurchase()

        val result = purchaseService.purchaseMeat(purchaseDTO)
        assertEquals(result.statusCodeValue, 201)
        val meatDTOResult = result.body as MeatDTO
        assertEquals(purchaseDTO.amountBought, meatDTOResult.availableAmountInKilograms)
    }

    @Test
    fun `insert new meat when it already exists`() {
        every { butcherRepository.findById(purchaseDTO.meatName) } returns (Optional.of(meat))
        every { butcherRepository.save(any()) } returns meat
        every { purchaseRepository.save(any()) } returns ModelDataGenerator.generatePurchase()

        val result = purchaseService.purchaseMeat(purchaseDTO)
        assertEquals(result.statusCodeValue, 201)
        val meatDTOResult = result.body as MeatDTO
        assertEquals(
            meat.availableAmountInKilograms,
            meatDTOResult.availableAmountInKilograms
        )
    }
}