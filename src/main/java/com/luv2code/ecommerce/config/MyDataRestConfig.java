package com.luv2code.ecommerce.config;

import com.luv2code.ecommerce.Entity.Country;
import com.luv2code.ecommerce.Entity.Product;
import com.luv2code.ecommerce.Entity.ProductCategory;
import com.luv2code.ecommerce.Entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;
    @Autowired

    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
       HttpMethod[] theUnsupportedMethods = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
        disableHttpMethods(ProductCategory.class,config, theUnsupportedMethods);

        disableHttpMethods(Product.class,config, theUnsupportedMethods);
        disableHttpMethods(Country.class,config, theUnsupportedMethods);
        disableHttpMethods(State.class,config, theUnsupportedMethods);

//        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        //call an internal helper method to expose the ids
        exposeIds(config);
    }

    private static void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedMethods) {
        config.getExposureConfiguration()
                 .forDomainType(theClass)
                 .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods)))
                 .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedMethods));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        //expose entity ids


        //get a list of all entity classes from the entity manager

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        //create an array list of those entity types

        List<Class> entityClasses = new ArrayList<>();

        //get the entity types for the entities
        for (EntityType tempEntityType : entities){
            entityClasses.add(tempEntityType.getJavaType());
        }
        //expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);


    }
}
