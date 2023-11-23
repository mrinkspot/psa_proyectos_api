package psa.api_proyectos.domain.enums;

public enum ColaboradorProyectoRol {
    LIDER("Líder de Proyecto"),
    MIEMBRO("Miembro");

    private final String value;

    ColaboradorProyectoRol(String value) { this.value = value; }

    public String get() {
        return value;
    }
}
