package com.david.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform