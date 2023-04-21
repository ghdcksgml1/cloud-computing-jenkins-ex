package com.chanhee.hackingspringbootch2reactive

import com.chanhee.hackingspringbootch2reactive.config.CoroutineBlockHoundIntegration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound
import reactor.blockhound.integration.BlockHoundIntegration
import java.util.zip.InflaterInputStream


@SpringBootApplication
class HackingSpringBootCh2ReactiveApplication

fun main(args: Array<String>) {
	BlockHound.builder().with(CoroutineBlockHoundIntegration()).install()
	runApplication<HackingSpringBootCh2ReactiveApplication>(*args)
}