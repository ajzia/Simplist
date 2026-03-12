package com.ajzia.simplist.screens.utils

import java.text.Collator
import java.util.Locale

private val polishCollator: Collator =
  Collator.getInstance(
    Locale.forLanguageTag("pl-PL"))
    .apply { strength = Collator.TERTIARY }

fun <T> List<T>.sortedByLocale(selector: (T) -> String): List<T> {
  return sortedWith { a, b ->
    polishCollator.compare(selector(a), selector(b))
  }
}

fun <T> List<T>.sortedByLocaleDescending(selector: (T) -> String): List<T> {
  return sortedWith { a, b ->
    polishCollator.compare(selector(b), selector(a))
  }
}