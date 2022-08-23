package com.each.adsc.blockingbutcher

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlockingButcherApplication

fun main(args: Array<String>) {
	runApplication<BlockingButcherApplication>(*args)
}
