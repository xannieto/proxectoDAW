package aplicacion;

import java.util.ArrayList;
import java.util.List;

public final class Carro {

    private List<Artigo> artigos;

    public Carro() {
        this.artigos = new ArrayList<>();
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
        this.artigos = artigos;
    }

    public void engadirArtigo(Artigo artigo){
        if (this.comprobarLista(artigo)){
            for (Artigo art : this.artigos){
                if (art.equals(artigo)){
                    art.setSeleccion(art.getSeleccion() + 1);
                }
            }

        } else {
            this.artigos.add(artigo);
        }
    }

    public void quitarArtigo(String artigo){
        for (Artigo art : this.artigos){
            if (art.getId().equals(artigo)){
                
            }
        }
    }

    private Boolean comprobarLista(Artigo artigo){
        for (Artigo art : this.artigos){
            if (art.equals(artigo)){
                return true;
            }
        }
        return false;
    }

}