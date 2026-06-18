import React, { useState } from 'react';
import { StyleSheet, Text, View, TextInput, Button, FlatList, TouchableOpacity } from 'react-native';

export default function App() {
  // Estado para armazenar a lista de veículos
  const [veiculos, setVeiculos] = useState([]);
  
  // Estados para os campos do formulário
  const [marca, setMarca] = useState('');
  const [modelo, setModelo] = useState('');
  const [ano, setAno] = useState('');
  const [placa, setPlaca] = useState('');

  // Função para salvar um novo veículo
  const salvarVeiculo = () => {
    // Validação simples para não salvar vazio
    if (marca === '' || modelo === '' || ano === '' || placa === '') {
      alert('Por favor, preencha todos os campos!');
      return;
    }

    // Criando o objeto veículo
    const novoVeiculo = {
      id: Math.random().toString(), // ID único gerado aleatoriamente
      marca,
      modelo,
      ano,
      placa
    };

    // Adicionando o novo veículo à lista (estado)
    setVeiculos([...veiculos, novoVeiculo]);

    // Limpando os campos do formulário após salvar
    setMarca('');
    setModelo('');
    setAno('');
    setPlaca('');
  };

  // Função para excluir um veículo da lista
  const excluirVeiculo = (id) => {
    // Filtra a lista, removendo o item que tem o id especificado
    setVeiculos(veiculos.filter(veiculo => veiculo.id !== id));
  };

  return (
    <View style={styles.container}>
      {/* 1. Tela principal com título do sistema */}
      <Text style={styles.titulo}>Cadastro de Veículos</Text>

      {/* 2. Formulário de cadastro com pelo menos 4 campos */}
      <View style={styles.formulario}>
        <TextInput
          style={styles.input}
          placeholder="Marca"
          value={marca}
          onChangeText={setMarca}
        />
        <TextInput
          style={styles.input}
          placeholder="Modelo"
          value={modelo}
          onChangeText={setModelo}
        />
        <TextInput
          style={styles.input}
          placeholder="Ano"
          value={ano}
          onChangeText={setAno}
          keyboardType="numeric"
        />
        <TextInput
          style={styles.input}
          placeholder="Placa"
          value={placa}
          onChangeText={setPlaca}
        />
        
        {/* 3. Botão para salvar/cadastrar */}
        <Button title="Salvar" onPress={salvarVeiculo} />
      </View>

      <Text style={styles.subtitulo}>Veículos Cadastrados:</Text>

      {/* 4. Listagem dos itens cadastrados */}
      <FlatList
        data={veiculos}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <View style={styles.itemLista}>
            <View style={styles.infoVeiculo}>
              <Text style={styles.textoItem}><Text style={styles.negrito}>Marca:</Text> {item.marca}</Text>
              <Text style={styles.textoItem}><Text style={styles.negrito}>Modelo:</Text> {item.modelo}</Text>
              <Text style={styles.textoItem}><Text style={styles.negrito}>Ano:</Text> {item.ano}</Text>
              <Text style={styles.textoItem}><Text style={styles.negrito}>Placa:</Text> {item.placa}</Text>
            </View>
            
            {/* 5. Opção para excluir um item da lista */}
            <TouchableOpacity onPress={() => excluirVeiculo(item.id)} style={styles.botaoExcluir}>
              <Text style={styles.textoBotaoExcluir}>Excluir</Text>
            </TouchableOpacity>
          </View>
        )}
        style={styles.lista}
      />
    </View>
  );
}

// 6. Organização básica do código (estilos separados)
const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f5f5f5',
    marginTop: 30, // Margem para não sobrepor a barra de status do celular
  },
  titulo: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 20,
    color: '#333',
  },
  formulario: {
    backgroundColor: '#fff',
    padding: 15,
    borderRadius: 8,
    marginBottom: 20,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 2,
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 4,
    padding: 10,
    marginBottom: 10,
    backgroundColor: '#fafafa',
  },
  subtitulo: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
    color: '#555',
  },
  lista: {
    flex: 1,
  },
  itemLista: {
    flexDirection: 'row',
    backgroundColor: '#fff',
    padding: 15,
    borderRadius: 8,
    marginBottom: 10,
    alignItems: 'center',
    justifyContent: 'space-between',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 1,
  },
  infoVeiculo: {
    flex: 1,
  },
  textoItem: {
    fontSize: 14,
    marginBottom: 2,
    color: '#333',
  },
  negrito: {
    fontWeight: 'bold',
  },
  botaoExcluir: {
    backgroundColor: '#ff5252',
    paddingVertical: 8,
    paddingHorizontal: 12,
    borderRadius: 4,
    marginLeft: 10,
  },
  textoBotaoExcluir: {
    color: '#fff',
    fontWeight: 'bold',
  },
});
