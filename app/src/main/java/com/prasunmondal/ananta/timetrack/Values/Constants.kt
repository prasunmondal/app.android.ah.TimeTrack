package com.prasunmondal.ananta.timetrack.Values

class Constants {

    val googleScript_scriptURL = "https://docs.google.com/spreadsheets/d/1gZA5tqllOArlLJb2nLcmLqfNR-cdgFzNqTl9ZKyzcOI/edit#gid=0"
    val sheet_output_URL =     "https://docs.google.com/spreadsheets/d/1gZA5tqllOArlLJb2nLcmLqfNR-cdgFzNqTl9ZKyzcOI/edit#gid=0" // project_Ananta
    val sheet_output_name = "Ananta";
    val sheet_devlogs_URL = "https://docs.google.com/spreadsheets/d/1nMItEbUTq8do0XZDOWr5gsTugwSxqoWPfCw6yqbroNw/edit#gid=0" // Project_Ananta_devlogs
    val sheet_devlogs_tabName = "run_logs"

    object Singleton {
        var instance = Constants()
    }
}