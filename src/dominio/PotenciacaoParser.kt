package dominio

import java.lang.ArithmeticException
import kotlin.math.pow

class PotenciacaoParser : Parser() {

    override val regex: Regex
        get() = Regex("""(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))\^(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))""")

    override fun parse(expressao: String): String {
        val matchResult = regex.find(expressao) ?: return ""
        val groups = matchResult.groups

        val primeiroNumero: Double = trataNumeroDeGruoDeCaptura(groups, 1)
        val segundoNumero: Double = trataNumeroDeGruoDeCaptura(groups, 4)

        if (primeiroNumero == segundoNumero && primeiroNumero == 0.0) {
            throw ArithmeticException("0^0 é uma indeterminação")
        }

        if (primeiroNumero == 0.0 && segundoNumero < 0.0) {
            throw ArithmeticException("Divisão por 0 é uma indeterminação")
        }

        val resultadoParcial: Double = if (groups[1]?.value == "(-") {
            -primeiroNumero.pow(segundoNumero)
        } else {
            primeiroNumero.pow(segundoNumero)
        }

        return expressao.replaceFirst(matchResult.value, "($resultadoParcial)")
    }
}