package dominio

import kotlin.math.pow

class PotenciacaoParser : Parser {

    override val regex: Regex
        get() = Regex("""(\([+-])?(\d+)\)?\^\(?([+-]?\d+)\)?""")

    override fun parse(expressao: String): String {
        val matchResult = regex.find(expressao) ?: return ""
        val groups = matchResult.groups

        val primeiroNumero: Double = groups[2]?.value?.toDouble() ?: return ""
        val segundoNumero: Double = groups[3]?.value?.toDouble() ?: return ""

        val resultadorParcial: String = if (groups[1]?.value == "(-") {
            primeiroNumero.pow(segundoNumero).toString()
        } else {
            (-1 * primeiroNumero.pow(segundoNumero)).toString()
        }

        return expressao.replaceFirst(matchResult.value, resultadorParcial)
    }
}