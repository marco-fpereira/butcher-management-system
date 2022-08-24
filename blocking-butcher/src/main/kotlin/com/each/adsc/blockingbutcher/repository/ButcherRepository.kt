package com.each.adsc.blockingbutcher.repository

import com.each.adsc.blockingbutcher.model.Meat
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
@EnableScan
interface ButcherRepository : CrudRepository<Meat, String> {

    fun getByName(meatName: String) : Optional<Meat>
}