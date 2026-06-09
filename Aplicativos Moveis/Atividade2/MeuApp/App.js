import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  Button,
  ScrollView,
  StyleSheet,
  TouchableOpacity,
  Alert,
} from 'react-native';
import Checkbox from 'expo-checkbox';
import { MaskedTextInput } from 'react-native-mask-text';

// ─────────────────────────────────────────────
// Componente: Radio Button personalizado
// ─────────────────────────────────────────────
function RadioButton({ label, selected, onPress }) {
  return (
    <TouchableOpacity style={styles.radioRow} onPress={onPress} activeOpacity={0.7}>
      <View style={[styles.radioCircle, selected && styles.radioCircleSelected]}>
        {selected && <View style={styles.radioInner} />}
      </View>
      <Text style={styles.radioLabel}>{label}</Text>
    </TouchableOpacity>
  );
}

// ─────────────────────────────────────────────
// App principal
// ─────────────────────────────────────────────
export default function App() {
  // ── Campos do formulário ──
  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [telefone, setTelefone] = useState('');
  const [endereco, setEndereco] = useState('');

  // ── Sexo (radio) ──
  const [sexo, setSexo] = useState(''); // 'Masculino' | 'Feminino'

  // ── Checkboxes ──
  const [aceitaTermos, setAceitaTermos] = useState(false);
  const [receberEmail, setReceberEmail] = useState(false);

  // ── Lista de contatos ──
  const [contatos, setContatos] = useState([]);
  const [proximoId, setProximoId] = useState(1);

  // ─────────────────────────────────────────────
  // Função: Adicionar contato
  // ─────────────────────────────────────────────
  function adicionarContato() {
    // Validações obrigatórias
    if (nome.trim() === '') {
      Alert.alert('Campo obrigatório', 'Por favor, informe o nome.');
      return;
    }
    if (cpf.trim() === '') {
      Alert.alert('Campo obrigatório', 'Por favor, informe o CPF.');
      return;
    }
    if (telefone.trim() === '') {
      Alert.alert('Campo obrigatório', 'Por favor, informe o telefone.');
      return;
    }
    if (endereco.trim() === '') {
      Alert.alert('Campo obrigatório', 'Por favor, informe o endereço.');
      return;
    }
    if (sexo === '') {
      Alert.alert('Campo obrigatório', 'Por favor, selecione o sexo.');
      return;
    }
    if (!aceitaTermos) {
      Alert.alert('Termos obrigatórios', 'Você deve aceitar os Termos para continuar.');
      return;
    }

    // Cria o novo contato
    const novoContato = {
      id: proximoId,
      nome,
      cpf,
      telefone,
      endereco,
      sexo,
      aceitaTermos,
      receberEmail,
    };

    setContatos(contatos.concat(novoContato));
    setProximoId(proximoId + 1);

    // Limpa o formulário
    setNome('');
    setCpf('');
    setTelefone('');
    setEndereco('');
    setSexo('');
    setAceitaTermos(false);
    setReceberEmail(false);
  }

  // ─────────────────────────────────────────────
  // Render
  // ─────────────────────────────────────────────
  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>

      {/* ── Título ── */}
      <Text style={styles.titulo}>📋 Cadastro de Contatos</Text>

      {/* ── Formulário ── */}
      <View style={styles.card}>
        <Text style={styles.sectionTitle}>Informações Pessoais</Text>

        {/* Nome */}
        <Text style={styles.label}>Nome *</Text>
        <TextInput
          style={styles.input}
          value={nome}
          onChangeText={setNome}
          placeholder="Digite o nome completo"
          placeholderTextColor="#999"
        />

        {/* CPF com máscara */}
        <Text style={styles.label}>CPF *</Text>
        <MaskedTextInput
          mask="999.999.999-99"
          style={styles.input}
          value={cpf}
          onChangeText={(text) => setCpf(text)}
          placeholder="000.000.000-00"
          placeholderTextColor="#999"
          keyboardType="numeric"
        />

        {/* Telefone com máscara */}
        <Text style={styles.label}>Telefone *</Text>
        <MaskedTextInput
          mask="(99) 99999-9999"
          style={styles.input}
          value={telefone}
          onChangeText={(text) => setTelefone(text)}
          placeholder="(00) 00000-0000"
          placeholderTextColor="#999"
          keyboardType="phone-pad"
        />

        {/* Endereço */}
        <Text style={styles.label}>Endereço *</Text>
        <TextInput
          style={styles.input}
          value={endereco}
          onChangeText={setEndereco}
          placeholder="Rua, número, bairro, cidade"
          placeholderTextColor="#999"
        />

        {/* ── Sexo (Radio Button) ── */}
        <Text style={styles.label}>Sexo *</Text>
        <View style={styles.radioGroup}>
          <RadioButton
            label="Masculino"
            selected={sexo === 'Masculino'}
            onPress={() => setSexo('Masculino')}
          />
          <RadioButton
            label="Feminino"
            selected={sexo === 'Feminino'}
            onPress={() => setSexo('Feminino')}
          />
        </View>

        {/* ── CheckBoxes ── */}
        <Text style={styles.label}>Termos e Preferências</Text>

        <TouchableOpacity
          style={styles.checkboxRow}
          onPress={() => setAceitaTermos(!aceitaTermos)}
          activeOpacity={0.7}
        >
          <Checkbox
            value={aceitaTermos}
            onValueChange={setAceitaTermos}
            color={aceitaTermos ? '#4A90D9' : undefined}
          />
          <Text style={styles.checkboxLabel}>Aceito os Termos de Uso *</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.checkboxRow}
          onPress={() => setReceberEmail(!receberEmail)}
          activeOpacity={0.7}
        >
          <Checkbox
            value={receberEmail}
            onValueChange={setReceberEmail}
            color={receberEmail ? '#4A90D9' : undefined}
          />
          <Text style={styles.checkboxLabel}>Quero receber informações por e-mail</Text>
        </TouchableOpacity>

        {/* ── Botão Salvar ── */}
        <View style={styles.botaoContainer}>
          <Button title="💾  Salvar Contato" onPress={adicionarContato} color="#4A90D9" />
        </View>
      </View>

      {/* ── Lista de Contatos ── */}
      {contatos.length > 0 && (
        <View style={styles.card}>
          <Text style={styles.sectionTitle}>Lista de Contatos ({contatos.length})</Text>

          {contatos.map((contato) => (
            <View key={contato.id} style={styles.contatoItem}>
              <Text style={styles.contatoNome}>👤 {contato.nome}</Text>
              <Text style={styles.contatoDetalhe}>🪪 CPF: {contato.cpf}</Text>
              <Text style={styles.contatoDetalhe}>📞 Telefone: {contato.telefone}</Text>
              <Text style={styles.contatoDetalhe}>🏠 Endereço: {contato.endereco}</Text>
              <Text style={styles.contatoDetalhe}>
                {contato.sexo === 'Masculino' ? '♂️' : '♀️'} Sexo: {contato.sexo}
              </Text>
              <Text style={styles.contatoDetalhe}>
                ✅ Termos aceitos: {contato.aceitaTermos ? 'Sim' : 'Não'}
              </Text>
              <Text style={styles.contatoDetalhe}>
                📧 Receber e-mail: {contato.receberEmail ? 'Sim' : 'Não'}
              </Text>
            </View>
          ))}
        </View>
      )}

      {contatos.length === 0 && (
        <View style={styles.emptyContainer}>
          <Text style={styles.emptyText}>Nenhum contato cadastrado ainda.</Text>
          <Text style={styles.emptyText}>Preencha o formulário acima e clique em Salvar!</Text>
        </View>
      )}

    </ScrollView>
  );
}

// ─────────────────────────────────────────────
// Estilos
// ─────────────────────────────────────────────
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F0F4F8',
  },
  content: {
    padding: 16,
    paddingBottom: 40,
  },
  titulo: {
    fontSize: 26,
    fontWeight: 'bold',
    color: '#1A2F4E',
    textAlign: 'center',
    marginTop: 40,
    marginBottom: 20,
  },
  card: {
    backgroundColor: '#FFFFFF',
    borderRadius: 12,
    padding: 16,
    marginBottom: 20,
    // Sombra Android
    elevation: 4,
    // Sombra iOS
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.12,
    shadowRadius: 4,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: '700',
    color: '#4A90D9',
    marginBottom: 14,
    borderBottomWidth: 1,
    borderBottomColor: '#E8EDF2',
    paddingBottom: 8,
  },
  label: {
    fontSize: 14,
    fontWeight: '600',
    color: '#444',
    marginTop: 10,
    marginBottom: 4,
  },
  input: {
    borderWidth: 1,
    borderColor: '#D1D9E0',
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    fontSize: 15,
    color: '#222',
    backgroundColor: '#FAFBFC',
  },
  // ── Radio Button ──
  radioGroup: {
    flexDirection: 'row',
    gap: 20,
    marginTop: 4,
    marginBottom: 6,
  },
  radioRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  radioCircle: {
    width: 22,
    height: 22,
    borderRadius: 11,
    borderWidth: 2,
    borderColor: '#4A90D9',
    justifyContent: 'center',
    alignItems: 'center',
  },
  radioCircleSelected: {
    borderColor: '#4A90D9',
  },
  radioInner: {
    width: 11,
    height: 11,
    borderRadius: 6,
    backgroundColor: '#4A90D9',
  },
  radioLabel: {
    fontSize: 15,
    color: '#333',
  },
  // ── Checkbox ──
  checkboxRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 10,
    marginTop: 10,
  },
  checkboxLabel: {
    fontSize: 14,
    color: '#444',
    flexShrink: 1,
  },
  // ── Botão ──
  botaoContainer: {
    marginTop: 20,
    borderRadius: 8,
    overflow: 'hidden',
  },
  // ── Lista de contatos ──
  contatoItem: {
    backgroundColor: '#F7F9FB',
    borderRadius: 10,
    padding: 14,
    marginBottom: 12,
    borderLeftWidth: 4,
    borderLeftColor: '#4A90D9',
  },
  contatoNome: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#1A2F4E',
    marginBottom: 6,
  },
  contatoDetalhe: {
    fontSize: 13,
    color: '#555',
    marginBottom: 2,
  },
  // ── Estado vazio ──
  emptyContainer: {
    alignItems: 'center',
    marginTop: 10,
    padding: 20,
  },
  emptyText: {
    fontSize: 14,
    color: '#999',
    textAlign: 'center',
    lineHeight: 22,
  },
});
