package view

import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

class JanelaPrincipal(painel: JPanel) {

    private val largura = 600
    private val altura = 400

    private val janela: JFrame = JFrame("Calculadora/Parser de expressões")

    init {
        janela.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        janela.setLocation(500, 300)
        janela.preferredSize = Dimension(largura, altura)
        janela.isVisible = true
        janela.isResizable = false
        janela.add(painel)
        janela.pack()
    }
}