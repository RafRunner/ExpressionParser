package main;

public class MainConsole {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("{ \"sucesso\": false, \"mensagem\": \"Número incorreto de argumentos! Numéro correto: 1\" }");
            return;
        }

        final String expressao = args[0];
        System.out.println(KtParserWrapperKt.getStringResultadoParse(expressao));
    }
}
