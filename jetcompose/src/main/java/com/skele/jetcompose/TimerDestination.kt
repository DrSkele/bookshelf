package com.skele.jetcompose

interface NavDestination {
    val route : String
}

object TimerPage : NavDestination {
    override val route: String
        get() = "timer-page"
}
object SettingPage : NavDestination {
    override val route: String
        get() = "setting-page"
}