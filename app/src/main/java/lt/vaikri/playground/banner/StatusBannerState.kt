package lt.vaikri.playground.banner


data class StatusBannerState(val messages: List<StatusMessage>) {
    val hasMessages: Boolean get() = !isEmpty
    val isEmpty: Boolean get() = messages.isEmpty()

    fun renderMessage(): String {
        return if (messages.isEmpty()) {
            ""
        } else {
            val newest = messages.first().message
            when (messages.size) {
                1 -> newest
                2 -> {
                    val second = messages[1].message
                    "$newest + $second"
                }
                else -> {
                    "$newest + ${messages.size - 1} other errors"
                }
            }
        }
    }

    private val StatusMessage.message: String
        get() = when (this) {
            StatusMessage.NO_INTERNET -> "No internet connection"
            StatusMessage.NO_GPS -> "No gps"
            StatusMessage.OTHER -> "Other error"
        }


    operator fun plus(type: StatusMessage): StatusBannerState =
        StatusBannerState(
            ArrayList<StatusMessage>().apply {
                add(type)
                messages.forEach { if (it != type) add(it) }
            }
        )

    operator fun minus(type: StatusMessage): StatusBannerState =
        StatusBannerState(
            ArrayList<StatusMessage>().apply {
                messages.forEach { if (it != type) add(it) }
            }
        )
}
