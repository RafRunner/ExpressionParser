package dominio

import exceptions.ErroDeFormatacaoException
import java.lang.NumberFormatException
import java.math.BigDecimal

class ExpressionParser {

    private val potenciacaoParser: PotenciacaoParser = PotenciacaoParser()
    private val multiplicacaoDivisaoParser: MultiplicacaoDivisaoParser = MultiplicacaoDivisaoParser()
    private val somaSubtracaoParser: SomaSubtracaoParser = SomaSubtracaoParser()

    private val regexExpressaoAninhada = Regex("""\((([^\(\)]*)((\([+-]?\d+(\.\d+)?\))[^\(^)]*)*)\)""")
    private val regexParentesesRedundantes = Regex("""\(([+-])?\(([+-]?\d+(\.\d+)?)\){2}""")
    private val regexNumeroEmParenteses = Regex("""([+-])?\(([+-]?\d+(\.\d+)?)\)""")

    private fun tentaExtrairNumeroPuro(expressao: String): BigDecimal? {
        return try {
            BigDecimal(expressao)
        } catch (e: NumberFormatException) {
            val matchNumeroEmParenteses: MatchResult? = regexNumeroEmParenteses.matchEntire(expressao)

            if (matchNumeroEmParenteses != null) {
                val group1 = matchNumeroEmParenteses.groups[1]
                val group2 = matchNumeroEmParenteses.groups[2]

                if (group2 != null) {
                    return if (group1?.value == "-") {
                        -BigDecimal(group2.value)
                    } else {
                        BigDecimal(group2.value)
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
                (BigDecimal(numeroInterno) * (-BigDecimal.ONE)).toString()
            }

            return removeParentesesRedundantes(expressao.replace(matchParenteses.value.substring(1, matchParenteses.value.length - 1), conteudoSimplificadoParenteses))
        }

        return expressao
    }

    fun parse(expressao: String): BigDecimal {
        val expressaoSimplificada: String = removeParentesesRedundantes(expressao)

        val valorExpressao: BigDecimal? = tentaExtrairNumeroPuro(expressaoSimplificada)
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

        throw ErroDeFormatacaoException(expressaoSimplificada)
    }
}