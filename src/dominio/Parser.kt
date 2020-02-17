package dominio

interface Parser {

    val regex: Regex

    fun parse(expressao: String): String
}