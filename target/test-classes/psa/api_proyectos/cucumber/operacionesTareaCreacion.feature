Feature: Operaciones de tareas
  Revisión de comportamiento del servicio de tareas

  Scenario: Creación correcta de una tarea
    Given Que existe un proyecto crado y conozco su Id
    When Se intenta crear una tarea con todos los campos asignados correctamente
    Then La tarea se crea correctamente

  Scenario: Creación correcta de una tarea sin definir las fechas
    Given Que existe un proyecto crado y conozco su Id
    When Se intenta crear una tarea con todos los campos asignados correctamente menos las fechas
    Then La tarea se crea correctamente

  Scenario: Creación incorrecta de una tarea por falta de descripción
    Given Que existe un proyecto crado y conozco su Id
    When Se intenta crear una tarea sin indicar la descripción correctamente
    Then La tarea no es creada, y se informa del error

  Scenario: Creación incorrecta de un proyecto por falta de estado
    Given Que existe un proyecto crado y conozco su Id
    When Se intenta crear una tarea sin indicar un estado correctamente
    Then La tarea no es creada, y se informa del error

  Scenario: Creación incorrecta de una tarea por poner una fecha final anterior a la fecha de inicio
    Given Que existe un proyecto crado y conozco su Id
    When se intenta crear una tarea con una fecha final anterior a su fecha de inicio
    Then La tarea no es creada, y se informa del error

    Scenario: Intento crear una tarea para un proyecto que no existe
      Given Que tengo un id de un proyecto que no existe
      When Se intenta crear una tarea para dicho proyecto
      Then La tarea no es creada porque el proyecto no existe