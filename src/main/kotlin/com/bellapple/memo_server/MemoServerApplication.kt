package com.bellapple.memo_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemoServerApplication

fun main(args: Array<String>) {
	runApplication<MemoServerApplication>(*args)
}
