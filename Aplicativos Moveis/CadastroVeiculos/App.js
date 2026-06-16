// Arquivo principal do aplicativo de Cadastro de Veículos
// Gabriel Maier Teixeira - Aplicativos Móveis
// Gerencia a navegação entre as telas de listagem e cadastro

import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  Alert,
  StatusBar,
  SafeAreaView,
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import FormularioCadastro from './components/FormularioCadastro';
import CartaoVeiculo from './components/CartaoVeiculo';

// Chave usada para persistir os dados no AsyncStorage
const CHAVE_VEICULOS = '@cadastro_veiculos';

export default function App() {
  // Estado principal: lista de veículos cadastrados
  const [veiculos, setVeiculos] = useState([]);

  // Controla se o formulário de cadastro está visível
  const [mostrarFormulario, setMostrarFormulario] = useState(false);

  // Armazena o veículo sendo editado (null = novo cadastro)
  const [veiculoEmEdicao, setVeiculoEmEdicao] = useState(null);

  // Carrega os veículos salvos ao iniciar o aplicativo
  useEffect(() => {
    carregarVeiculos();
  }, []);

  /**
   * Carrega os veículos do armazenamento local (AsyncStorage)
   */
  const carregarVeiculos = async () => {
    try {
      const dadosSalvos = await AsyncStorage.getItem(CHAVE_VEICULOS);
      if (dadosSalvos !== null) {
        setVeiculos(JSON.parse(dadosSalvos));
      }
    } catch (erro) {
      console.error('Erro ao carregar veículos:', erro);
      Alert.alert('Erro', 'Não foi possível carregar os dados salvos.');
    }
  };

  /**
   * Salva a lista de veículos no armazenamento local
   * @param {Array} listaAtualizada - Lista de veículos a ser salva
   */
  const salvarVeiculos = async (listaAtualizada) => {
    try {
      await AsyncStorage.setItem(CHAVE_VEICULOS, JSON.stringify(listaAtualizada));
    } catch (erro) {
      console.error('Erro ao salvar veículos:', erro);
      Alert.alert('Erro', 'Não foi possível salvar os dados.');
    }
  };

  /**
   * Adiciona um novo veículo ou atualiza um existente
   * @param {Object} dadosVeiculo - Dados do veículo preenchidos no formulário
   */
  const salvarVeiculo = async (dadosVeiculo) => {
    let listaAtualizada;

    if (veiculoEmEdicao) {
      // Atualiza veículo existente mantendo o mesmo ID
      listaAtualizada = veiculos.map((v) =>
        v.id === veiculoEmEdicao.id ? { ...dadosVeiculo, id: veiculoEmEdicao.id, dataCadastro: veiculoEmEdicao.dataCadastro } : v
      );
    } else {
      // Cria novo veículo com ID único baseado no timestamp
      const novoVeiculo = {
        ...dadosVeiculo,
        id: Date.now().toString(),
        dataCadastro: new Date().toLocaleDateString('pt-BR'),
      };
      listaAtualizada = [...veiculos, novoVeiculo];
    }

    setVeiculos(listaAtualizada);
    await salvarVeiculos(listaAtualizada);
    setMostrarFormulario(false);
    setVeiculoEmEdicao(null);
  };

  /**
   * Exibe confirmação e realiza a exclusão de um veículo da lista
   * @param {string} id - ID do veículo a ser excluído
   * @param {string} nomeVeiculo - Nome para exibir na confirmação
   */
  const excluirVeiculo = (id, nomeVeiculo) => {
    Alert.alert(
      'Confirmar Exclusão',
      `Deseja excluir o veículo "${nomeVeiculo}"?`,
      [
        {
          text: 'Cancelar',
          style: 'cancel',
        },
        {
          text: 'Excluir',
          style: 'destructive',
          onPress: async () => {
            const listaAtualizada = veiculos.filter((v) => v.id !== id);
            setVeiculos(listaAtualizada);
            await salvarVeiculos(listaAtualizada);
          },
        },
      ]
    );
  };

  /**
   * Abre o formulário preenchido para editar um veículo existente
   * @param {Object} veiculo - Dados do veículo a ser editado
   */
  const editarVeiculo = (veiculo) => {
    setVeiculoEmEdicao(veiculo);
    setMostrarFormulario(true);
  };

  /**
   * Cancela o formulário e limpa o estado de edição
   */
  const cancelarFormulario = () => {
    setMostrarFormulario(false);
    setVeiculoEmEdicao(null);
  };

  return (
    <SafeAreaView style={estilos.container}>
      {/* Barra de status com cor escura */}
      <StatusBar backgroundColor="#1a1a2e" barStyle="light-content" />

      {/* Cabeçalho do aplicativo */}
      <View style={estilos.cabecalho}>
        <Text style={estilos.tituloCabecalho}>🚗 Cadastro de Veículos</Text>
        <Text style={estilos.subtituloCabecalho}>Gerencie sua frota com facilidade</Text>
      </View>

      {mostrarFormulario ? (
        // Tela de cadastro/edição
        <FormularioCadastro
          veiculoInicial={veiculoEmEdicao}
          aoSalvar={salvarVeiculo}
          aoCancelar={cancelarFormulario}
        />
      ) : (
        // Tela de listagem
        <View style={estilos.conteudo}>
          {/* Barra com contador de veículos */}
          <View style={estilos.barraInfo}>
            <Text style={estilos.textoInfo}>
              {veiculos.length === 0
                ? 'Nenhum veículo cadastrado'
                : `${veiculos.length} veículo${veiculos.length !== 1 ? 's' : ''} cadastrado${veiculos.length !== 1 ? 's' : ''}`}
            </Text>
          </View>

          {veiculos.length === 0 ? (
            // Mensagem exibida quando não há veículos cadastrados
            <View style={estilos.listaVazia}>
              <Text style={estilos.iconeLista}>🚘</Text>
              <Text style={estilos.textoListaVazia}>Sua garagem está vazia!</Text>
              <Text style={estilos.subtextoListaVazia}>
                Toque no botão abaixo para cadastrar seu primeiro veículo.
              </Text>
            </View>
          ) : (
            // Lista de veículos cadastrados usando FlatList para melhor performance
            <FlatList
              data={veiculos}
              keyExtractor={(item) => item.id}
              renderItem={({ item }) => (
                <CartaoVeiculo
                  veiculo={item}
                  aoEditar={() => editarVeiculo(item)}
                  aoExcluir={() => excluirVeiculo(item.id, `${item.marca} ${item.modelo}`)}
                />
              )}
              contentContainerStyle={estilos.lista}
              showsVerticalScrollIndicator={false}
            />
          )}

          {/* Botão flutuante para cadastrar novo veículo */}
          <TouchableOpacity
            style={estilos.botaoAdicionar}
            onPress={() => setMostrarFormulario(true)}
            activeOpacity={0.8}
          >
            <Text style={estilos.textoBotaoAdicionar}>+ Cadastrar Veículo</Text>
          </TouchableOpacity>
        </View>
      )}
    </SafeAreaView>
  );
}

// Estilos do componente principal usando StyleSheet para melhor performance
const estilos = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0f0f23',
  },
  cabecalho: {
    backgroundColor: '#1a1a2e',
    paddingVertical: 20,
    paddingHorizontal: 20,
    borderBottomWidth: 2,
    borderBottomColor: '#e94560',
    alignItems: 'center',
  },
  tituloCabecalho: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#e94560',
    letterSpacing: 1,
  },
  subtituloCabecalho: {
    fontSize: 13,
    color: '#888',
    marginTop: 4,
  },
  conteudo: {
    flex: 1,
  },
  barraInfo: {
    backgroundColor: '#16213e',
    paddingHorizontal: 20,
    paddingVertical: 10,
  },
  textoInfo: {
    color: '#aaa',
    fontSize: 13,
  },
  lista: {
    padding: 16,
    paddingBottom: 100,
  },
  listaVazia: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 40,
  },
  iconeLista: {
    fontSize: 64,
    marginBottom: 16,
  },
  textoListaVazia: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#ccc',
    textAlign: 'center',
    marginBottom: 8,
  },
  subtextoListaVazia: {
    fontSize: 14,
    color: '#666',
    textAlign: 'center',
    lineHeight: 22,
  },
  botaoAdicionar: {
    position: 'absolute',
    bottom: 24,
    left: 24,
    right: 24,
    backgroundColor: '#e94560',
    borderRadius: 16,
    paddingVertical: 16,
    alignItems: 'center',
    shadowColor: '#e94560',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.4,
    shadowRadius: 8,
    elevation: 8,
  },
  textoBotaoAdicionar: {
    color: '#fff',
    fontSize: 17,
    fontWeight: 'bold',
    letterSpacing: 0.5,
  },
});
