package com.chanhee.hackingspringbootch2reactive.config

import org.springframework.context.annotation.Configuration
import reactor.blockhound.BlockHound
import reactor.blockhound.integration.BlockHoundIntegration

class CoroutineBlockHoundIntegration : BlockHoundIntegration {
    override fun applyTo(builder: BlockHound.Builder) {
        builder.allowBlockingCallsInside("kotlinx.coroutines.reactive.*", "kotlinx.coroutines.FlowKt")
    }
}