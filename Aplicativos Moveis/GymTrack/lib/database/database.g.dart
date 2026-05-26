// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'database.dart';

// ignore_for_file: type=lint
class $TreinosTable extends Treinos with TableInfo<$TreinosTable, Treino> {
  @override
  final GeneratedDatabase attachedDatabase;
  final String? _alias;
  $TreinosTable(this.attachedDatabase, [this._alias]);
  static const VerificationMeta _idMeta = const VerificationMeta('id');
  @override
  late final GeneratedColumn<int> id = GeneratedColumn<int>(
      'id', aliasedName, false,
      hasAutoIncrement: true,
      type: DriftSqlType.int,
      requiredDuringInsert: false,
      defaultConstraints:
          GeneratedColumn.constraintIsAlways('PRIMARY KEY AUTOINCREMENT'));
  static const VerificationMeta _nomeMeta = const VerificationMeta('nome');
  @override
  late final GeneratedColumn<String> nome = GeneratedColumn<String>(
      'nome', aliasedName, false,
      additionalChecks:
          GeneratedColumn.checkTextLength(minTextLength: 1, maxTextLength: 100),
      type: DriftSqlType.string,
      requiredDuringInsert: true);
  static const VerificationMeta _diaSemanaMeta =
      const VerificationMeta('diaSemana');
  @override
  late final GeneratedColumn<String> diaSemana = GeneratedColumn<String>(
      'dia_semana', aliasedName, false,
      additionalChecks:
          GeneratedColumn.checkTextLength(minTextLength: 1, maxTextLength: 20),
      type: DriftSqlType.string,
      requiredDuringInsert: true);
  static const VerificationMeta _concluidoMeta =
      const VerificationMeta('concluido');
  @override
  late final GeneratedColumn<bool> concluido = GeneratedColumn<bool>(
      'concluido', aliasedName, false,
      type: DriftSqlType.bool,
      requiredDuringInsert: false,
      defaultConstraints:
          GeneratedColumn.constraintIsAlways('CHECK ("concluido" IN (0, 1))'),
      defaultValue: const Constant(false));
  @override
  List<GeneratedColumn> get $columns => [id, nome, diaSemana, concluido];
  @override
  String get aliasedName => _alias ?? actualTableName;
  @override
  String get actualTableName => $name;
  static const String $name = 'treinos';
  @override
  VerificationContext validateIntegrity(Insertable<Treino> instance,
      {bool isInserting = false}) {
    final context = VerificationContext();
    final data = instance.toColumns(true);
    if (data.containsKey('id')) {
      context.handle(_idMeta, id.isAcceptableOrUnknown(data['id']!, _idMeta));
    }
    if (data.containsKey('nome')) {
      context.handle(
          _nomeMeta, nome.isAcceptableOrUnknown(data['nome']!, _nomeMeta));
    } else if (isInserting) {
      context.missing(_nomeMeta);
    }
    if (data.containsKey('dia_semana')) {
      context.handle(_diaSemanaMeta,
          diaSemana.isAcceptableOrUnknown(data['dia_semana']!, _diaSemanaMeta));
    } else if (isInserting) {
      context.missing(_diaSemanaMeta);
    }
    if (data.containsKey('concluido')) {
      context.handle(_concluidoMeta,
          concluido.isAcceptableOrUnknown(data['concluido']!, _concluidoMeta));
    }
    return context;
  }

  @override
  Set<GeneratedColumn> get $primaryKey => {id};
  @override
  Treino map(Map<String, dynamic> data, {String? tablePrefix}) {
    final effectivePrefix = tablePrefix != null ? '$tablePrefix.' : '';
    return Treino(
      id: attachedDatabase.typeMapping
          .read(DriftSqlType.int, data['${effectivePrefix}id'])!,
      nome: attachedDatabase.typeMapping
          .read(DriftSqlType.string, data['${effectivePrefix}nome'])!,
      diaSemana: attachedDatabase.typeMapping
          .read(DriftSqlType.string, data['${effectivePrefix}dia_semana'])!,
      concluido: attachedDatabase.typeMapping
          .read(DriftSqlType.bool, data['${effectivePrefix}concluido'])!,
    );
  }

  @override
  $TreinosTable createAlias(String alias) {
    return $TreinosTable(attachedDatabase, alias);
  }
}

class Treino extends DataClass implements Insertable<Treino> {
  final int id;
  final String nome;
  final String diaSemana;
  final bool concluido;
  const Treino(
      {required this.id,
      required this.nome,
      required this.diaSemana,
      required this.concluido});
  @override
  Map<String, Expression> toColumns(bool nullToAbsent) {
    final map = <String, Expression>{};
    map['id'] = Variable<int>(id);
    map['nome'] = Variable<String>(nome);
    map['dia_semana'] = Variable<String>(diaSemana);
    map['concluido'] = Variable<bool>(concluido);
    return map;
  }

  TreinosCompanion toCompanion(bool nullToAbsent) {
    return TreinosCompanion(
      id: Value(id),
      nome: Value(nome),
      diaSemana: Value(diaSemana),
      concluido: Value(concluido),
    );
  }

  factory Treino.fromJson(Map<String, dynamic> json,
      {ValueSerializer? serializer}) {
    serializer ??= driftRuntimeOptions.defaultSerializer;
    return Treino(
      id: serializer.fromJson<int>(json['id']),
      nome: serializer.fromJson<String>(json['nome']),
      diaSemana: serializer.fromJson<String>(json['diaSemana']),
      concluido: serializer.fromJson<bool>(json['concluido']),
    );
  }
  @override
  Map<String, dynamic> toJson({ValueSerializer? serializer}) {
    serializer ??= driftRuntimeOptions.defaultSerializer;
    return <String, dynamic>{
      'id': serializer.toJson<int>(id),
      'nome': serializer.toJson<String>(nome),
      'diaSemana': serializer.toJson<String>(diaSemana),
      'concluido': serializer.toJson<bool>(concluido),
    };
  }

  Treino copyWith(
          {int? id, String? nome, String? diaSemana, bool? concluido}) =>
      Treino(
        id: id ?? this.id,
        nome: nome ?? this.nome,
        diaSemana: diaSemana ?? this.diaSemana,
        concluido: concluido ?? this.concluido,
      );
  @override
  String toString() {
    return (StringBuffer('Treino(')
          ..write('id: $id, ')
          ..write('nome: $nome, ')
          ..write('diaSemana: $diaSemana, ')
          ..write('concluido: $concluido')
          ..write(')'))
        .toString();
  }

  @override
  int get hashCode => Object.hash(id, nome, diaSemana, concluido);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (other is Treino &&
          other.id == this.id &&
          other.nome == this.nome &&
          other.diaSemana == this.diaSemana &&
          other.concluido == this.concluido);
}

class TreinosCompanion extends UpdateCompanion<Treino> {
  final Value<int> id;
  final Value<String> nome;
  final Value<String> diaSemana;
  final Value<bool> concluido;
  const TreinosCompanion({
    this.id = const Value.absent(),
    this.nome = const Value.absent(),
    this.diaSemana = const Value.absent(),
    this.concluido = const Value.absent(),
  });
  TreinosCompanion.insert({
    this.id = const Value.absent(),
    required String nome,
    required String diaSemana,
    this.concluido = const Value.absent(),
  })  : nome = Value(nome),
        diaSemana = Value(diaSemana);
  static Insertable<Treino> custom({
    Expression<int>? id,
    Expression<String>? nome,
    Expression<String>? diaSemana,
    Expression<bool>? concluido,
  }) {
    return RawValuesInsertable({
      if (id != null) 'id': id,
      if (nome != null) 'nome': nome,
      if (diaSemana != null) 'dia_semana': diaSemana,
      if (concluido != null) 'concluido': concluido,
    });
  }

  TreinosCompanion copyWith(
      {Value<int>? id,
      Value<String>? nome,
      Value<String>? diaSemana,
      Value<bool>? concluido}) {
    return TreinosCompanion(
      id: id ?? this.id,
      nome: nome ?? this.nome,
      diaSemana: diaSemana ?? this.diaSemana,
      concluido: concluido ?? this.concluido,
    );
  }

  @override
  Map<String, Expression> toColumns(bool nullToAbsent) {
    final map = <String, Expression>{};
    if (id.present) {
      map['id'] = Variable<int>(id.value);
    }
    if (nome.present) {
      map['nome'] = Variable<String>(nome.value);
    }
    if (diaSemana.present) {
      map['dia_semana'] = Variable<String>(diaSemana.value);
    }
    if (concluido.present) {
      map['concluido'] = Variable<bool>(concluido.value);
    }
    return map;
  }

  @override
  String toString() {
    return (StringBuffer('TreinosCompanion(')
          ..write('id: $id, ')
          ..write('nome: $nome, ')
          ..write('diaSemana: $diaSemana, ')
          ..write('concluido: $concluido')
          ..write(')'))
        .toString();
  }
}

class $ExerciciosTable extends Exercicios
    with TableInfo<$ExerciciosTable, Exercicio> {
  @override
  final GeneratedDatabase attachedDatabase;
  final String? _alias;
  $ExerciciosTable(this.attachedDatabase, [this._alias]);
  static const VerificationMeta _idMeta = const VerificationMeta('id');
  @override
  late final GeneratedColumn<int> id = GeneratedColumn<int>(
      'id', aliasedName, false,
      hasAutoIncrement: true,
      type: DriftSqlType.int,
      requiredDuringInsert: false,
      defaultConstraints:
          GeneratedColumn.constraintIsAlways('PRIMARY KEY AUTOINCREMENT'));
  static const VerificationMeta _nomeMeta = const VerificationMeta('nome');
  @override
  late final GeneratedColumn<String> nome = GeneratedColumn<String>(
      'nome', aliasedName, false,
      additionalChecks:
          GeneratedColumn.checkTextLength(minTextLength: 1, maxTextLength: 100),
      type: DriftSqlType.string,
      requiredDuringInsert: true);
  static const VerificationMeta _seriesMeta = const VerificationMeta('series');
  @override
  late final GeneratedColumn<int> series = GeneratedColumn<int>(
      'series', aliasedName, false,
      type: DriftSqlType.int, requiredDuringInsert: true);
  static const VerificationMeta _repeticoesMeta =
      const VerificationMeta('repeticoes');
  @override
  late final GeneratedColumn<int> repeticoes = GeneratedColumn<int>(
      'repeticoes', aliasedName, false,
      type: DriftSqlType.int, requiredDuringInsert: true);
  static const VerificationMeta _pesoMeta = const VerificationMeta('peso');
  @override
  late final GeneratedColumn<double> peso = GeneratedColumn<double>(
      'peso', aliasedName, false,
      type: DriftSqlType.double, requiredDuringInsert: true);
  static const VerificationMeta _treinoIdMeta =
      const VerificationMeta('treinoId');
  @override
  late final GeneratedColumn<int> treinoId = GeneratedColumn<int>(
      'treino_id', aliasedName, false,
      type: DriftSqlType.int,
      requiredDuringInsert: true,
      defaultConstraints: GeneratedColumn.constraintIsAlways(
          'REFERENCES treinos (id) ON DELETE CASCADE'));
  @override
  List<GeneratedColumn> get $columns =>
      [id, nome, series, repeticoes, peso, treinoId];
  @override
  String get aliasedName => _alias ?? actualTableName;
  @override
  String get actualTableName => $name;
  static const String $name = 'exercicios';
  @override
  VerificationContext validateIntegrity(Insertable<Exercicio> instance,
      {bool isInserting = false}) {
    final context = VerificationContext();
    final data = instance.toColumns(true);
    if (data.containsKey('id')) {
      context.handle(_idMeta, id.isAcceptableOrUnknown(data['id']!, _idMeta));
    }
    if (data.containsKey('nome')) {
      context.handle(
          _nomeMeta, nome.isAcceptableOrUnknown(data['nome']!, _nomeMeta));
    } else if (isInserting) {
      context.missing(_nomeMeta);
    }
    if (data.containsKey('series')) {
      context.handle(_seriesMeta,
          series.isAcceptableOrUnknown(data['series']!, _seriesMeta));
    } else if (isInserting) {
      context.missing(_seriesMeta);
    }
    if (data.containsKey('repeticoes')) {
      context.handle(
          _repeticoesMeta,
          repeticoes.isAcceptableOrUnknown(
              data['repeticoes']!, _repeticoesMeta));
    } else if (isInserting) {
      context.missing(_repeticoesMeta);
    }
    if (data.containsKey('peso')) {
      context.handle(
          _pesoMeta, peso.isAcceptableOrUnknown(data['peso']!, _pesoMeta));
    } else if (isInserting) {
      context.missing(_pesoMeta);
    }
    if (data.containsKey('treino_id')) {
      context.handle(_treinoIdMeta,
          treinoId.isAcceptableOrUnknown(data['treino_id']!, _treinoIdMeta));
    } else if (isInserting) {
      context.missing(_treinoIdMeta);
    }
    return context;
  }

  @override
  Set<GeneratedColumn> get $primaryKey => {id};
  @override
  Exercicio map(Map<String, dynamic> data, {String? tablePrefix}) {
    final effectivePrefix = tablePrefix != null ? '$tablePrefix.' : '';
    return Exercicio(
      id: attachedDatabase.typeMapping
          .read(DriftSqlType.int, data['${effectivePrefix}id'])!,
      nome: attachedDatabase.typeMapping
          .read(DriftSqlType.string, data['${effectivePrefix}nome'])!,
      series: attachedDatabase.typeMapping
          .read(DriftSqlType.int, data['${effectivePrefix}series'])!,
      repeticoes: attachedDatabase.typeMapping
          .read(DriftSqlType.int, data['${effectivePrefix}repeticoes'])!,
      peso: attachedDatabase.typeMapping
          .read(DriftSqlType.double, data['${effectivePrefix}peso'])!,
      treinoId: attachedDatabase.typeMapping
          .read(DriftSqlType.int, data['${effectivePrefix}treino_id'])!,
    );
  }

  @override
  $ExerciciosTable createAlias(String alias) {
    return $ExerciciosTable(attachedDatabase, alias);
  }
}

class Exercicio extends DataClass implements Insertable<Exercicio> {
  final int id;
  final String nome;
  final int series;
  final int repeticoes;
  final double peso;
  final int treinoId;
  const Exercicio(
      {required this.id,
      required this.nome,
      required this.series,
      required this.repeticoes,
      required this.peso,
      required this.treinoId});
  @override
  Map<String, Expression> toColumns(bool nullToAbsent) {
    final map = <String, Expression>{};
    map['id'] = Variable<int>(id);
    map['nome'] = Variable<String>(nome);
    map['series'] = Variable<int>(series);
    map['repeticoes'] = Variable<int>(repeticoes);
    map['peso'] = Variable<double>(peso);
    map['treino_id'] = Variable<int>(treinoId);
    return map;
  }

  ExerciciosCompanion toCompanion(bool nullToAbsent) {
    return ExerciciosCompanion(
      id: Value(id),
      nome: Value(nome),
      series: Value(series),
      repeticoes: Value(repeticoes),
      peso: Value(peso),
      treinoId: Value(treinoId),
    );
  }

  factory Exercicio.fromJson(Map<String, dynamic> json,
      {ValueSerializer? serializer}) {
    serializer ??= driftRuntimeOptions.defaultSerializer;
    return Exercicio(
      id: serializer.fromJson<int>(json['id']),
      nome: serializer.fromJson<String>(json['nome']),
      series: serializer.fromJson<int>(json['series']),
      repeticoes: serializer.fromJson<int>(json['repeticoes']),
      peso: serializer.fromJson<double>(json['peso']),
      treinoId: serializer.fromJson<int>(json['treinoId']),
    );
  }
  @override
  Map<String, dynamic> toJson({ValueSerializer? serializer}) {
    serializer ??= driftRuntimeOptions.defaultSerializer;
    return <String, dynamic>{
      'id': serializer.toJson<int>(id),
      'nome': serializer.toJson<String>(nome),
      'series': serializer.toJson<int>(series),
      'repeticoes': serializer.toJson<int>(repeticoes),
      'peso': serializer.toJson<double>(peso),
      'treinoId': serializer.toJson<int>(treinoId),
    };
  }

  Exercicio copyWith(
          {int? id,
          String? nome,
          int? series,
          int? repeticoes,
          double? peso,
          int? treinoId}) =>
      Exercicio(
        id: id ?? this.id,
        nome: nome ?? this.nome,
        series: series ?? this.series,
        repeticoes: repeticoes ?? this.repeticoes,
        peso: peso ?? this.peso,
        treinoId: treinoId ?? this.treinoId,
      );
  @override
  String toString() {
    return (StringBuffer('Exercicio(')
          ..write('id: $id, ')
          ..write('nome: $nome, ')
          ..write('series: $series, ')
          ..write('repeticoes: $repeticoes, ')
          ..write('peso: $peso, ')
          ..write('treinoId: $treinoId')
          ..write(')'))
        .toString();
  }

  @override
  int get hashCode => Object.hash(id, nome, series, repeticoes, peso, treinoId);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (other is Exercicio &&
          other.id == this.id &&
          other.nome == this.nome &&
          other.series == this.series &&
          other.repeticoes == this.repeticoes &&
          other.peso == this.peso &&
          other.treinoId == this.treinoId);
}

class ExerciciosCompanion extends UpdateCompanion<Exercicio> {
  final Value<int> id;
  final Value<String> nome;
  final Value<int> series;
  final Value<int> repeticoes;
  final Value<double> peso;
  final Value<int> treinoId;
  const ExerciciosCompanion({
    this.id = const Value.absent(),
    this.nome = const Value.absent(),
    this.series = const Value.absent(),
    this.repeticoes = const Value.absent(),
    this.peso = const Value.absent(),
    this.treinoId = const Value.absent(),
  });
  ExerciciosCompanion.insert({
    this.id = const Value.absent(),
    required String nome,
    required int series,
    required int repeticoes,
    required double peso,
    required int treinoId,
  })  : nome = Value(nome),
        series = Value(series),
        repeticoes = Value(repeticoes),
        peso = Value(peso),
        treinoId = Value(treinoId);
  static Insertable<Exercicio> custom({
    Expression<int>? id,
    Expression<String>? nome,
    Expression<int>? series,
    Expression<int>? repeticoes,
    Expression<double>? peso,
    Expression<int>? treinoId,
  }) {
    return RawValuesInsertable({
      if (id != null) 'id': id,
      if (nome != null) 'nome': nome,
      if (series != null) 'series': series,
      if (repeticoes != null) 'repeticoes': repeticoes,
      if (peso != null) 'peso': peso,
      if (treinoId != null) 'treino_id': treinoId,
    });
  }

  ExerciciosCompanion copyWith(
      {Value<int>? id,
      Value<String>? nome,
      Value<int>? series,
      Value<int>? repeticoes,
      Value<double>? peso,
      Value<int>? treinoId}) {
    return ExerciciosCompanion(
      id: id ?? this.id,
      nome: nome ?? this.nome,
      series: series ?? this.series,
      repeticoes: repeticoes ?? this.repeticoes,
      peso: peso ?? this.peso,
      treinoId: treinoId ?? this.treinoId,
    );
  }

  @override
  Map<String, Expression> toColumns(bool nullToAbsent) {
    final map = <String, Expression>{};
    if (id.present) {
      map['id'] = Variable<int>(id.value);
    }
    if (nome.present) {
      map['nome'] = Variable<String>(nome.value);
    }
    if (series.present) {
      map['series'] = Variable<int>(series.value);
    }
    if (repeticoes.present) {
      map['repeticoes'] = Variable<int>(repeticoes.value);
    }
    if (peso.present) {
      map['peso'] = Variable<double>(peso.value);
    }
    if (treinoId.present) {
      map['treino_id'] = Variable<int>(treinoId.value);
    }
    return map;
  }

  @override
  String toString() {
    return (StringBuffer('ExerciciosCompanion(')
          ..write('id: $id, ')
          ..write('nome: $nome, ')
          ..write('series: $series, ')
          ..write('repeticoes: $repeticoes, ')
          ..write('peso: $peso, ')
          ..write('treinoId: $treinoId')
          ..write(')'))
        .toString();
  }
}

abstract class _$AppDatabase extends GeneratedDatabase {
  _$AppDatabase(QueryExecutor e) : super(e);
  _$AppDatabaseManager get managers => _$AppDatabaseManager(this);
  late final $TreinosTable treinos = $TreinosTable(this);
  late final $ExerciciosTable exercicios = $ExerciciosTable(this);
  @override
  Iterable<TableInfo<Table, Object?>> get allTables =>
      allSchemaEntities.whereType<TableInfo<Table, Object?>>();
  @override
  List<DatabaseSchemaEntity> get allSchemaEntities => [treinos, exercicios];
  @override
  StreamQueryUpdateRules get streamUpdateRules => const StreamQueryUpdateRules(
        [
          WritePropagation(
            on: TableUpdateQuery.onTableName('treinos',
                limitUpdateKind: UpdateKind.delete),
            result: [
              TableUpdate('exercicios', kind: UpdateKind.delete),
            ],
          ),
        ],
      );
}

typedef $$TreinosTableInsertCompanionBuilder = TreinosCompanion Function({
  Value<int> id,
  required String nome,
  required String diaSemana,
  Value<bool> concluido,
});
typedef $$TreinosTableUpdateCompanionBuilder = TreinosCompanion Function({
  Value<int> id,
  Value<String> nome,
  Value<String> diaSemana,
  Value<bool> concluido,
});

class $$TreinosTableTableManager extends RootTableManager<
    _$AppDatabase,
    $TreinosTable,
    Treino,
    $$TreinosTableFilterComposer,
    $$TreinosTableOrderingComposer,
    $$TreinosTableProcessedTableManager,
    $$TreinosTableInsertCompanionBuilder,
    $$TreinosTableUpdateCompanionBuilder> {
  $$TreinosTableTableManager(_$AppDatabase db, $TreinosTable table)
      : super(TableManagerState(
          db: db,
          table: table,
          filteringComposer:
              $$TreinosTableFilterComposer(ComposerState(db, table)),
          orderingComposer:
              $$TreinosTableOrderingComposer(ComposerState(db, table)),
          getChildManagerBuilder: (p) => $$TreinosTableProcessedTableManager(p),
          getUpdateCompanionBuilder: ({
            Value<int> id = const Value.absent(),
            Value<String> nome = const Value.absent(),
            Value<String> diaSemana = const Value.absent(),
            Value<bool> concluido = const Value.absent(),
          }) =>
              TreinosCompanion(
            id: id,
            nome: nome,
            diaSemana: diaSemana,
            concluido: concluido,
          ),
          getInsertCompanionBuilder: ({
            Value<int> id = const Value.absent(),
            required String nome,
            required String diaSemana,
            Value<bool> concluido = const Value.absent(),
          }) =>
              TreinosCompanion.insert(
            id: id,
            nome: nome,
            diaSemana: diaSemana,
            concluido: concluido,
          ),
        ));
}

class $$TreinosTableProcessedTableManager extends ProcessedTableManager<
    _$AppDatabase,
    $TreinosTable,
    Treino,
    $$TreinosTableFilterComposer,
    $$TreinosTableOrderingComposer,
    $$TreinosTableProcessedTableManager,
    $$TreinosTableInsertCompanionBuilder,
    $$TreinosTableUpdateCompanionBuilder> {
  $$TreinosTableProcessedTableManager(super.$state);
}

class $$TreinosTableFilterComposer
    extends FilterComposer<_$AppDatabase, $TreinosTable> {
  $$TreinosTableFilterComposer(super.$state);
  ColumnFilters<int> get id => $state.composableBuilder(
      column: $state.table.id,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  ColumnFilters<String> get nome => $state.composableBuilder(
      column: $state.table.nome,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  ColumnFilters<String> get diaSemana => $state.composableBuilder(
      column: $state.table.diaSemana,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  ColumnFilters<bool> get concluido => $state.composableBuilder(
      column: $state.table.concluido,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  ComposableFilter exerciciosRefs(
      ComposableFilter Function($$ExerciciosTableFilterComposer f) f) {
    final $$ExerciciosTableFilterComposer composer = $state.composerBuilder(
        composer: this,
        getCurrentColumn: (t) => t.id,
        referencedTable: $state.db.exercicios,
        getReferencedColumn: (t) => t.treinoId,
        builder: (joinBuilder, parentComposers) =>
            $$ExerciciosTableFilterComposer(ComposerState($state.db,
                $state.db.exercicios, joinBuilder, parentComposers)));
    return f(composer);
  }
}

class $$TreinosTableOrderingComposer
    extends OrderingComposer<_$AppDatabase, $TreinosTable> {
  $$TreinosTableOrderingComposer(super.$state);
  ColumnOrderings<int> get id => $state.composableBuilder(
      column: $state.table.id,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));

  ColumnOrderings<String> get nome => $state.composableBuilder(
      column: $state.table.nome,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));

  ColumnOrderings<String> get diaSemana => $state.composableBuilder(
      column: $state.table.diaSemana,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));

  ColumnOrderings<bool> get concluido => $state.composableBuilder(
      column: $state.table.concluido,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));
}

typedef $$ExerciciosTableInsertCompanionBuilder = ExerciciosCompanion Function({
  Value<int> id,
  required String nome,
  required int series,
  required int repeticoes,
  required double peso,
  required int treinoId,
});
typedef $$ExerciciosTableUpdateCompanionBuilder = ExerciciosCompanion Function({
  Value<int> id,
  Value<String> nome,
  Value<int> series,
  Value<int> repeticoes,
  Value<double> peso,
  Value<int> treinoId,
});

class $$ExerciciosTableTableManager extends RootTableManager<
    _$AppDatabase,
    $ExerciciosTable,
    Exercicio,
    $$ExerciciosTableFilterComposer,
    $$ExerciciosTableOrderingComposer,
    $$ExerciciosTableProcessedTableManager,
    $$ExerciciosTableInsertCompanionBuilder,
    $$ExerciciosTableUpdateCompanionBuilder> {
  $$ExerciciosTableTableManager(_$AppDatabase db, $ExerciciosTable table)
      : super(TableManagerState(
          db: db,
          table: table,
          filteringComposer:
              $$ExerciciosTableFilterComposer(ComposerState(db, table)),
          orderingComposer:
              $$ExerciciosTableOrderingComposer(ComposerState(db, table)),
          getChildManagerBuilder: (p) =>
              $$ExerciciosTableProcessedTableManager(p),
          getUpdateCompanionBuilder: ({
            Value<int> id = const Value.absent(),
            Value<String> nome = const Value.absent(),
            Value<int> series = const Value.absent(),
            Value<int> repeticoes = const Value.absent(),
            Value<double> peso = const Value.absent(),
            Value<int> treinoId = const Value.absent(),
          }) =>
              ExerciciosCompanion(
            id: id,
            nome: nome,
            series: series,
            repeticoes: repeticoes,
            peso: peso,
            treinoId: treinoId,
          ),
          getInsertCompanionBuilder: ({
            Value<int> id = const Value.absent(),
            required String nome,
            required int series,
            required int repeticoes,
            required double peso,
            required int treinoId,
          }) =>
              ExerciciosCompanion.insert(
            id: id,
            nome: nome,
            series: series,
            repeticoes: repeticoes,
            peso: peso,
            treinoId: treinoId,
          ),
        ));
}

class $$ExerciciosTableProcessedTableManager extends ProcessedTableManager<
    _$AppDatabase,
    $ExerciciosTable,
    Exercicio,
    $$ExerciciosTableFilterComposer,
    $$ExerciciosTableOrderingComposer,
    $$ExerciciosTableProcessedTableManager,
    $$ExerciciosTableInsertCompanionBuilder,
    $$ExerciciosTableUpdateCompanionBuilder> {
  $$ExerciciosTableProcessedTableManager(super.$state);
}

class $$ExerciciosTableFilterComposer
    extends FilterComposer<_$AppDatabase, $ExerciciosTable> {
  $$ExerciciosTableFilterComposer(super.$state);
  ColumnFilters<int> get id => $state.composableBuilder(
      column: $state.table.id,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  ColumnFilters<String> get nome => $state.composableBuilder(
      column: $state.table.nome,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  ColumnFilters<int> get series => $state.composableBuilder(
      column: $state.table.series,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  ColumnFilters<int> get repeticoes => $state.composableBuilder(
      column: $state.table.repeticoes,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  ColumnFilters<double> get peso => $state.composableBuilder(
      column: $state.table.peso,
      builder: (column, joinBuilders) =>
          ColumnFilters(column, joinBuilders: joinBuilders));

  $$TreinosTableFilterComposer get treinoId {
    final $$TreinosTableFilterComposer composer = $state.composerBuilder(
        composer: this,
        getCurrentColumn: (t) => t.treinoId,
        referencedTable: $state.db.treinos,
        getReferencedColumn: (t) => t.id,
        builder: (joinBuilder, parentComposers) => $$TreinosTableFilterComposer(
            ComposerState(
                $state.db, $state.db.treinos, joinBuilder, parentComposers)));
    return composer;
  }
}

class $$ExerciciosTableOrderingComposer
    extends OrderingComposer<_$AppDatabase, $ExerciciosTable> {
  $$ExerciciosTableOrderingComposer(super.$state);
  ColumnOrderings<int> get id => $state.composableBuilder(
      column: $state.table.id,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));

  ColumnOrderings<String> get nome => $state.composableBuilder(
      column: $state.table.nome,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));

  ColumnOrderings<int> get series => $state.composableBuilder(
      column: $state.table.series,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));

  ColumnOrderings<int> get repeticoes => $state.composableBuilder(
      column: $state.table.repeticoes,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));

  ColumnOrderings<double> get peso => $state.composableBuilder(
      column: $state.table.peso,
      builder: (column, joinBuilders) =>
          ColumnOrderings(column, joinBuilders: joinBuilders));

  $$TreinosTableOrderingComposer get treinoId {
    final $$TreinosTableOrderingComposer composer = $state.composerBuilder(
        composer: this,
        getCurrentColumn: (t) => t.treinoId,
        referencedTable: $state.db.treinos,
        getReferencedColumn: (t) => t.id,
        builder: (joinBuilder, parentComposers) =>
            $$TreinosTableOrderingComposer(ComposerState(
                $state.db, $state.db.treinos, joinBuilder, parentComposers)));
    return composer;
  }
}

class _$AppDatabaseManager {
  final _$AppDatabase _db;
  _$AppDatabaseManager(this._db);
  $$TreinosTableTableManager get treinos =>
      $$TreinosTableTableManager(_db, _db.treinos);
  $$ExerciciosTableTableManager get exercicios =>
      $$ExerciciosTableTableManager(_db, _db.exercicios);
}
