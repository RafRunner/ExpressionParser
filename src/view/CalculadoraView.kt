package view

import dominio.ExpressionParser
import exceptions.ErroDeFormatacaoException
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.math.BigDecimal
import javax.swing.*

class CalculadoraView : JPanel() {

    private val btnCalcular = JButton("Calcular valor")
    private val expressionParser = ExpressionParser()

    init {
        isVisible = true

        val labelExplicacao = JLabel("<html>Entre com uma expressão matemática abaixo (podendo conter soma, subtração, multiplicação, divisão, potenciação e parênteses) e clique em calcular</html>")
        val textExpressao = JTextField()

        btnCalcular.addActionListener {
            val resultado: BigDecimal = try {
                expressionParser.parse(textExpressao.text)
            } catch (e: ErroDeFormatacaoException) {
                JOptionPane.showMessageDialog(this, e.message, "Erro de formatação!", JOptionPane.ERROR_MESSAGE)
                return@addActionListener
            }

            JOptionPane.showMessageDialog(this, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE)
        }

        border = BorderFactory.createTitledBorder("Calculadora")
        layout = GridBagLayout()

        val gb = GridBagConstraints()
        gb.anchor = GridBagConstraints.CENTER
        gb.fill = GridBagConstraints.HORIZONTAL
        gb.weightx = 1.0
        gb.weighty = 1.0

        gb.gridx = 0
        gb.gridy = 0
        add(JLabel(), gb)
        gb.weighty = 0.1

        gb.gridy = 1
        add(labelExplicacao, gb)

        gb.gridy = 2
        add(textExpressao, gb)
        gb.fill = GridBagConstraints.NONE

        gb.gridy = 3
        add(btnCalcular, gb)
        gb.weighty = 1.0

        gb.gridy = 4
        add(JLabel(), gb)
    }
}