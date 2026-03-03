package com.ajzia.simplist.nav

sealed class NavGraph(val route: String) {
  object Lists: NavGraph("Lists")

  object Archive: NavGraph("Archive")

  object Products: NavGraph("Products")

  object EditList: NavGraph("EditList/{id}") {
    fun createRoute(id: Int) = "EditList/${id}"
  }
}