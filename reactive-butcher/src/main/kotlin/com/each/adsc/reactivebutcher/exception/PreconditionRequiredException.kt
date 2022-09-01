package com.each.adsc.reactivebutcher.exception

class PreconditionRequiredException(
    override val message: String,
    e: Throwable? = null
) : RuntimeException(message, e)