package com.iti.skypulse.forecast.ui.components

data class ForecastHourlyData(
    val time: String,
    val iconCode: String,
    val temperature: String
)

data class DailyForecastData(
    val dayName: String,
    val date: String,
    val highTemp: String,
    val lowTemp: String,
    val condition: String,
    val iconCode: String,
    val hourly: List<ForecastHourlyData>
)

val sampleDailyForecast = listOf(
    DailyForecastData(
        dayName = "Monday", date = "Oct 12",
        highTemp = "78°", lowTemp = "65°",
        condition = "Sunny", iconCode = "01d",
        hourly = listOf(
            ForecastHourlyData("10 AM", "01d", "64°"),
            ForecastHourlyData("1 PM", "01d", "72°"),
            ForecastHourlyData("4 PM", "01d", "75°"),
            ForecastHourlyData("7 PM", "01n", "68°"),
            ForecastHourlyData("10 PM", "01n", "62°")
        )
    ),
    DailyForecastData(
        dayName = "Tuesday", date = "Oct 13",
        highTemp = "75°", lowTemp = "62°",
        condition = "Partly Cloudy", iconCode = "02d",
        hourly = listOf(
            ForecastHourlyData("10 AM", "02d", "60°"),
            ForecastHourlyData("1 PM", "02d", "70°"),
            ForecastHourlyData("4 PM", "02d", "73°"),
            ForecastHourlyData("7 PM", "02n", "66°"),
            ForecastHourlyData("10 PM", "02n", "60°")
        )
    ),
    DailyForecastData(
        dayName = "Wednesday", date = "Oct 14",
        highTemp = "68°", lowTemp = "58°",
        condition = "Rainy", iconCode = "10d",
        hourly = listOf(
            ForecastHourlyData("10 AM", "10d", "58°"),
            ForecastHourlyData("1 PM", "10d", "64°"),
            ForecastHourlyData("4 PM", "10d", "66°"),
            ForecastHourlyData("7 PM", "10n", "60°"),
            ForecastHourlyData("10 PM", "10n", "56°")
        )
    ),
    DailyForecastData(
        dayName = "Thursday", date = "Oct 15",
        highTemp = "70°", lowTemp = "60°",
        condition = "Cloudy", iconCode = "04d",
        hourly = listOf(
            ForecastHourlyData("10 AM", "04d", "60°"),
            ForecastHourlyData("1 PM", "04d", "66°"),
            ForecastHourlyData("4 PM", "04d", "68°"),
            ForecastHourlyData("7 PM", "04n", "63°"),
            ForecastHourlyData("10 PM", "04n", "58°")
        )
    ),
    DailyForecastData(
        dayName = "Friday", date = "Oct 16",
        highTemp = "82°", lowTemp = "67°",
        condition = "Sunny", iconCode = "01d",
        hourly = listOf(
            ForecastHourlyData("10 AM", "01d", "67°"),
            ForecastHourlyData("1 PM", "01d", "76°"),
            ForecastHourlyData("4 PM", "01d", "80°"),
            ForecastHourlyData("7 PM", "01n", "72°"),
            ForecastHourlyData("10 PM", "01n", "66°")
        )
    )
)
