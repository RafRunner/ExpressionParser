package dominio

import exceptions.ErroDeFormatacaoException
import java.math.BigDecimal

class ExpressionParser {

    val potenciacaoParser: PotenciacaoParser = PotenciacaoParser()
    val multiplicacaoDivisaoParser: MultiplicacaoDivisaoParser = MultiplicacaoDivisaoParser()
    val somaSubtracaoParser: SomaSubtracaoParser = SomaSubtracaoParser()

    // Essa regex verifica se uma expressão sem parênteses é válida (não considera parênteses)
    private val regexExpressaoGeral = Regex("""[+-]?\d+(\^[+-]?\d+(\/[+-]?\d+)?)?([*\/+-]?\d+(\^[+-]?\d+(\/[+-]?\d+)?)?)*""")
    private val regexExpressaoAninhada = Regex("""\((([^\)])+)\)""")

    private fun expressaoEhNumero(expressao: String): Boolean = Regex("""[+-]?\d+""").find(expressao)?.value == expressao

    private fun getExpressaoAninhada(expressao: String): String {
        val matchResult: MatchResult = regexExpressaoAninhada.find(expressao) ?: return ""
        val conteudoParenteses: String? = matchResult.groups[1]?.value

        if (conteudoParenteses == null || expressaoEhNumero(conteudoParenteses)) {
            return ""
        }

        return conteudoParenteses
    }

    fun parse(expressao: String): BigDecimal {
        if (expressaoEhNumero(expressao)) {
            return BigDecimal(expressao)
        }

        val expressaoParenteses: String = getExpressaoAninhada(expressao)

        if (expressaoParenteses != "") {
            val resultadoParcialParenteses = parse(expressaoParenteses)
            return parse(expressao.replaceFirst("($expressaoParenteses)", resultadoParcialParenteses.toString()))
        }

        val resultadoParcialPotenciacao: String = potenciacaoParser.parse(expressao)

        if (resultadoParcialPotenciacao != "") {
            return parse(resultadoParcialPotenciacao)
        }

        val resultadoParcialMultiplicacaoDivisao: String = multiplicacaoDivisaoParser.parse(expressao)

        if (resultadoParcialMultiplicacaoDivisao != "") {
            return parse(resultadoParcialMultiplicacaoDivisao)
        }

        val resultadoParcialSomaSubtracao: String = somaSubtracaoParser.parse(expressao)

        if (resultadoParcialSomaSubtracao == "") {
            throw ErroDeFormatacaoException(expressao)
        }

        return parse(resultadoParcialSomaSubtracao)
    }
}