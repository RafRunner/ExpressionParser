package dominio

import java.lang.RuntimeException
import java.math.BigDecimal

abstract class Parser {

    protected abstract val regex: Regex

    abstract fun parse(expressao: String): String

    protected fun trataNumeroDeGruoDeCaptura(groups: MatchGroupCollection, index: Int): BigDecimal {
        val representacaoBruta: String = groups[index]?.value ?: throw RuntimeException("Não foi possível capturar o número. Grupos: $groups")
        return BigDecimal(representacaoBruta.replace(Regex("[()]"), ""))
    }
}