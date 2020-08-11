package br.ufsm.csi.pp.decorator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private Long idObjeto;
    private String classe;

    public Log(String tipo, long idObjeto, String classe) {
        this.tipo = tipo;
        this.idObjeto = idObjeto;
        this.classe = classe;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getIdObjeto() {
        return this.idObjeto;
    }

    public void setIdObjeto(long idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getClasse() {
        return this.classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
