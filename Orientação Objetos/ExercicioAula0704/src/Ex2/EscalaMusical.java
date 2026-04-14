package Ex2;

import java.util.Scanner;

public class EscalaMusical {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] notas = {"Dó", "Ré", "Mi", "Fá", "Sol", "Lá", "Si"};

        try {
            System.out.print("Digite um número de 1 a 7: ");
            int num = sc.nextInt();

            System.out.println("Nota: " + notas[num - 1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Número inválido! Escolha entre 1 e 7.");
        }

        sc.close();
    }
}