package lt.vaikri.playground.edit

data class EditState(
    val id: Int,
    val initial: String,
    val reset: String,
    val hint: String,
    val limit: Int,
    val type: Type
) {
    sealed interface Type

    object NumberType : Type

    data class TextType(val allCaps: Boolean, val regex: Regex?, val suggestions: Boolean) : Type
}
