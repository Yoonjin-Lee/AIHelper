package com.example.aihelper

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform