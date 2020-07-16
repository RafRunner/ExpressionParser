package dominio

class SomaSubtracaoParser : Parser() {

    override val regex: Regex
        get() = Regex("""(\D*-)?(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))([+-])(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))""")

    override fun parse(expressao: String): String {
        val matchResult = regex.find(expressao) ?: return ""
        val groups = matchResult.groups

        var primeiroNumero: Double = trataNumeroDeGruoDeCaptura(groups, 2)
        val segundoNumero: Double = trataNumeroDeGruoDeCaptura(groups, 6)

        if (groups[1] != null) {
            primeiroNumero *= -1
        }

        val resultadoParcial: Double = if (groups[5]?.value == "+") {
            primeiroNumero + segundoNumero
        } else {
            primeiroNumero - segundoNumero
        }

        return expressao.replaceFirst(matchResult.value, "($resultadoParcial)")
    }
}