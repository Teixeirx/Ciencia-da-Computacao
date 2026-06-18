# Cadastro de Veículos - React Native

Projeto de conclusão (Trabalho Final - Nota 03) para a disciplina de Aplicativos Móveis, curso de Ciência da Computação (UFN).

**Aluno:** Gabriel Maier Teixeira
**Tema:** Cadastro de Veículos

## Funcionalidades
- Tela principal com título do sistema;
- Formulário com os campos de Marca, Modelo, Ano e Placa;
- Botão "Salvar" para armazenar os dados no estado da aplicação (`useState`);
- Lista com todos os veículos cadastrados (`FlatList`);
- Opção para excluir um veículo da lista;
- Comentários explicando a lógica base;
- Estilização limpa e organizada.

## Como Instalar e Executar

1. **Pré-requisitos:**
   - Node.js instalado na máquina
   - Ter o [Expo Go](https://expo.dev/go) instalado no seu celular, caso deseje testar em dispositivo físico.

2. **Instalação:**
   Abra o terminal na pasta raiz do projeto (`CadastroVeiculos`) e execute o seguinte comando para baixar as dependências:
   ```bash
   npm install
   ```
   *(Caso queira rodar via web, as dependências web também estão inclusas no projeto).*

3. **Execução:**
   Ainda no terminal da pasta do projeto, execute:
   ```bash
   npx expo start
   ```

4. **Testando o Aplicativo:**
   - **No celular:** Com o comando acima rodando, abra o aplicativo *Expo Go* no seu celular (conectado à mesma rede WiFi) e escaneie o QR Code que apareceu no terminal ou no navegador (pressionando a tecla `c` para abrir no navegador, se necessário).
   - **Na web:** No terminal, pressione a tecla `w` para abrir a aplicação diretamente no seu navegador.
   - **No Emulador:** Caso possua emulador Android configurado, pressione `a`.
