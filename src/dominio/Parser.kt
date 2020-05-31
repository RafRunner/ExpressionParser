package dominio

import java.lang.RuntimeException

abstract class Parser {

    protected abstract val regex: Regex

    abstract fun parse(expressao: String): String

    protected fun trataNumeroDeGruoDeCaptura(groups: MatchGroupCollection, index: Int): Double {
        val representacaoBruta: String = groups[index]?.value ?: throw RuntimeException("Não foi possível capturar o número. Grupos: $groups")
        return (representacaoBruta.replace(Regex("[()]"), "")).toDouble()
    }
}