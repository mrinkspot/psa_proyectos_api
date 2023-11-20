Feature: Operaciones de proyectos
  Revisión de comportamiento del servicio de proyectos

  Scenario: Creación correcta de un proyecto
    When se intenta crear crear un proyecto con todos los campos asignados correctamente
    Then El Proyecto se crea correctamente