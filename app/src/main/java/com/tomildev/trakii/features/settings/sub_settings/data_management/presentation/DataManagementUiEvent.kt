package com.tomildev.trakii.features.settings.sub_settings.data_management.presentation

sealed class DataManagementUiEvent {
    data object DownloadData : DataManagementUiEvent()
    data object SyncData : DataManagementUiEvent()
    data object ClearHabits : DataManagementUiEvent()
    data object DeleteAccount : DataManagementUiEvent()
}
