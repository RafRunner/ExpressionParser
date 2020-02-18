package testes

import dominio.ExpressionParser
import dominio.MultiplicacaoDivisaoParser
import dominio.PotenciacaoParser
import view.CalculadoraView
import view.JanelaPrincipal
import kotlin.test.assertEquals

fun main() {
    val multiplicacaoDivisaoParser = MultiplicacaoDivisaoParser()
    val expressionParser = ExpressionParser()
    val potenciacaoParser = PotenciacaoParser()

    assertEquals("(12)", multiplicacaoDivisaoParser.parse("6*2"))
    assertEquals("(-4)", multiplicacaoDivisaoParser.parse("8/(-2)"))

    assertEquals("(0.125)", potenciacaoParser.parse("2^(-3)"))
    assertEquals("(2.0)", potenciacaoParser.parse("4^0.5"))

    assertEquals(12.toBigDecimal(), expressionParser.parse("3+(4+5)"))
    assertEquals(15.toBigDecimal(), expressionParser.parse("5+2*5"))
    assertEquals(4.toBigDecimal(), expressionParser.parse("2+10/5"))
    assertEquals(17.toBigDecimal(), expressionParser.parse("3*4+5"))
    assertEquals(140.toBigDecimal(), expressionParser.parse("(3+4)*(4*5)"))
    assertEquals((-17).toBigDecimal(), expressionParser.parse("3+5*(-4)"))
    assertEquals((1.125).toBigDecimal(), expressionParser.parse("1+2^(-15/5)"))
    assertEquals((-1).toBigDecimal(), expressionParser.parse("(-3+2)"))
    assertEquals((4).toBigDecimal(), expressionParser.parse("((1)*4)"))
    assertEquals((-101250.0).toBigDecimal(), expressionParser.parse("2*(-15^((-2+3)*4))"))
    assertEquals((0.0625).toBigDecimal(), expressionParser.parse("2^(-(4))"))
    assertEquals((7).toBigDecimal(), expressionParser.parse("(-(-(+((7)))))"))
    assertEquals((2).toBigDecimal(), expressionParser.parse("(((2)))"))

    JanelaPrincipal(CalculadoraView())
}
