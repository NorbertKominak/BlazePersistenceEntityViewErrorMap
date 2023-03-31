package com.example.demo.cat;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.example.demo.cat.view.CatView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class CatRepository {

    @PersistenceContext
    private EntityManager em;

    private CriteriaBuilderFactory cbf;

    private EntityViewManager evm;


    public CatView find(@NonNull Integer id) {
        CriteriaBuilder<Cat> criteriaBuilder = cbf.create(em, Cat.class);

        CriteriaBuilder<CatView> viewCriteriaBuilder = evm
                .applySetting(EntityViewSetting.create(CatView.class), criteriaBuilder);

        return viewCriteriaBuilder
                .from(Cat.class)
                .where("id").eq(id)
                .getSingleResult();
    }

    @Transactional
    public Cat save(Cat cat) {
        return em.merge(cat);
    }


    @Autowired
    public void setCbf(CriteriaBuilderFactory cbf) {
        this.cbf = cbf;
    }

    @Autowired
    public void setEvm(EntityViewManager evm) {
        this.evm = evm;
    }
}
