package Ex7;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Hospital {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            if (nome == null || nome.isEmpty()) {
                throw new NullPointerException("Nome vazio!");
            }

            System.out.print("Peso: ");
            double peso = sc.nextDouble();

            System.out.println("Paciente cadastrado!");

        } catch (NullPointerException e) {
            System.out.println("Erro: nome inválido.");
        } catch (InputMismatchException e) {
            System.out.println("Erro: peso inválido.");
        } catch (Exception e) {
            System.out.println("Erro interno no sistema do hospital: " + e.getMessage());
        }

        sc.close();
    }
}
