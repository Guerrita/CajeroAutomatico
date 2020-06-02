package sample.model;

public class Cuenta {
    private String nombres,
                   apellidos,
                   genero,
                   id,
                   celular,
                   contrasena;
    private Long saldoCuenta;

    public Cuenta(String nombres, String apellidos, String genero, String id, String celular, String contrasena, Long saldoCuenta) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.id = id;
        this.celular = celular;
        this.contrasena = contrasena;
        this.saldoCuenta = saldoCuenta;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(Long saldoCuenta) {
        this.saldoCuenta= saldoCuenta;
    }
}
