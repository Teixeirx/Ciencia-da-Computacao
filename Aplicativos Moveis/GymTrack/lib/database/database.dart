import 'package:drift/drift.dart';
import 'package:drift_flutter/drift_flutter.dart';
import 'tables.dart';

part 'database.g.dart';

@DriftDatabase(tables: [Treinos, Exercicios])
class AppDatabase extends _$AppDatabase {
  AppDatabase() : super(_openConnection());

  static QueryExecutor _openConnection() {
    return driftDatabase(
      name: 'gymtrack',
      web: DriftWebOptions(
        sqlite3Wasm: Uri.parse('sqlite3.wasm'),
        driftWorker: Uri.parse('drift_worker.js'),
      ),
    );
  }

  @override
  int get schemaVersion => 1;

  @override
  MigrationStrategy get migration {
    return MigrationStrategy(
      onCreate: (Migrator m) async {
        await m.createAll();
      },
      beforeOpen: (details) async {
        await customStatement('PRAGMA foreign_keys = ON');
      },
    );
  }

  // DAOs for Treinos
  Future<List<Treino>> getAllTreinos() => select(treinos).get();
  Stream<List<Treino>> watchAllTreinos() => select(treinos).watch();
  Future<int> insertTreino(TreinosCompanion treino) => into(treinos).insert(treino);
  Future<bool> updateTreino(Treino treino) => update(treinos).replace(treino);
  Future<int> deleteTreino(Treino treino) => delete(treinos).delete(treino);

  // DAOs for Exercicios
  Future<List<Exercicio>> getExerciciosPorTreino(int treinoId) {
    return (select(exercicios)..where((e) => e.treinoId.equals(treinoId))).get();
  }
  Stream<List<Exercicio>> watchExerciciosPorTreino(int treinoId) {
    return (select(exercicios)..where((e) => e.treinoId.equals(treinoId))).watch();
  }
  Future<int> insertExercicio(ExerciciosCompanion exercicio) => into(exercicios).insert(exercicio);
  Future<bool> updateExercicio(Exercicio exercicio) => update(exercicios).replace(exercicio);
  Future<int> deleteExercicio(Exercicio exercicio) => delete(exercicios).delete(exercicio);
}
