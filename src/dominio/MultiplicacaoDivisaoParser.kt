package dominio

import java.lang.ArithmeticException

class MultiplicacaoDivisaoParser : Parser() {

    override val regex: Regex
        get() = Regex("""(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))([*\/])(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))""")

    override fun parse(expressao: String): String {
        val matchResult = regex.find(expressao) ?: return ""
        val groups = matchResult.groups

        val primeiroNumero: Double = trataNumeroDeGruoDeCaptura(groups, 1)
        val segundoNumero: Double = trataNumeroDeGruoDeCaptura(groups, 5)

        // Sempre vai existir se o mathResult existir, o compilador que não sabe
        val operacao = groups[4]?.value

        if (operacao == "/" && segundoNumero == 0.0) {
            throw ArithmeticException("Divisão por 0 é uma indeterminação")
        }

        val resultadoParcial: Double = if (operacao == "*") {
            primeiroNumero * segundoNumero
        } else {
            primeiroNumero / segundoNumero
        }

        return expressao.replaceFirst(matchResult.value, "($resultadoParcial)")
    }
}