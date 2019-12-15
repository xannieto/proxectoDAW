package aplicacion;

public final class Usuario {
    private String nome;
    private String apelidos;
    private String email;
    private String id;
    private String contrasinal;
    private String dni;
    private String estadoCivil;
    private String dataNacemento;
    private String provincia;
    private String direccion;
    private String codigoPostal;
    private String numeroTelefono;

    public Usuario(String email) {
        this.setEmail(email);
    }

    public Usuario(String email, String id) {
        this.setEmail(email);
        this.setId(id);
    }

    public Usuario(String nome, String email, String id) {
        this.setNome(nome);
        this.setEmail(email);
        this.setId(id);
    }

    public Usuario(String nome, String apelidos, String email, String id, String contrasinal, String dni, String estadoCivil, String dataNacemento, String provincia, String direccion, String codigoPostal, String numeroTelefono) {
        this.setNome(nome);
        this.setApelidos(apelidos);
        this.setEmail(email);
        this.setId(id);
        this.setContrasinal(contrasinal);
        this.setDni(dni);
        this.setEstadoCivil(estadoCivil);
        this.setDataNacemento(dataNacemento);
        this.setProvincia(provincia);
        this.setDireccion(direccion);
        this.setCodigoPostal(codigoPostal);
        this.setNumeroTelefono(numeroTelefono);
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDataNacemento() {
        return dataNacemento;
    }

    public void setDataNacemento(String dataNacemento) {
        this.dataNacemento = dataNacemento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContrasinal() {
        return contrasinal;
    }

    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApelidos() {
        return apelidos;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}