package dominio

class MultiplicacaoDivisaoParser : Parser() {

    override val regex: Regex
        get() = Regex("""(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))([*\/])(\d+(\.\d+)?|\([+-]?\d+(\.\d+)?\))""")

    override fun parse(expressao: String): String {
        val matchResult = regex.find(expressao) ?: return ""

        val groups = matchResult.groups
        val resultadoParcial: String

        val primeiroNumero = trataNumeroDeGruoDeCaptura(groups, 1)
        val segundoNumero = trataNumeroDeGruoDeCaptura(groups, 5)

        resultadoParcial = if (groups[4]?.value == "*") {
            (primeiroNumero * segundoNumero).toString()
        } else {
            (primeiroNumero / segundoNumero).toString()
        }

        return expressao.replaceFirst(matchResult.value, "($resultadoParcial)")
    }
}