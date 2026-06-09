package ex4;

public class Guerreiro extends Personagem {
    public Guerreiro(String nome, int pontosVida) {
        super(nome, pontosVida);
    }

    @Override
    public void atacar(Personagem alvo) throws AlvoInvalidoException {
        if (alvo.getPontosVida() == 0) {
            throw new AlvoInvalidoException(alvo.getNome() + " já está fora de combate!");
        }
        System.out.println(getNome() + " ataca " + alvo.getNome() + " com a espada!");
        alvo.setPontosVida(alvo.getPontosVida() - 10);
    }
}
