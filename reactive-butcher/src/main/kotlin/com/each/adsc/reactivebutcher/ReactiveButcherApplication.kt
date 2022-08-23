package com.each.adsc.reactivebutcher

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveButcherApplication

fun main(args: Array<String>) {
	runApplication<ReactiveButcherApplication>(*args)
}
