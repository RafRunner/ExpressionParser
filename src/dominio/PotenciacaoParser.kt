package dominio

import kotlin.math.pow

class PotenciacaoParser : Parser() {

    override val regex: Regex
        get() = Regex("""(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))\^(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))""")

    override fun parse(expressao: String): String {
        val matchResult = regex.find(expressao) ?: return ""
        val groups = matchResult.groups

        val primeiroNumero: Double = trataNumeroDeGruoDeCaptura(groups, 1)
        val segundoNumero: Double = trataNumeroDeGruoDeCaptura(groups, 4)

        val resultadoParcial: String = if (groups[1]?.value == "(-") {
            (-primeiroNumero.pow(segundoNumero)).toString()
        } else {
            primeiroNumero.pow(segundoNumero).toString()
        }

        return expressao.replaceFirst(matchResult.value, "($resultadoParcial)")
    }
}