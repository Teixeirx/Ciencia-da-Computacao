package Ex3;

import java.util.Scanner;

public class FormularioIdade {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite sua idade: ");
        String entrada = sc.nextLine();

        try {
            int idade = Integer.parseInt(entrada);
            System.out.println("Idade: " + idade);

        } catch (NumberFormatException e) {
            System.out.println("Por favor, utilize apenas algarismos numéricos");
        }

        sc.close();
    }
}