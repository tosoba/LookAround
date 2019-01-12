package com.example.there.lookaround.util.ext

import androidx.drawerlayout.widget.DrawerLayout

fun DrawerLayout.toggle(gravity: Int) = if (isDrawerOpen(gravity)) closeDrawer(gravity)
else openDrawer(gravity)
