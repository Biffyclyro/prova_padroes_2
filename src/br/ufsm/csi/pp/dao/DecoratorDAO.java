package br.ufsm.csi.pp.dao;

import br.ufsm.csi.pp.model.Log;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class DecoratorDAO<T> implements DAOGenericoInterface<T> {
    private final DAOGenericoInterface<T> daoGenericoInterface;
    private final SessionFactory sessionFactory;

    private enum TipoEntrada {
        CRIAÇÃO,
        REMOÇÃO,
        ATUALIZAÇÃO,
        LEITURA
    }


    public DecoratorDAO(DAOGenericoInterface daoGenericoInterface,
                        SessionFactory sessionFactory) {

        this.daoGenericoInterface = daoGenericoInterface;
        this.sessionFactory= sessionFactory;
    }

    @Override
    public T getById(Serializable id) {
        final var t = this.daoGenericoInterface.getById(id);
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
        return this.daoGenericoInterface.getDataSource();
    }


    private Log insertLog(Log l) {
        final var session = this.sessionFactory.openSession();
        session.beginTransaction();
        l.setId((Long) session.save(l));
        session.getTransaction().commit();

        return l;
    }

    private Log buildLog(T t, TipoEntrada tipo )  {

        final var classe = t.getClass();
        try {

            final var metodo = classe.getDeclaredMethod("getId");
            if ( metodo.getReturnType() == Long.class) {

                final var idObjeto = (Long) metodo.invoke(t);

                return new Log(tipo.name(), idObjeto, classe.getName());
            } else {
                throw new UnsupportedOperationException("id não é do tipo Long");
            }

        } catch (NoSuchMethodException |
                IllegalAccessException |
                InvocationTargetException e) {

          throw new RuntimeException(e);
        }


    }

}
