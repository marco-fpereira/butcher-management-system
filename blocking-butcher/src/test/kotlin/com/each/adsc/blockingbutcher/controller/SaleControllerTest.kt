package com.each.adsc.blockingbutcher.controller

import com.each.adsc.blockingbutcher.model.ModelDataGenerator
import com.each.adsc.blockingbutcher.service.SaleService
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

class SaleControllerTest{

    @InjectMockKs
    private var saleController: SaleController = SaleController()

    @MockK
    private lateinit var saleService: SaleService

    @MockK
    private lateinit var result: BindingResult

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `insert new meat when input json is correct`() {
        val saleDTO = ModelDataGenerator.generateSaleDTO()

        every { saleService.saleMeat(saleDTO) } returns ResponseEntity(saleDTO, HttpStatus.CREATED)
        every { result.hasErrors() } returns (false)

        val result = saleController.insertNewMeat(saleDTO, result)
        assertEquals(result.statusCodeValue, 201)
    }

    @Test
    fun `insert new meat when input json is not correct`() {
        val saleDTO = ModelDataGenerator.generateInvalidSaleDTO()
        val fieldErrorList = listOf(
            ModelDataGenerator.generateSaleFieldError("meatName"),
            ModelDataGenerator.generateSaleFieldError("amount"),
            ModelDataGenerator.generateSaleFieldError("totalPrice"),
            ModelDataGenerator.generateSaleFieldError("typeOfCut")
        )
        every { result.hasErrors() } returns (true)
        every { result.fieldErrors } returns (fieldErrorList)

        val result = saleController.insertNewMeat(saleDTO, result)
        assertEquals(result.statusCodeValue, 400)

    }
}