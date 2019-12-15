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
                    art.setSeleccion(art.getSeleccion() + artigo.getSeleccion());
                    return;
                }
            }
        } else {
            this.artigos.add(artigo);
        }
    }

    public void quitarArtigo(Artigo artigo){
        this.artigos.remove(artigo);
    }

    public void quitarArtigo(Artigo artigo, Integer cantidade){
        if (this.comprobarLista(artigo)){
            for (Artigo art : this.artigos){
                if (art.equals(artigo)){
                    art.setSeleccion(art.getSeleccion() - cantidade);
                    if (art.getSeleccion() <= 0) this.artigos.remove(art); 
                    return;
                }
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