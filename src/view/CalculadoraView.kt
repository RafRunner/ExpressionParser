package view

import dominio.ExpressionParser
import exceptions.ErroDeSintaxeException
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener


class CalculadoraView : JPanel() {

    private val btnCalcular = JButton("Calcular valor")
    private val expressionParser = ExpressionParser()
    private val textExpressao = JTextField()

    init {
        isVisible = true

        val labelExplicacao = JLabel("<html>Entre com uma expressão matemática abaixo (podendo conter soma, subtração, multiplicação, divisão, potenciação e parênteses) e clique em calcular</html>")
        labelExplicacao.font = Font(labelExplicacao.font.name, Font.PLAIN, 15)

        textExpressao.font = Font(labelExplicacao.font.name, Font.PLAIN, 15)
        textExpressao.addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent) {}

            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == 10) {
                    calculaExpressaoMostraResultadoOuErro()
                }
            }

            override fun keyReleased(e: KeyEvent) {}
        })

        btnCalcular.addActionListener {
            calculaExpressaoMostraResultadoOuErro()
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

    private fun calculaExpressaoMostraResultadoOuErro() {
        val resultado: String = try {
            expressionParser.formatAndParse(textExpressao.text)
        } catch (e: ErroDeSintaxeException) {
            JOptionPane.showMessageDialog(this, e.message, "Erro de formatação!", JOptionPane.ERROR_MESSAGE)
            return
        } catch (e1: ArithmeticException) {
            JOptionPane.showMessageDialog(this, "Erro de arritimética! Descrição: " + e1.message, "Erro de arritimética!", JOptionPane.ERROR_MESSAGE)
            return
        }

        JOptionPane.showMessageDialog(this, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE)
    }
}