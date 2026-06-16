// Componente CartaoVeiculo
// Exibe as informações de um veículo em um card com opções de editar e excluir

import React, { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Animated,
} from 'react-native';

/**
 * Mapeamento de emoji para cada tipo de veículo
 */
const EMOJI_TIPO = {
  'Carro': '🚗',
  'Moto': '🏍️',
  'Caminhão': '🚛',
  'SUV': '🚙',
  'Van': '🚐',
  'Ônibus': '🚌',
};

/**
 * Componente CartaoVeiculo
 * Renderiza um card com os dados de um veículo cadastrado
 * 
 * Props:
 * - veiculo: objeto com os dados do veículo
 * - aoEditar: função chamada ao pressionar o botão de editar
 * - aoExcluir: função chamada ao pressionar o botão de excluir
 */
export default function CartaoVeiculo({ veiculo, aoEditar, aoExcluir }) {
  // Controla se os detalhes do veículo estão expandidos
  const [expandido, setExpandido] = useState(false);

  // Obtém o emoji correspondente ao tipo do veículo
  const emojiVeiculo = EMOJI_TIPO[veiculo.tipo] || '🚗';

  return (
    <View style={estilos.cartao}>
      {/* Cabeçalho do cartão - clicável para expandir/recolher */}
      <TouchableOpacity
        style={estilos.cabecalhoCartao}
        onPress={() => setExpandido(!expandido)}
        activeOpacity={0.7}
      >
        {/* Ícone e informações principais */}
        <View style={estilos.iconeContainer}>
          <Text style={estilos.emojiVeiculo}>{emojiVeiculo}</Text>
        </View>

        <View style={estilos.infoPrincipal}>
          {/* Nome do veículo: Marca + Modelo */}
          <Text style={estilos.nomeVeiculo}>
            {veiculo.marca} {veiculo.modelo}
          </Text>
          {/* Placa e ano do veículo */}
          <View style={estilos.linhaSubtitulo}>
            <View style={estilos.badgePlaca}>
              <Text style={estilos.textoBadgePlaca}>{veiculo.placa}</Text>
            </View>
            <Text style={estilos.anoTexto}>• {veiculo.ano}</Text>
          </View>
        </View>

        {/* Seta indicando se está expandido ou recolhido */}
        <Text style={estilos.seta}>{expandido ? '▲' : '▼'}</Text>
      </TouchableOpacity>

      {/* Detalhes expandidos do veículo */}
      {expandido && (
        <View style={estilos.detalhes}>
          {/* Linha divisória */}
          <View style={estilos.divisor} />

          {/* Grade com informações detalhadas */}
          <View style={estilos.gradeDetalhes}>
            {/* Proprietário */}
            <View style={estilos.itemDetalhe}>
              <Text style={estilos.labelDetalhe}>👤 Proprietário</Text>
              <Text style={estilos.valorDetalhe}>{veiculo.proprietario}</Text>
            </View>

            {/* Cor */}
            <View style={estilos.itemDetalhe}>
              <Text style={estilos.labelDetalhe}>🎨 Cor</Text>
              <Text style={estilos.valorDetalhe}>{veiculo.cor}</Text>
            </View>

            {/* Tipo */}
            <View style={estilos.itemDetalhe}>
              <Text style={estilos.labelDetalhe}>{emojiVeiculo} Tipo</Text>
              <Text style={estilos.valorDetalhe}>{veiculo.tipo}</Text>
            </View>

            {/* Data de cadastro */}
            <View style={estilos.itemDetalhe}>
              <Text style={estilos.labelDetalhe}>📅 Cadastrado em</Text>
              <Text style={estilos.valorDetalhe}>{veiculo.dataCadastro}</Text>
            </View>
          </View>

          {/* Botões de ação: Editar e Excluir */}
          <View style={estilos.containerAcoes}>
            {/* Botão Editar */}
            <TouchableOpacity
              style={estilos.botaoEditar}
              onPress={aoEditar}
              activeOpacity={0.7}
            >
              <Text style={estilos.textoBotaoEditar}>✏️ Editar</Text>
            </TouchableOpacity>

            {/* Botão Excluir */}
            <TouchableOpacity
              style={estilos.botaoExcluir}
              onPress={aoExcluir}
              activeOpacity={0.7}
            >
              <Text style={estilos.textoBotaoExcluir}>🗑️ Excluir</Text>
            </TouchableOpacity>
          </View>
        </View>
      )}
    </View>
  );
}

// Estilos do componente de cartão de veículo
const estilos = StyleSheet.create({
  cartao: {
    backgroundColor: '#16213e',
    borderRadius: 16,
    marginBottom: 12,
    overflow: 'hidden',
    borderWidth: 1,
    borderColor: '#0f3460',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
    elevation: 4,
  },
  cabecalhoCartao: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
  },
  iconeContainer: {
    width: 50,
    height: 50,
    borderRadius: 25,
    backgroundColor: '#0f3460',
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 14,
  },
  emojiVeiculo: {
    fontSize: 24,
  },
  infoPrincipal: {
    flex: 1,
  },
  nomeVeiculo: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#fff',
    marginBottom: 6,
  },
  linhaSubtitulo: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  badgePlaca: {
    backgroundColor: '#e94560',
    borderRadius: 6,
    paddingHorizontal: 8,
    paddingVertical: 2,
  },
  textoBadgePlaca: {
    color: '#fff',
    fontSize: 12,
    fontWeight: 'bold',
    letterSpacing: 1,
  },
  anoTexto: {
    color: '#aaa',
    fontSize: 13,
    marginLeft: 8,
  },
  seta: {
    color: '#555',
    fontSize: 12,
    marginLeft: 8,
  },
  detalhes: {
    paddingHorizontal: 16,
    paddingBottom: 16,
  },
  divisor: {
    height: 1,
    backgroundColor: '#0f3460',
    marginBottom: 14,
  },
  gradeDetalhes: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 12,
    marginBottom: 16,
  },
  itemDetalhe: {
    width: '45%',
  },
  labelDetalhe: {
    fontSize: 11,
    color: '#777',
    marginBottom: 2,
    fontWeight: '500',
  },
  valorDetalhe: {
    fontSize: 14,
    color: '#ddd',
    fontWeight: '500',
  },
  containerAcoes: {
    flexDirection: 'row',
    gap: 10,
  },
  botaoEditar: {
    flex: 1,
    backgroundColor: '#0f3460',
    borderRadius: 10,
    paddingVertical: 10,
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#1a5276',
  },
  textoBotaoEditar: {
    color: '#7eb8f7',
    fontSize: 14,
    fontWeight: 'bold',
  },
  botaoExcluir: {
    flex: 1,
    backgroundColor: '#3d0c1a',
    borderRadius: 10,
    paddingVertical: 10,
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#e94560',
  },
  textoBotaoExcluir: {
    color: '#e94560',
    fontSize: 14,
    fontWeight: 'bold',
  },
});
