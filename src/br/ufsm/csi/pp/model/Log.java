package br.ufsm.csi.pp.model;

import javax.persistence.Entity;

@Entity
public class Log {
    private long id;
    private String tipo;
    private long idObjeto;
    private String classe;

    public Log(long id, String tipo, long idObjeto, String classe) {
        this.id = id;
        this.tipo = tipo;
        this.idObjeto = idObjeto;
        this.classe = classe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(long idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
