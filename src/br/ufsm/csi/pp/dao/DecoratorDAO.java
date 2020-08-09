package br.ufsm.csi.pp.dao;

import br.ufsm.csi.pp.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@Repository
public class DecoratorDAO<T> implements DAOGenericoInterface<T> {
    private final DAOGenericoInterface daoGenericoInterface;
    private final HibernateTemplate hibernateTemplate;

    private enum TipoEntrada {
        CRIAÇÃO,
        REMOÇÃO,
        ATUALIZAÇÃO,
        LEITURA
    }


    public DecoratorDAO(DAOGenericoInterface daoGenericoInterface,
                        HibernateTemplate hibernateTemplate) {

        this.daoGenericoInterface = daoGenericoInterface;
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public T getById(Serializable id) {
        final var t = (T) this.daoGenericoInterface.getById(id);
        final var log = this.buildLog(t, TipoEntrada.LEITURA);

        this.insertLog(log);

        return t;
    }

    @Override
    public void update(T t) {
        this.daoGenericoInterface.update(t);
        final var log = this.buildLog(t, TipoEntrada.ATUALIZAÇÃO);

        this.insertLog(log);

    }

    @Override
    public void remove(T t) {
        this.daoGenericoInterface.remove(t);
        final var log = this.buildLog(t, TipoEntrada.REMOÇÃO);

        this.insertLog(log);
    }

    @Override
    public void create(T t) {
        this.daoGenericoInterface.create(t);
        final var log = this.buildLog(t, TipoEntrada.CRIAÇÃO);

        this.insertLog(log);

    }


    @Override
    public DataSource getDataSource() {
        return null;
    }


    private Log insertLog(Log l) {
        l.setId((Long) this.hibernateTemplate.save(l));

        return l;
    }

    private Log buildLog(T t, TipoEntrada tipo )  {

        final var classe = t.getClass();
        try {

            final var metodo = classe.getDeclaredMethod("getId");
            if (metodo != null && metodo.getReturnType() == Long.class) {

                final var idObjeto = (Long) metodo.invoke(t);

                return new Log(tipo.name(), idObjeto, classe.getName());
            }
        } catch (NoSuchMethodException |
                IllegalAccessException |
                InvocationTargetException e) {

            e.printStackTrace();
        }


        return null;
    }

}
