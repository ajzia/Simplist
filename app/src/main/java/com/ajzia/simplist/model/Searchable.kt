package com.ajzia.simplist.model

interface Searchable {
  fun doesMatchQuery(query: String): Boolean
}