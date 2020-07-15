package dominio

import exceptions.ErroDeSintaxeException
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class ExpressionParser {

    private val potenciacaoParser = PotenciacaoParser()
    private val multiplicacaoDivisaoParser = MultiplicacaoDivisaoParser()
    private val somaSubtracaoParser = SomaSubtracaoParser()

    private val regexExpressaoAninhada = Regex("""\((([^\(\)]*)((\([+-]?\d+(\.\d+)?\))[^\(^)]*)*)\)""")
    private val regexParentesesRedundantes = Regex("""\(([+-])?\(([+-]?\d+(\.\d+)?)\){2}""")
    private val regexNumeroEmParenteses = Regex("""([+-])?\(([+-]?\d+(\.\d+)?)\)""")

    private fun tentaExtrairNumeroPuro(expressao: String): Double? {
        return try {
            expressao.toDouble()
        } catch (e: NumberFormatException) {
            val matchNumeroEmParenteses: MatchResult? = regexNumeroEmParenteses.matchEntire(expressao)

            if (matchNumeroEmParenteses != null) {
                val sinal = matchNumeroEmParenteses.groups[1]
                val numero = matchNumeroEmParenteses.groups[2]

                if (numero != null) {
                    return if (sinal?.value == "-") {
                        -numero.value.toDouble()
                    } else {
                        numero.value.toDouble()
                    }
                }
            }
            null
        }
    }

    private fun getExpressaoAninhada(expressao: String): String {
        val matchResult: MatchResult = regexExpressaoAninhada.find(expressao) ?: return ""
        val conteudoParenteses: String = matchResult.groups[1]?.value ?: ""

        if (tentaExtrairNumeroPuro(conteudoParenteses) != null) {
            return ""
        }

        return conteudoParenteses
    }

    private fun removeParentesesRedundantes(expressao: String): String {
        val matchParenteses: MatchResult? = regexParentesesRedundantes.find(expressao)

        if (matchParenteses != null) {
            val sinalExterno: String = matchParenteses.groups[1]?.value ?: "+"
            val numeroInterno: String = matchParenteses.groups[2]?.value ?: ""

            val conteudoSimplificadoParenteses = if (sinalExterno == "+" || sinalExterno == "") {
                numeroInterno
            } else {
                (-numeroInterno.toDouble()).toString()
            }

            return removeParentesesRedundantes(expressao.replace(matchParenteses.value.substring(1, matchParenteses.value.length - 1), conteudoSimplificadoParenteses))
        }

        return expressao
    }

    fun formatAndParse(expressao: String): String {
        val resultado = parse(expressao.replace(" ", ""))
        return DecimalFormat("0.###############", DecimalFormatSymbols(Locale.ENGLISH)).format(resultado)
    }

    fun parse(expressao: String): Double {
        val expressaoSimplificada: String = removeParentesesRedundantes(expressao)

        val valorExpressao: Double? = tentaExtrairNumeroPuro(expressaoSimplificada)
        if (valorExpressao != null) {
            return valorExpressao
        }

        val expressaoParenteses: String = getExpressaoAninhada(expressaoSimplificada)

        if (expressaoParenteses != "") {
            val resultadoParcialParenteses = parse(expressaoParenteses)
            return parse(expressaoSimplificada.replaceFirst("($expressaoParenteses)", "($resultadoParcialParenteses)"))
        }

        val resultadoParcialPotenciacao: String = potenciacaoParser.parse(expressaoSimplificada)

        if (resultadoParcialPotenciacao != "") {
            return parse(resultadoParcialPotenciacao)
        }

        val resultadoParcialMultiplicacaoDivisao: String = multiplicacaoDivisaoParser.parse(expressaoSimplificada)

        if (resultadoParcialMultiplicacaoDivisao != "") {
            return parse(resultadoParcialMultiplicacaoDivisao)
        }

        val resultadoParcialSomaSubtracao: String = somaSubtracaoParser.parse(expressaoSimplificada)

        if (resultadoParcialSomaSubtracao != "") {
            return parse(resultadoParcialSomaSubtracao)
        }

        throw ErroDeSintaxeException(expressaoSimplificada)
    }
}