import 'package:flutter/material.dart';
import 'package:drift/drift.dart' as drift;
import '../database/database.dart';

class AddTreinoScreen extends StatefulWidget {
  final AppDatabase db;
  const AddTreinoScreen({super.key, required this.db});

  @override
  State<AddTreinoScreen> createState() => _AddTreinoScreenState();
}

class _AddTreinoScreenState extends State<AddTreinoScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nomeController = TextEditingController();
  String _diaSemana = 'Segunda-feira';
  
  final List<String> _dias = [
    'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado', 'Domingo'
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Cadastrar Treino')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                controller: _nomeController,
                decoration: const InputDecoration(labelText: 'Nome do Treino', border: OutlineInputBorder()),
                validator: (value) => value == null || value.isEmpty ? 'Informe o nome' : null,
              ),
              const SizedBox(height: 16),
              DropdownButtonFormField<String>(
                value: _diaSemana,
                decoration: const InputDecoration(labelText: 'Dia da Semana', border: OutlineInputBorder()),
                items: _dias.map((dia) => DropdownMenuItem(value: dia, child: Text(dia))).toList(),
                onChanged: (value) {
                  setState(() => _diaSemana = value!);
                },
              ),
              const Spacer(),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  OutlinedButton(
                    onPressed: () => Navigator.pop(context),
                    child: const Text('Cancelar'),
                  ),
                  FilledButton(
                    onPressed: () async {
                      if (_formKey.currentState!.validate()) {
                        await widget.db.insertTreino(
                          TreinosCompanion(
                            nome: drift.Value(_nomeController.text),
                            diaSemana: drift.Value(_diaSemana),
                          ),
                        );
                        if (context.mounted) Navigator.pop(context);
                      }
                    },
                    child: const Text('Salvar'),
                  ),
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
