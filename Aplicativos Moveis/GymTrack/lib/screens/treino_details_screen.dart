import 'package:flutter/material.dart';
import 'package:drift/drift.dart' as drift;
import '../database/database.dart';

class TreinoDetailsScreen extends StatelessWidget {
  final AppDatabase db;
  final Treino treino;

  const TreinoDetailsScreen({super.key, required this.db, required this.treino});

  void _mostrarDialogExercicio(BuildContext context, {Exercicio? exercicio}) {
    final formKey = GlobalKey<FormState>();
    final nomeController = TextEditingController(text: exercicio?.nome ?? '');
    final seriesController = TextEditingController(text: exercicio?.series.toString() ?? '');
    final repController = TextEditingController(text: exercicio?.repeticoes.toString() ?? '');
    final pesoController = TextEditingController(text: exercicio?.peso.toString() ?? '');

    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(exercicio == null ? 'Adicionar Exercício' : 'Editar Exercício'),
        content: Form(
          key: formKey,
          child: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextFormField(
                  controller: nomeController,
                  decoration: const InputDecoration(labelText: 'Nome do Exercício'),
                  validator: (v) => v!.isEmpty ? 'Obrigatório' : null,
                ),
                TextFormField(
                  controller: seriesController,
                  decoration: const InputDecoration(labelText: 'Séries'),
                  keyboardType: TextInputType.number,
                  validator: (v) => v!.isEmpty ? 'Obrigatório' : null,
                ),
                TextFormField(
                  controller: repController,
                  decoration: const InputDecoration(labelText: 'Repetições'),
                  keyboardType: TextInputType.number,
                  validator: (v) => v!.isEmpty ? 'Obrigatório' : null,
                ),
                TextFormField(
                  controller: pesoController,
                  decoration: const InputDecoration(labelText: 'Peso (kg)'),
                  keyboardType: TextInputType.number,
                  validator: (v) => v!.isEmpty ? 'Obrigatório' : null,
                ),
              ],
            ),
          ),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cancelar'),
          ),
          FilledButton(
            onPressed: () async {
              if (formKey.currentState!.validate()) {
                if (exercicio == null) {
                  await db.insertExercicio(
                    ExerciciosCompanion(
                      nome: drift.Value(nomeController.text),
                      series: drift.Value(int.parse(seriesController.text)),
                      repeticoes: drift.Value(int.parse(repController.text)),
                      peso: drift.Value(double.parse(pesoController.text)),
                      treinoId: drift.Value(treino.id),
                    ),
                  );
                } else {
                  await db.updateExercicio(
                    exercicio.copyWith(
                      nome: nomeController.text,
                      series: int.parse(seriesController.text),
                      repeticoes: int.parse(repController.text),
                      peso: double.parse(pesoController.text),
                    ),
                  );
                }
                if (context.mounted) Navigator.pop(context);
              }
            },
            child: const Text('Salvar'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('${treino.nome} - Exercícios')),
      body: StreamBuilder<List<Exercicio>>(
        stream: db.watchExerciciosPorTreino(treino.id),
        builder: (context, snapshot) {
          if (!snapshot.hasData) return const Center(child: CircularProgressIndicator());
          final exercicios = snapshot.data!;
          
          if (exercicios.isEmpty) return const Center(child: Text('Nenhum exercício cadastrado neste treino.'));

          return ListView.builder(
            itemCount: exercicios.length,
            itemBuilder: (context, index) {
              final ex = exercicios[index];
              return Card(
                margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
                child: ListTile(
                  leading: const CircleAvatar(child: Icon(Icons.fitness_center_outlined)),
                  title: Text(ex.nome, style: const TextStyle(fontWeight: FontWeight.bold)),
                  subtitle: Text('${ex.series} séries de ${ex.repeticoes} reps | Peso: ${ex.peso}kg'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      IconButton(
                        icon: const Icon(Icons.edit, color: Colors.blue),
                        onPressed: () => _mostrarDialogExercicio(context, exercicio: ex),
                      ),
                      IconButton(
                        icon: const Icon(Icons.delete, color: Colors.red),
                        onPressed: () => db.deleteExercicio(ex),
                      ),
                    ],
                  ),
                ),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () => _mostrarDialogExercicio(context),
        icon: const Icon(Icons.add),
        label: const Text('Novo Exercício'),
      ),
    );
  }
}
