package psa.api_proyectos.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psa.api_proyectos.domain.models.Colaborador;
import psa.api_proyectos.domain.models.ColaboradorProyecto;
import psa.api_proyectos.domain.enums.ColaboradorProyectoRol;
import psa.api_proyectos.domain.models.Proyecto;
import psa.api_proyectos.data.repositories.ColaboradorProyectoRepository;
import psa.api_proyectos.data.repositories.ColaboradorRepository;
import psa.api_proyectos.data.repositories.ProyectoRepository;

@Service
public class ColaboradorService {
    @Autowired
    private ColaboradorRepository colaboradorRepository;
    @Autowired
    private ColaboradorProyectoRepository colaboradorProyectoRepository;
    @Autowired
    private ProyectoRepository proyectoRepository;

    public ColaboradorProyecto createLiderProyecto(Long liderId, Proyecto proyecto) {
        Colaborador lider = colaboradorRepository.findById(liderId).get();
        ColaboradorProyecto liderProyecto = new ColaboradorProyecto();
        liderProyecto.colaborador = lider;
        liderProyecto.proyecto = proyecto;
        liderProyecto.rol = ColaboradorProyectoRol.LIDER.get();
        return liderProyecto;
    }
    public ColaboradorProyecto createMiembroProyecto(Long miembroId, Proyecto proyecto) {
        Colaborador miembro = colaboradorRepository.findById(miembroId).get();
        ColaboradorProyecto miembroProyecto = new ColaboradorProyecto();
        miembroProyecto.colaborador = miembro;
        miembroProyecto.proyecto = proyecto;
        miembroProyecto.rol = ColaboradorProyectoRol.MIEMBRO.get();
        return miembroProyecto;
    }
}
