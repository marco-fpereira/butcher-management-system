package com.each.adsc.blockingbutcher.controller

import com.each.adsc.blockingbutcher.model.ModelDataGenerator
import com.each.adsc.blockingbutcher.service.PurchaseService
import com.each.adsc.blockingbutcher.utils.ObjectParser
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult

class PurchaseControllerTest {

    @InjectMockKs
    private var purchaseController: PurchaseController = PurchaseController()

    @MockK
    private lateinit var purchaseService: PurchaseService

    @MockK
    private lateinit var result: BindingResult

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `insert new meat when input json is correct`() {
        val purchaseDTO = ModelDataGenerator.generatePurchaseDTO()
        val meat = ObjectParser.purchaseDTOToMeat(purchaseDTO)
        val meatDTO = ObjectParser.meatToMeatDTO(meat)

        every {
            purchaseService.purchaseMeat(purchaseDTO)
        } returns ResponseEntity(meatDTO, HttpStatus.CREATED)
        every { result.hasErrors() } returns (false)

        val result = purchaseController.insertNewMeat(purchaseDTO, result)
        assertEquals(result.statusCodeValue, 201)
        assertEquals(result.body, meatDTO)
    }

    @Test
    fun `insert new meat when input json is not correct`() {
        val purchaseDTO = ModelDataGenerator.generateInvalidPurchaseDTO()
        val fieldErrorList = listOf(
            ModelDataGenerator.generatePurchaseFieldError("meatName"),
            ModelDataGenerator.generatePurchaseFieldError("animalOfOrigin"),
            ModelDataGenerator.generatePurchaseFieldError("purchasePriceInKilograms"),
            ModelDataGenerator.generatePurchaseFieldError("amountBought")
        )

        every { result.hasErrors() } returns (true)
        every { result.fieldErrors } returns (fieldErrorList)

        val result = purchaseController.insertNewMeat(purchaseDTO, result)
        assertEquals(result.statusCodeValue, 400)

    }
}