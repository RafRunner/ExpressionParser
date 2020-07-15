package exceptions

import java.lang.Exception

class ErroDeSintaxeException(local: String) : Exception("Erro de sintaxe na expressão ao tentar processar: $local!")
