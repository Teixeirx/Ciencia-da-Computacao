import 'package:drift/drift.dart';

@DataClassName('Treino')
class Treinos extends Table {
  IntColumn get id => integer().autoIncrement()();
  TextColumn get nome => text().withLength(min: 1, max: 100)();
  TextColumn get diaSemana => text().withLength(min: 1, max: 20)();
  BoolColumn get concluido => boolean().withDefault(const Constant(false))();
}

@DataClassName('Exercicio')
class Exercicios extends Table {
  IntColumn get id => integer().autoIncrement()();
  TextColumn get nome => text().withLength(min: 1, max: 100)();
  IntColumn get series => integer()();
  IntColumn get repeticoes => integer()();
  RealColumn get peso => real()();
  IntColumn get treinoId => integer().references(Treinos, #id, onDelete: KeyAction.cascade)();
}
