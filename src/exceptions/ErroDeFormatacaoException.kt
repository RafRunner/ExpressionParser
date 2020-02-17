package exceptions

import java.lang.Exception

class ErroDeFormatacaoException(val local: String) : Exception("Erro de formatação na expressão próximo de: $local!")
