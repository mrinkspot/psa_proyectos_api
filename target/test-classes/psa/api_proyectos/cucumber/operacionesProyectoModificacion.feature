Feature: Operaciones de modificición de proyecto
  Revisión del comportamiento de la modificación de un proyecto

  Scenario: Modificacion correcta de los campos de un proyecto
    Given Existe un proyecto y se conoce su Id
    When Se le intentan modificar algún campo con un dato válido
    Then El proyecto se actualiza y ahora tiene sus campos modificados

  Scenario: Modificacion incorrecta del nombre de un proyecto
    Given Existe un proyecto y se conoce su Id
    When Se le intentan modificar algún campo con un dato inválido
    Then El proyecto no se actualiza y se recibe una excepción

  Scenario: Eliminacion de un proyecto ya creado
    Given Existe un proyecto y se conoce su Id
    When Se le intenta eliminar
    Then El proyecto es eliminado y ya no puede ser obtenido

    Scenario: Eliminacion en cascada de las tareas incluidas en un proyecto cuando este se elimina
      Given Existe un proyecto y se conoce su Id
      Given El proyecto tiene tareas
      When Se le intenta eliminar
      Then Tambien se eliminan las tareas
