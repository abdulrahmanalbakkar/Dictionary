package com.example.dictionary

data class Word(
    var id: Int?,
    var word: String?,
    var type: String?,
    var meaning: String?,
    var example_sentence: String?,
    var history: String?,
    var favorites: String?,
    var expandable: Boolean = false
)

