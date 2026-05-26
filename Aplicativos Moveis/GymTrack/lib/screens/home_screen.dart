import 'package:flutter/material.dart';
import '../database/database.dart';
import 'add_treino_screen.dart';
import 'treino_details_screen.dart';

class HomeScreen extends StatelessWidget {
  final AppDatabase db;
  const HomeScreen({super.key, required this.db});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('GymTrack - Meus Treinos')),
      body: StreamBuilder<List<Treino>>(
        stream: db.watchAllTreinos(),
        builder: (context, snapshot) {
          if (!snapshot.hasData) {
            return const Center(child: CircularProgressIndicator());
          }
          final treinos = snapshot.data!;
          
          if (treinos.isEmpty) {
            return const Center(child: Text('Nenhum treino cadastrado. Adicione um novo treino!'));
          }

          return ListView.builder(
            itemCount: treinos.length,
            itemBuilder: (context, index) {
              final treino = treinos[index];
              return Card(
                margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                child: ListTile(
                  leading: const Icon(Icons.fitness_center),
                  title: Text(treino.nome, style: const TextStyle(fontWeight: FontWeight.bold)),
                  subtitle: Text('Dia: ${treino.diaSemana}'),
                  trailing: Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      const Text('Concluído'),
                      Checkbox(
                        value: treino.concluido,
                        onChanged: (value) {
                          db.updateTreino(treino.copyWith(concluido: value ?? false));
                        },
                      ),
                      IconButton(
                        icon: const Icon(Icons.delete, color: Colors.red),
                        onPressed: () => db.deleteTreino(treino),
                      )
                    ],
                  ),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => TreinoDetailsScreen(db: db, treino: treino),
                      ),
                    );
                  },
                ),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => AddTreinoScreen(db: db)),
          );
        },
        icon: const Icon(Icons.add),
        label: const Text('Novo Treino'),
      ),
    );
  }
}
