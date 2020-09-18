package pl.mikel.hibernate.search;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mikel.insurance.dao.InsuranceDao;
import java.util.List;

@Service
public class HibernateSearchService {

    private EntityManager entityManager;

    @Autowired
   public HibernateSearchService(EntityManagerFactory entityManagerFactory){
       entityManager = entityManagerFactory.createEntityManager();

    }

    @Transactional
    public List<InsuranceDao> search(String text) {


        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(InsuranceDao.class).get();


        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .wildcard()
                        .onFields("id",
                                           "clientName",
                                           "clientSurname",
                                           "carModel",
                                           "carSubModel")
                        .matching("*"+text+"*")
                        .createQuery();


        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, InsuranceDao.class);


        return (List<InsuranceDao>) jpaQuery.getResultList();
    }
}
