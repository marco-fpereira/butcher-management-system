package com.each.adsc.blockingbutcher.repository

import com.each.adsc.blockingbutcher.model.Purchase
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
@EnableScan
interface PurchaseRepository : CrudRepository<Purchase, String>