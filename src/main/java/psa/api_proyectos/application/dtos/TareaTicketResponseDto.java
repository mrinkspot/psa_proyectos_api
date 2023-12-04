package psa.api_proyectos.application.dtos;

import psa.api_proyectos.domain.models.Tarea;

// TODO: revisar si esta ok devolver ids o si es necesario devolver otra cosa
public class TareaTicketResponseDto {
    public long tareaId;
    public long ticketId;
    public Tarea tarea;
}