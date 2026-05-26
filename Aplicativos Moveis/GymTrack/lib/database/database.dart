import 'dart:io';
import 'package:drift/drift.dart';
import 'package:drift/native.dart';
import 'package:path_provider/path_provider.dart';
import 'package:path/path.dart' as p;
import 'package:sqlite3/sqlite3.dart';
import 'package:sqlite3_flutter_libs/sqlite3_flutter_libs.dart';
import 'tables.dart';

part 'database.g.dart';

LazyDatabase _openConnection() {
  return LazyDatabase(() async {
    final dbFolder = await getApplicationDocumentsDirectory();
    final file = File(p.join(dbFolder.path, 'gymtrack.sqlite'));
    
    if (Platform.isAndroid) {
      await applyWorkaroundToOpenSqlite3OnOldAndroidVersions();
    }
    
    final cachebase = (await getTemporaryDirectory()).path;
    sqlite3.tempDirectory = cachebase;
    
    return NativeDatabase.createInBackground(file);
  });
}

@DriftDatabase(tables: [Treinos, Exercicios])
class AppDatabase extends _$AppDatabase {
  AppDatabase() : super(_openConnection());

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
