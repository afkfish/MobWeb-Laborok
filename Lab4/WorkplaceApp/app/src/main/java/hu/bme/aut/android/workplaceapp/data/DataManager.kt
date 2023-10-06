package hu.bme.aut.android.workplaceapp.data

object DataManager {
    val person = Person(
        "Test User", "testuser@domain.com",
        "1234 Test, Random Street 1.",
        "123456AB",
        "123456789",
        "1234567890",
        "0123456789"
    )
    const val HOLIDAY_MAX_VALUE = 20
    const val HOLIDAY_DEFAULT_VALUE = 15

    var holidays = HOLIDAY_DEFAULT_VALUE
    val remainingHolidays get() = HOLIDAY_MAX_VALUE - holidays
}