package aplicacion;

public final class Artigo {
    private String id;
    private String nome;
    private String descricion;
    private Double prezo;
    private Integer existencias;
    private Integer seleccion;
    private String imaxe;
    
    public Artigo(){
        this.setId("");
        this.setNome("");
        this.setDescricion("");
        this.setPrezo(0.0);
        this.setExistencias(0);
        this.setSeleccion(0);
        this.setImaxe("");
    }

    public Artigo(String id, String nome, String descricion, Double prezo, Integer existencias, String imaxe) {
        this.setId(id);
        this.setNome(nome);
        this.setDescricion(descricion);
        this.setPrezo(prezo);
        this.setExistencias(existencias);
        this.setSeleccion(0);
        this.setImaxe(imaxe);
    }

    public Artigo(Artigo artigo){
        this.setId(artigo.getId());
        this.setNome(artigo.getNome());
        this.setDescricion(artigo.getDescricion());
        this.setPrezo(artigo.getPrezo());
        this.setExistencias(artigo.getExistencias());
        this.setSeleccion(artigo.getSeleccion());
        this.setImaxe(artigo.getImaxe());
    }

    public String getImaxe(){
        return imaxe;
    }

    public void setImaxe(String imaxe){
        this.imaxe = imaxe;
    }

    public Integer getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Integer seleccion) {
        this.seleccion = seleccion;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    public Double getPrezo() {
        return prezo;
    }

    public void setPrezo(Double prezo) {
        this.prezo = prezo;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Artigo){
            return this.id.equals(((Artigo)obj).getId());
        }
        
        return false;
    }

}