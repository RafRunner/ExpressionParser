package dominio

import java.math.BigDecimal

class MultiplicacaoDivisaoParser : Parser {

    override val regex: Regex
        get() = Regex("""\(?(\d+)\)?([*\/])\(?([+-]?\d+)\)?""")

    override fun parse(expressao: String): String {
        val matchResult = regex.find(expressao) ?: return ""

        val groups = matchResult.groups
        val resultadoParcial: String

        val primeiroNumero = BigDecimal(groups[1]?.value)
        val segundoNumero = BigDecimal(groups[3]?.value)

        resultadoParcial = if (groups[2]?.value == "*") {
            (primeiroNumero * segundoNumero).toString()
        } else {
            (primeiroNumero / segundoNumero).toString()
        }

        return expressao.replaceFirst(matchResult.value, resultadoParcial)
    }
}