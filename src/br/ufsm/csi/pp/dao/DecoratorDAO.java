package br.ufsm.csi.pp.dao;

import br.ufsm.csi.pp.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;

@Repository
public class DecoratorDAO<T> implements DAOGenericoInterface<T> {
    private final DAOGenericoInterface daoGenericoInterface;

    @Autowired
    private final HibernateTemplate hibernateTemplate;

    public DecoratorDAO(DAOGenericoInterface daoGenericoInterface,
                        HibernateTemplate hibernateTemplate) {

        this.daoGenericoInterface = daoGenericoInterface;
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public T getById(Serializable id) {
        return (T) this.daoGenericoInterface.getById(id);
    }

    @Override
    public void update(T t) {
        this.daoGenericoInterface.update(t);

    }

    @Override
    public void remove(T t) {
        this.daoGenericoInterface.remove(t);
    }

    @Override
    public void create(T t) {
        this.daoGenericoInterface.create(t);

    }

    private void insertLog(Log l) {
        this.hibernateTemplate.save(l);
    }

    @Override
    public DataSource getDataSource() {
        return null;
    }
}
