package main

import dominio.ExpressionParser
import exceptions.ErroDeFormatacaoException

fun getStringResultadoParse(expressao: String): String {
    return try {
        val expressionParser = ExpressionParser()
        val resultado = expressionParser.formatAndParse(expressao)

        "{ \"sucesso\": true, \"mensagem\": \"$resultado\" }"
    } catch (e: ErroDeFormatacaoException) {
        "{ \"sucesso\": false, \"mensagem\": \"Erro de formatação!\" }"
    } catch (e1: ArithmeticException) {
        "{ \"sucesso\": false, \"mensagem\": \"Erro de arritimética! Descrição: ${e1.message} }"
    }
}
