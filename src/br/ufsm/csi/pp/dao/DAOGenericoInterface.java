package br.ufsm.csi.pp.dao;

import javax.sql.DataSource;
import java.io.Serializable;

public interface DAOGenericoInterface<T> {
    T getById(Serializable id);

    void update(T t);

    void remove(T t);

    void create(T t);

    DataSource getDataSource();
}
