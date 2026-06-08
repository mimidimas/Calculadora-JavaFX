package edu.curso.control;

public class CalculadoraControl {
    
    private double valorAnterior = 0;
    private String operadorAtual = "";
    private boolean iniciarNovoNumero = true;

    public String processarNumero(String numero, String textoDisplay) {
        if (iniciarNovoNumero) {
            iniciarNovoNumero = false;
            return numero.equals(",") ? "0," : numero;
        }
        // Evita múltiplas vírgulas
        if (numero.equals(",") && textoDisplay.contains(",")) {
            return textoDisplay;
        }
        return textoDisplay + numero;
    }

    public void processarOperador(String operador, String textoDisplay) {
        try {
            // Converte o texto da tela para Double (trocando vírgula por ponto)
            valorAnterior = Double.parseDouble(textoDisplay.replace(",", "."));
        } catch (NumberFormatException e) {
            valorAnterior = 0;
        }
        operadorAtual = operador;
        iniciarNovoNumero = true;
    }

    public String calcularResultado(String textoDisplay) {
        if (operadorAtual.isEmpty() || iniciarNovoNumero) return textoDisplay;
        
        try {
            double valorAtual = Double.parseDouble(textoDisplay.replace(",", "."));
            
            // Chama a Entidade para resolver a regra de negócio
            double resultado = realizarCalculo(valorAnterior, valorAtual, operadorAtual);
            
            operadorAtual = "";
            iniciarNovoNumero = true;
            
            // Formata o resultado
            String textoResultado = (resultado % 1 == 0) ? 
                                    String.valueOf((long) resultado) : 
                                    String.valueOf(resultado);
            return textoResultado.replace(".", ",");

        } catch (Exception e) {
            iniciarNovoNumero = true;
            operadorAtual = "";
            return "Erro";
        }
    }

    public double realizarCalculo(double valor1, double valor2, String operador) throws Exception {
        switch (operador) {
            case "+": return valor1 + valor2;
            case "-": return valor1 - valor2;
            case "*": return valor1 * valor2;
            case "/": 
                if (valor2 == 0) {
                    throw new Exception("Divisão por zero");
                }
                return valor1 / valor2;
            default: return valor2;
        }
    }

    public void limpar() {
        valorAnterior = 0;
        operadorAtual = "";
        iniciarNovoNumero = true;
    }
}