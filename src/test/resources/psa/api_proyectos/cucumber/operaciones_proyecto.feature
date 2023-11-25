Feature: Operaciones de proyectos
  Revisión de comportamiento del servicio de proyectos

  Scenario: Creación correcta de un proyecto
    When se intenta crear un proyecto con todos los campos asignados correctamente
    Then El Proyecto se crea correctamente

  Scenario: Creación correcta de un proyecto sin definir las fechas
    When se intenta crear un proyecto con todos los campos asignados correctamente menos las fechas
    Then El Proyecto se crea correctamente

  Scenario: Creación incorrecta de un proyecto por falta de nombre
    When se intenta crear un proyecto sin indicar el nombre correctamente
    Then El proyecto no es creado, y se informa del error

  Scenario: Creación incorrecta de un proyecto por falta de descripción
    When se intenta crear un proyecto sin indicar la descripción correctamente
    Then El proyecto no es creado, y se informa del error

  Scenario: Creación incorrecta de un proyecto por falta de estado
    When se intenta crear un proyecto sin indicar un estado correctamente
    Then El proyecto no es creado, y se informa del error

  Scenario: Creación incorrecta de un proyecto por poner una fecha final anterior a la fecha de inicio
    When se intenta crear un proyecto con una fecha final anterior a su fecha de inicio
    Then El proyecto no es creado, y se informa del error