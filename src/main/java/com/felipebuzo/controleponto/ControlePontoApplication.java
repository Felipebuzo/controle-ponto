package com.felipebuzo.controleponto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class ControlePontoApplication {

	public static void main(String[] args) {
    carregarVariaveisDeAmbiente();
    SpringApplication.run(ControlePontoApplication.class, args);
}
	private static void carregarVariaveisDeAmbiente() {
    try (BufferedReader leitor = new BufferedReader(new FileReader(".env"))) {
        String linha;
        while ((linha = leitor.readLine()) != null) {
            if (linha.trim().isEmpty() || linha.startsWith("#")) {
                continue;
            }
            String[] partes = linha.split("=", 2);
            if (partes.length == 2) {
                String chave = partes[0].trim();
                String valor = partes[1].trim();
                System.setProperty(chave, valor);
            }
        }
    } catch (IOException e) {
        System.out.println("Aviso: arquivo .env não encontrado ou não pôde ser lido.");
    }
}

}
