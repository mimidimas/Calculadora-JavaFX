package edu.curso.boundary;

import edu.curso.control.CalculadoraControl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CalculadoraBoundary extends Application {

    private TextField display;
    private CalculadoraControl controle; // Instância do Controle

    @Override
    public void start(Stage primaryStage) {
        controle = new CalculadoraControl();

        // 1. Configurando o Display e o botão CE
        display = new TextField("0");
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setFont(Font.font(18));
        display.setPrefWidth(180);

        Button btnCE = new Button("CE");
        btnCE.setPrefSize(50, 30);
        btnCE.setOnAction(e -> {
            controle.limpar();
            display.setText("0");
        });

        HBox topoCalculadora = new HBox(10, display, btnCE);
        topoCalculadora.setAlignment(Pos.CENTER);

        // 2. Configurando o Grid de Botões
        GridPane gridBotoes = new GridPane();
        gridBotoes.setHgap(5);
        gridBotoes.setVgap(5);
        gridBotoes.setAlignment(Pos.CENTER);

        String[][] layoutBotoes = {
            {"1", "2", "3", "+"},
            {"4", "5", "6", "-"},
            {"7", "8", "9", "*"},
            {",", "0", "=", "/"}
        };

        for (int linha = 0; linha < layoutBotoes.length; linha++) {
            for (int coluna = 0; coluna < layoutBotoes[linha].length; coluna++) {
                String textoBotao = layoutBotoes[linha][coluna];
                Button btn = new Button(textoBotao);
                btn.setPrefSize(50, 40);
                
                // Direciona o clique para o método que faz a ponte com o Controle
                btn.setOnAction(e -> handleClique(textoBotao));
                
                gridBotoes.add(btn, coluna, linha);
            }
        }

        // 3. Montando a tela
        VBox layoutPrincipal = new VBox(15, topoCalculadora, gridBotoes);
        layoutPrincipal.setPadding(new Insets(15));
        layoutPrincipal.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layoutPrincipal, 260, 280);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Método da Fronteira que conversa com o Controle
    private void handleClique(String valor) {
        if (valor.matches("[0-9,]")) {
            String novoTexto = controle.processarNumero(valor, display.getText());
            display.setText(novoTexto);
        } 
        else if (valor.matches("[+\\-*/]")) {
            controle.processarOperador(valor, display.getText());
        } 
        else if (valor.equals("=")) {
            String resultado = controle.calcularResultado(display.getText());
            display.setText(resultado);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}