package ex4;

public class Paladino extends Personagem implements Curandeiro {
    public Paladino(String nome, int pontosVida) {
        super(nome, pontosVida);
    }

    @Override
    public void atacar(Personagem alvo) throws AlvoInvalidoException {
        if (alvo.getPontosVida() == 0) {
            throw new AlvoInvalidoException(alvo.getNome() + " já está fora de combate!");
        }
        System.out.println(getNome() + " ataca " + alvo.getNome() + " com o martelo!");
        alvo.setPontosVida(alvo.getPontosVida() - 8);
    }

    @Override
    public void curarAliado(Personagem aliado) throws AlvoInvalidoException {
        if (aliado.getPontosVida() == 0) {
            throw new AlvoInvalidoException(aliado.getNome() + " já está fora de combate!");
        }
        System.out.println(getNome() + " cura " + aliado.getNome());
        aliado.setPontosVida(aliado.getPontosVida() + 5);
    }
}
