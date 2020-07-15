package main

import dominio.ExpressionParser
import exceptions.ErroDeSintaxeException

fun getStringResultadoParse(expressao: String): String {
    return try {
        val expressionParser = ExpressionParser()
        val resultado = expressionParser.formatAndParse(expressao)

        "{ \"sucesso\": true, \"mensagem\": \"$resultado\" }"
    } catch (e: ErroDeSintaxeException) {
        "{ \"sucesso\": false, \"mensagem\": \"Erro de sintaxe!\" }"
    } catch (e1: ArithmeticException) {
        "{ \"sucesso\": false, \"mensagem\": \"Erro de arritimética! Descrição: ${e1.message} }"
    }
}
