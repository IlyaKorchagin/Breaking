package com.korchagin.breaking.data.helper

interface Mapper<I, O> {

    fun transform(input: I): O
}