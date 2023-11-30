Feature: Operaciones de modificición de tarea
  Revisión del comportamiento de la modificación de una tarea

  Scenario: Modificacion correcta de los campos de una tarea
    Given Existe una tarea, esta pertenece a un proyecto y se conoce su Id y el Id del proyecto
    When Se le intentan modificar algún campo de la tarea con un dato válido
    Then La tarea se actualiza y ahora tiene sus campos modificados

  Scenario: Modificacion incorrecta del nombre de un proyecto
    Given Existe una tarea, esta pertenece a un proyecto y se conoce su Id y el Id del proyecto
    When Se le intentan modificar algún campo de la tarea con un dato inválido
    Then La tarea no se actualiza y se recibe una excepción

  Scenario: Eliminacion de un proyecto ya creado
    Given Existe una tarea, esta pertenece a un proyecto y se conoce su Id y el Id del proyecto
    When Se le intenta eliminar a la tarea
    Then La tarea es eliminada y ya no puede ser obtenida