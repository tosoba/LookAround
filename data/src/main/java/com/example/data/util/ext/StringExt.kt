package com.example.data.util.ext

val String.eachWordCapitalized: String
    get() = toLowerCase()
        .split(' ')
        .joinToString(" ") { it.capitalize() }