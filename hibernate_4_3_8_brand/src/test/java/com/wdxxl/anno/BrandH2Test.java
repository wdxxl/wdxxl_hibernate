package com.wdxxl.anno;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wdxxl.model.Brand;
import com.wdxxl.model.BrandSynonym;
import com.wdxxl.persistence.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BrandH2Test {
    private static SessionFactory SessionFactory;
    private Session session;
    private Brand brand;

    @BeforeClass
    public static void setUpBeforeClass() {
        SessionFactory = HibernateUtil.getSessionFactory();// .createSessionFactory("hibernate-test.cfg.xml");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        if (SessionFactory != null) {
            SessionFactory.close();
        }
    }

    @Before
    public void setUp() {
        session = SessionFactory.openSession();
        addMockData();
    }

    @After
    public void tearDown() {
        removeMockData();
        if (session != null) {
            session.close();
        }
    }

    private void addMockData() {
        Transaction tx = session.beginTransaction();

        brand = new Brand();
        brand.setCreated(new Date());
        brand.setLastModified(new Date());
        brand.setParentBrand(null);
        brand.setName("Nordstorm");
        List<String> commonNames = new ArrayList<String>();
        commonNames.add("nord");
        commonNames.add("storm");
        brand.setCommonNames(commonNames);
        brand.setExplicitMatchOnly(false);
        brand.setBadSemKeyword(true);
        brand.setExcludedFromSem(false);
        brand.setFeatured(false);
        brand.setSizePrefix("size");
        brand.setImageUrl("imageUrl");

        BrandSynonym brandSynonym1 = new BrandSynonym();
        brandSynonym1.setIndexable(true);
        brandSynonym1.setLanguage("zh");
        brandSynonym1.setName("cn Synonymn");
        BrandSynonym brandSynonym2 = new BrandSynonym();
        brandSynonym2.setIndexable(false);
        brandSynonym2.setLanguage("en");
        brandSynonym2.setName("us Synonymn");
        Set<BrandSynonym> brandSynonyms = new HashSet<>();
        brandSynonyms.add(brandSynonym1);
        brandSynonyms.add(brandSynonym2);
        brand.setBrandSynonyms(brandSynonyms);

        session.save(brand);

        tx.commit();
    }

    private void removeMockData() {
        Transaction tx = session.beginTransaction();
        session.delete(brand);
        tx.commit();
    }

    @Test
    public void addMockSuccess() {
        assertNotNull(session.get(Brand.class, brand.getId()));
    }

    @Test
    public void testRemoveBrandSynonyms() {
        Transaction tx = session.beginTransaction();
        brand = (Brand) session.get(Brand.class, brand.getId());
        assertEquals(2, brand.getBrandSynonyms().size());
        for (BrandSynonym bs : brand.getBrandSynonyms()) {
            brand.getBrandSynonyms().remove(bs);
            break;
        }
        session.saveOrUpdate(brand);
        tx.commit();

        brand = (Brand) session.get(Brand.class, brand.getId());
        assertEquals(1, brand.getBrandSynonyms().size());

        BrandSynonym brandSynonym = (BrandSynonym) session.get(BrandSynonym.class,
                brand.getBrandSynonyms().iterator().next().getId());
        assertEquals(brandSynonym.getName(), brand.getBrandSynonyms().iterator().next().getName());
    }

    @Test
    public void testRemoveCommonNames() {
        Transaction tx = session.beginTransaction();
        brand = (Brand) session.get(Brand.class, brand.getId());
        assertEquals(2, brand.getCommonNames().size());
        brand.getCommonNames().remove(0);
        session.saveOrUpdate(brand);
        tx.commit();

        brand = (Brand) session.get(Brand.class, brand.getId());
        assertEquals(1, brand.getCommonNames().size());
        assertEquals("storm", brand.getCommonNames().get(0));
    }

}
