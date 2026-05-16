package com.tomildev.trakii.features.settings.sub_settings.data_management.presentation

data class DataManagementUiState(
    val isLoading: Boolean = false,
    val syncStatus: String = "Sincronizado" // Default value for example
)
