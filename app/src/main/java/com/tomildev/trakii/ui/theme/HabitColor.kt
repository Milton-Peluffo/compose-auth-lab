package com.tomildev.trakii.ui.theme
import androidx.compose.ui.graphics.Color

enum class HabitColor(val nameId: String) {
    SAGE("sage"),
    ROSE("rose"),
    MUTED_BLUE("muted_blue"),
    LAVENDER("lavender"),
    SAND("sand"),
    SLATE("slate");

    // Function for mapping the Enum to the real colors of app theme
    fun getColor(extendedColors: ExtendedColors): Color {
        return when (this) {
            SAGE -> extendedColors.habitSage
            ROSE -> extendedColors.habitRose
            MUTED_BLUE -> extendedColors.habitMutedBlue
            LAVENDER -> extendedColors.habitLavender
            SAND -> extendedColors.habitSand
            SLATE -> extendedColors.habitSlate
        }
    }

    companion object {
        fun fromId(id: String?): HabitColor =
            values().find { it.nameId == id } ?: SLATE
    }
}