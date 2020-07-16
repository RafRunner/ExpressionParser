package main

import dominio.ExpressionParser
import exceptions.ErroDeSintaxeException
import java.lang.ArithmeticException

fun getStringResultadoParse(expressao: String): String {
    return try {
        val expressionParser = ExpressionParser()
        val resultado = expressionParser.formatAndParse(expressao)

        "{ \"sucesso\": true, \"mensagem\": \"$resultado\" }"
    } catch (e: ErroDeSintaxeException) {
        "{ \"sucesso\": false, \"mensagem\": \"${e.message}\" }"
    } catch (e1: ArithmeticException) {
        "{ \"sucesso\": false, \"mensagem\": \"Erro de arritimética: ${e1.message}\" }"
    }
}
