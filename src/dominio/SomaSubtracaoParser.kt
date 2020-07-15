package dominio

class SomaSubtracaoParser : Parser() {

    override val regex: Regex
        get() = Regex("""(\D*-)?(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))([+-])(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))""")

    override fun parse(expressao: String): String {
        val matchResult = regex.find(expressao) ?: return ""

        val groups = matchResult.groups
        val resultadoParcial: String

        var primeiroNumero = trataNumeroDeGruoDeCaptura(groups, 2)
        val segundoNumero = trataNumeroDeGruoDeCaptura(groups, 6)

        if (groups[1] != null) {
            primeiroNumero *= -1
        }

        resultadoParcial = if (groups[5]?.value == "+") {
            (primeiroNumero + segundoNumero).toString()
        } else {
            (primeiroNumero - segundoNumero).toString()
        }

        return expressao.replaceFirst(matchResult.value, "($resultadoParcial)")
    }
}