Feature: Operaciones de la relacion de tarea con un ticket de soporte
  Revisión de comportamiento del servicio de tareaTicket

  Scenario: Creacion correcta de la relacion Tarea-Ticket
    Given existe una tarea y un ticket y no estan relacionados
    When los relaciono
    Then puedo obtener informacion de uno o del otro mediante esta relacion

  Scenario: No se permite la creacion de una relacion ya existente
    Given existe una tarea y un ticket y  estan relacionados
    When los relaciono de vuelta
    Then El sistema me indica que ya estan relacionados