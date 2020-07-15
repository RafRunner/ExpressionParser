package main;

import view.CalculadoraView;
import view.JanelaPrincipal;

public class MainGUI {

    public static void main(String[] args) {
        new JanelaPrincipal(new CalculadoraView());
    }
}
