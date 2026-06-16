// Componente de formulário para cadastro e edição de veículos
// Contém os campos: Proprietário, Marca, Modelo, Ano, Placa, Cor e Tipo

import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  ScrollView,
  Alert,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';

/**
 * Componente FormularioCadastro
 * Exibe um formulário com campos para cadastrar ou editar um veículo
 * 
 * Props:
 * - veiculoInicial: dados do veículo para edição (null para novo cadastro)
 * - aoSalvar: função chamada ao confirmar o cadastro
 * - aoCancelar: função chamada ao cancelar o formulário
 */
export default function FormularioCadastro({ veiculoInicial, aoSalvar, aoCancelar }) {
  // Estados para cada campo do formulário
  const [proprietario, setProprietario] = useState('');
  const [marca, setMarca] = useState('');
  const [modelo, setModelo] = useState('');
  const [ano, setAno] = useState('');
  const [placa, setPlaca] = useState('');
  const [cor, setCor] = useState('');
  const [tipo, setTipo] = useState('Carro');

  // Opções disponíveis para o tipo de veículo
  const tiposVeiculo = ['Carro', 'Moto', 'Caminhão', 'SUV', 'Van', 'Ônibus'];

  // Se estiver editando, preenche os campos com os dados do veículo
  useEffect(() => {
    if (veiculoInicial) {
      setProprietario(veiculoInicial.proprietario || '');
      setMarca(veiculoInicial.marca || '');
      setModelo(veiculoInicial.modelo || '');
      setAno(veiculoInicial.ano || '');
      setPlaca(veiculoInicial.placa || '');
      setCor(veiculoInicial.cor || '');
      setTipo(veiculoInicial.tipo || 'Carro');
    }
  }, [veiculoInicial]);

  /**
   * Valida os campos e salva o veículo
   */
  const handleSalvar = () => {
    // Validação dos campos obrigatórios
    if (!proprietario.trim()) {
      Alert.alert('Campo obrigatório', 'Informe o nome do proprietário.');
      return;
    }
    if (!marca.trim()) {
      Alert.alert('Campo obrigatório', 'Informe a marca do veículo.');
      return;
    }
    if (!modelo.trim()) {
      Alert.alert('Campo obrigatório', 'Informe o modelo do veículo.');
      return;
    }
    if (!ano.trim()) {
      Alert.alert('Campo obrigatório', 'Informe o ano do veículo.');
      return;
    }
    if (!placa.trim()) {
      Alert.alert('Campo obrigatório', 'Informe a placa do veículo.');
      return;
    }
    if (!cor.trim()) {
      Alert.alert('Campo obrigatório', 'Informe a cor do veículo.');
      return;
    }

    // Validação do ano (deve ser um número de 4 dígitos)
    const anoNum = parseInt(ano);
    if (isNaN(anoNum) || anoNum < 1900 || anoNum > new Date().getFullYear() + 1) {
      Alert.alert('Ano inválido', 'Informe um ano válido entre 1900 e o ano atual.');
      return;
    }

    // Chama a função de salvar com os dados do formulário
    aoSalvar({
      proprietario: proprietario.trim(),
      marca: marca.trim(),
      modelo: modelo.trim(),
      ano: ano.trim(),
      placa: placa.trim().toUpperCase(),
      cor: cor.trim(),
      tipo,
    });
  };

  /**
   * Renderiza um campo de texto do formulário
   */
  const renderCampo = (label, valor, aoMudar, placeholder, config = {}) => (
    <View style={estilos.grupoCampo}>
      <Text style={estilos.labelCampo}>{label}</Text>
      <TextInput
        style={estilos.inputCampo}
        value={valor}
        onChangeText={aoMudar}
        placeholder={placeholder}
        placeholderTextColor="#555"
        {...config}
      />
    </View>
  );

  return (
    <KeyboardAvoidingView
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      style={estilos.container}
    >
      <ScrollView contentContainerStyle={estilos.scrollContent} showsVerticalScrollIndicator={false}>
        {/* Título do formulário */}
        <Text style={estilos.tituloFormulario}>
          {veiculoInicial ? '✏️ Editar Veículo' : '🚗 Novo Veículo'}
        </Text>

        {/* Campo: Proprietário */}
        {renderCampo(
          'Proprietário *',
          proprietario,
          setProprietario,
          'Nome completo do proprietário',
          { autoCapitalize: 'words' }
        )}

        {/* Campo: Marca */}
        {renderCampo(
          'Marca *',
          marca,
          setMarca,
          'Ex: Toyota, Honda, Ford...',
          { autoCapitalize: 'words' }
        )}

        {/* Campo: Modelo */}
        {renderCampo(
          'Modelo *',
          modelo,
          setModelo,
          'Ex: Corolla, Civic, Ka...',
          { autoCapitalize: 'words' }
        )}

        {/* Campo: Ano */}
        {renderCampo(
          'Ano *',
          ano,
          setAno,
          'Ex: 2023',
          { keyboardType: 'numeric', maxLength: 4 }
        )}

        {/* Campo: Placa */}
        {renderCampo(
          'Placa *',
          placa,
          setPlaca,
          'Ex: ABC1234 ou ABC1D23',
          { autoCapitalize: 'characters', maxLength: 8 }
        )}

        {/* Campo: Cor */}
        {renderCampo(
          'Cor *',
          cor,
          setCor,
          'Ex: Preto, Branco, Prata...',
          { autoCapitalize: 'words' }
        )}

        {/* Seletor de Tipo de Veículo */}
        <View style={estilos.grupoCampo}>
          <Text style={estilos.labelCampo}>Tipo de Veículo</Text>
          <ScrollView horizontal showsHorizontalScrollIndicator={false} style={estilos.seletorTipo}>
            {tiposVeiculo.map((t) => (
              <TouchableOpacity
                key={t}
                style={[estilos.chipTipo, tipo === t && estilos.chipTipoSelecionado]}
                onPress={() => setTipo(t)}
              >
                <Text style={[estilos.textoChip, tipo === t && estilos.textoChipSelecionado]}>
                  {t}
                </Text>
              </TouchableOpacity>
            ))}
          </ScrollView>
        </View>

        {/* Botões de ação */}
        <View style={estilos.containerBotoes}>
          {/* Botão Cancelar */}
          <TouchableOpacity
            style={estilos.botaoCancelar}
            onPress={aoCancelar}
            activeOpacity={0.7}
          >
            <Text style={estilos.textoBotaoCancelar}>Cancelar</Text>
          </TouchableOpacity>

          {/* Botão Salvar */}
          <TouchableOpacity
            style={estilos.botaoSalvar}
            onPress={handleSalvar}
            activeOpacity={0.8}
          >
            <Text style={estilos.textoBotaoSalvar}>
              {veiculoInicial ? 'Atualizar' : 'Cadastrar'}
            </Text>
          </TouchableOpacity>
        </View>

        {/* Nota sobre campos obrigatórios */}
        <Text style={estilos.notaObrigatorio}>* Campos obrigatórios</Text>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

// Estilos do formulário de cadastro
const estilos = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0f0f23',
  },
  scrollContent: {
    padding: 20,
    paddingBottom: 40,
  },
  tituloFormulario: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#e94560',
    marginBottom: 24,
    textAlign: 'center',
  },
  grupoCampo: {
    marginBottom: 16,
  },
  labelCampo: {
    fontSize: 14,
    fontWeight: '600',
    color: '#ccc',
    marginBottom: 6,
  },
  inputCampo: {
    backgroundColor: '#16213e',
    borderRadius: 12,
    padding: 14,
    fontSize: 15,
    color: '#fff',
    borderWidth: 1,
    borderColor: '#0f3460',
  },
  seletorTipo: {
    flexDirection: 'row',
  },
  chipTipo: {
    backgroundColor: '#16213e',
    borderRadius: 20,
    paddingHorizontal: 16,
    paddingVertical: 8,
    marginRight: 8,
    borderWidth: 1,
    borderColor: '#0f3460',
  },
  chipTipoSelecionado: {
    backgroundColor: '#e94560',
    borderColor: '#e94560',
  },
  textoChip: {
    color: '#aaa',
    fontSize: 13,
    fontWeight: '500',
  },
  textoChipSelecionado: {
    color: '#fff',
    fontWeight: 'bold',
  },
  containerBotoes: {
    flexDirection: 'row',
    gap: 12,
    marginTop: 24,
    marginBottom: 12,
  },
  botaoCancelar: {
    flex: 1,
    backgroundColor: '#1a1a2e',
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#e94560',
  },
  textoBotaoCancelar: {
    color: '#e94560',
    fontSize: 16,
    fontWeight: 'bold',
  },
  botaoSalvar: {
    flex: 2,
    backgroundColor: '#e94560',
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
    shadowColor: '#e94560',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 6,
    elevation: 6,
  },
  textoBotaoSalvar: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
  },
  notaObrigatorio: {
    color: '#555',
    fontSize: 12,
    textAlign: 'center',
  },
});
