package com.wdxxl.model.brand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BrandJpaMetaModelTest {
    static EntityManagerFactory emf;
    static EntityManager entityManager;

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("jcg-JPA");
        entityManager = emf.createEntityManager();

        // Build Mock Data
        entityManager.getTransaction().begin();
        Brand brand1 = new Brand();
        brand1.setParentBrand(null);
        brand1.setName("Nordstorm1");
        List<String> commonNames1 = new ArrayList<String>();
        commonNames1.add("nord1");
        brand1.setCommonNames(commonNames1);
        brand1.setExplicitMatchOnly(false);
        brand1.setBadSemKeyword(false);
        brand1.setExcludedFromSem(false);
        brand1.setFeatured(false);
        brand1.setSizePrefix("size1");
        brand1.setImageUrl("imageUrl1");

        BrandSynonym brandSynonym11 = new BrandSynonym();
        brandSynonym11.setIndexable(true);
        brandSynonym11.setLanguage("zh");
        brandSynonym11.setName("cn Synonymn1");
        BrandSynonym brandSynonym12 = new BrandSynonym();
        brandSynonym12.setIndexable(false);
        brandSynonym12.setLanguage("en");
        brandSynonym12.setName("us Synonymn1");
        Set<BrandSynonym> brandSynonyms1 = new HashSet<>();
        brandSynonyms1.add(brandSynonym11);
        brandSynonyms1.add(brandSynonym12);
        brand1.setBrandSynonyms(brandSynonyms1);

        brand1.setBrandAttribute("facebook_key", "facebook_value_1");
        entityManager.persist(brand1);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Brand brand2 = new Brand();
        brand2.setParentBrand(brand1);
        brand2.setName("Nordstorm2");
        List<String> commonNames2 = new ArrayList<String>();
        commonNames2.add("nord2");
        commonNames2.add("storm2");
        brand2.setCommonNames(commonNames2);
        brand2.setExplicitMatchOnly(true);
        brand2.setBadSemKeyword(true);
        brand2.setExcludedFromSem(true);
        brand2.setFeatured(true);
        brand2.setSizePrefix("size2");
        brand2.setImageUrl("imageUrl2");

        BrandSynonym brandSynonym21 = new BrandSynonym();
        brandSynonym21.setIndexable(true);
        brandSynonym21.setLanguage("zh");
        brandSynonym21.setName("cn Synonymn2");
        BrandSynonym brandSynonym22 = new BrandSynonym();
        brandSynonym22.setIndexable(false);
        brandSynonym22.setLanguage("en");
        brandSynonym22.setName("us Synonymn2");
        Set<BrandSynonym> brandSynonyms2 = new HashSet<>();
        brandSynonyms2.add(brandSynonym21);
        brandSynonyms2.add(brandSynonym22);
        brand2.setBrandSynonyms(brandSynonyms2);

        brand2.setBrandAttribute("facebook_key", "facebook_value_2");
        entityManager.persist(brand2);

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Brand brand3 = new Brand();
        brand3.setParentBrand(brand1);
        brand3.setName("Nordstorm3");
        List<String> commonNames3 = new ArrayList<String>();
        commonNames3.add("nord3");
        commonNames3.add("storm3");
        commonNames3.add("king3");
        brand3.setCommonNames(commonNames3);
        brand3.setExplicitMatchOnly(true);
        brand3.setBadSemKeyword(true);
        brand3.setExcludedFromSem(true);
        brand3.setFeatured(true);
        brand3.setSizePrefix("size3");
        brand3.setImageUrl("imageUrl3");

        BrandSynonym brandSynonym31 = new BrandSynonym();
        brandSynonym31.setIndexable(true);
        brandSynonym31.setLanguage("zh");
        brandSynonym31.setName("cn Synonymn3");
        BrandSynonym brandSynonym32 = new BrandSynonym();
        brandSynonym32.setIndexable(false);
        brandSynonym32.setLanguage("en");
        brandSynonym32.setName("us Synonymn3");
        Set<BrandSynonym> brandSynonyms3 = new HashSet<>();
        brandSynonyms3.add(brandSynonym31);
        brandSynonyms3.add(brandSynonym32);
        brand3.setBrandSynonyms(brandSynonyms3);

        brand3.setBrandAttribute("facebook_key", "facebook_value_3");
        entityManager.persist(brand3);

        entityManager.getTransaction().commit();
    }

    @Test
    public void selectAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);
        CriteriaQuery<Brand> select = criteriaQuery.select(rootBrand);
        TypedQuery<Brand> typedQuery = entityManager.createQuery(select);
        List<Brand> resultlist = typedQuery.getResultList();

        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }

    // 排序结果 orderBy desc
    @Test
    public void selectAllDesc() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);
        // Ordering the records
        CriteriaQuery<Brand> select = criteriaQuery.select(rootBrand);
        select.orderBy(criteriaBuilder.desc(rootBrand.get("name")));
        TypedQuery<Brand> typedQuery = entityManager.createQuery(select);
        List<Brand> resultlist = typedQuery.getResultList();

        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }

    // 4.Predicate 过滤条件
    // CriteriaBuilder 也是作为Predicate 实例的工厂，Predicate 对象通过调用CriteriaBuilder 的条件方法（ equal，notEqual，
    // gt， ge，lt， le，between，like等）创建。
    // SQL:SELECT * FROM Brand WHERE brand_id > 1;
    @Test
    public void selectPredicate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);
        Predicate condition = criteriaBuilder.gt(rootBrand.get(TimeStampObject_.id), 1);
        // Predicate condition = criteriaBuilder.like(rootBrand.get(Brand_.name), "%storm1");
        criteriaQuery.where(condition);
        TypedQuery<Brand> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Brand> resultlist = typedQuery.getResultList();
        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }

    // 5.Predicate[] 多个过滤条件
    // SQL:SELECT * FROM brand WHERE upper(name) like '%NORDSTORM%' and brand_id > 1;
    @Test
    public void selectPredicateAnd() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);

        List<Predicate> predicatesList = new ArrayList<Predicate>();
        predicatesList.add(criteriaBuilder.like(criteriaBuilder.upper(rootBrand.get(Brand_.name)),
                "%NORDSTORM%"));
        predicatesList.add(criteriaBuilder.gt(rootBrand.get(TimeStampObject_.id), 1));
        criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));

        TypedQuery<Brand> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Brand> resultlist = typedQuery.getResultList();
        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }

    // 7.Expression 用在查询语句的select，where和having子句中，该接口有 isNull, isNotNull 和 in方法
    @Test
    public void testExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);
        criteriaQuery.where(rootBrand.get(TimeStampObject_.id).in(1, 3));
        List<Brand> resultlist = entityManager.createQuery(criteriaQuery).getResultList();
        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
        // SQL: SELECT * FROM brand WHERE brand_id in (1 , 3)

        // 定义一个Expression
        Expression<Long> exp = rootBrand.get(TimeStampObject_.id);
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(2L);
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        predicatesList.add(exp.in(idList));
        criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
        List<Brand> resultlist2 = entityManager.createQuery(criteriaQuery).getResultList();
        for (Brand brand : resultlist2) {
            System.out.println(brand.toString());
        }
        // SQL: SELECT * FROM brand WHERE bradn_id in (1 , 2)
    }

    // 8.复合谓词
    // Criteria
    // Query也允许开发者编写复合谓词，通过该查询可以为多条件测试下面的查询检查两个条件。
    // 首先，name属性是否以xxl1，其次，employee的id属性是否是1。逻辑操作符and执行获得结果记录。
    // SQL:SELECT * FROM Brand WHERE name like '%Nordstorm%' and brand_id = 1;
    @Test
    public void selectSingularAttributeAnd() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);
        Predicate condition =
                criteriaBuilder.and(criteriaBuilder.like(rootBrand.get(Brand_.name), "%Nordstorm%"),
                        criteriaBuilder.equal(rootBrand.get(TimeStampObject_.id), 1));
        criteriaQuery.where(condition);
        TypedQuery<Brand> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Brand> resultlist = typedQuery.getResultList();
        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }

    // 参数化表达式
    // 在JPQL中，查询参数是在运行时通过使用命名参数语法(冒号加变量，如:name)传入的。
    // 在Criteria查询中，查询参数是在运行时创建ParameterExpression对象并为在查询前调用TypeQuery,setParameter方法设置而传入的
    // SQL:SELECT * FROM brand WHERE name = 'Nordstorm3';
    @Test
    public void selectParameterExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);
        ParameterExpression<String> name = criteriaBuilder.parameter(String.class);
        Predicate condition = criteriaBuilder.equal(rootBrand.get(Brand_.name), name);
        criteriaQuery.where(condition);
        TypedQuery<Brand> testQuery = entityManager.createQuery(criteriaQuery);
        List<Brand> resultList = testQuery.setParameter(name, "Nordstorm3").getResultList();
        for (Brand brand : resultList) {
            System.out.println(brand.toString());
        }
    }

    // 排序结果 orderBy desc
    // SELECT * FROM Employee ORDER BY age ASC
    @Test
    public void selectOrderByDesc() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);

        CriteriaQuery<Brand> select = criteriaQuery.select(rootBrand)
                .orderBy(criteriaBuilder.desc(rootBrand.get("name")));
        // CriteriaQuery<Object> select =
        // criteriaQuery.select(rootBrand).orderBy(criteriaBuilder.asc(rootBrand.get(Brand_.name)));
        TypedQuery<Brand> typedQuery = entityManager.createQuery(select);
        List<Brand> resultList = typedQuery.getResultList();
        for (Brand brand : resultList) {
            System.out.println(brand.toString());
        }
    }

    // 分组
    // CriteriaQuery 实例的groupBy 方法用于基于Expression的结果分组。查询通过设置额外表达式，以后调用having方法。
    // SELECT name, COUNT(name) FROM employee GROUP BY name HAVING name like '%xxl%'
    @Test
    public void selectGroupHaving() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);

        criteriaQuery.groupBy(rootBrand.get(Brand_.name));
        criteriaQuery.having(criteriaBuilder.like(rootBrand.get(Brand_.name), "%storm%"));
        criteriaQuery.select(criteriaBuilder.tuple(rootBrand.get(Brand_.name),
                criteriaBuilder.count(rootBrand.get(Brand_.name))));
        TypedQuery<Tuple> q = entityManager.createQuery(criteriaQuery);
        List<Tuple> resultList = q.getResultList();
        for (Tuple result : resultList) {
            System.out.println(result.get(0).toString() + ":" + result.get(1).toString());
        }
    }

    // Self @ManyToOne
    // 查询父Id为1的所有对象
    @Test
    public void selectJoinParentBrand() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Join<Brand, Brand> brandNode = criteriaQuery.from(Brand.class).join(Brand_.parentBrand);
        criteriaQuery.where(criteriaBuilder.equal(brandNode.get(TimeStampObject_.id), 1));
        TypedQuery<Brand> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Brand> resultlist = typedQuery.getResultList();
        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }

    // List
    // 关联的List 大于2的数据
    // TODO current cannot query ListJoin values only have index
    // SQL: SELECT * FROM brand b INNER JOIN brand_common_name bc ON b.brand_id=bc.brand_id WHERE
    // bc.common_name_index>1
    @Test
    public void selectListJoinCommonNames() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        ListJoin<Brand, String> listJoin = criteriaQuery.from(Brand.class).join(Brand_.commonNames);
        criteriaQuery.where(criteriaBuilder.gt(listJoin.index(), 1));
        TypedQuery<Brand> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Brand> resultlist = typedQuery.getResultList();
        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }
    // Set
    // SELECT DISTINCT * FROM brand b INNER JOIN brand_synonym bs ON b.brand_id = bs.brand_id WHERE
    // bs.brand_synonym_id=1
    @Test
    public void selectSetJoinBrandSynonymn() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);

        SetJoin<Brand, BrandSynonym> setJoin =
                criteriaQuery.from(Brand.class).join(Brand_.brandSynonyms);
        criteriaQuery.where(criteriaBuilder.equal(setJoin.get(BrandSynonym_.id), 1)).distinct(true);
        TypedQuery<Brand> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Brand> resultlist = typedQuery.getResultList();
        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }

    // Map
    // SQL: SELECT * FROM brand b INNER JOIN brand_additional_attributes a ON b.brand_id=a.brand_id
    // WHERE attribute_name=? AND attribute_value=?
    // http://stackoverflow.com/questions/8490930/querying-a-map-using-jpa-2-0-criteria
    @Test
    public void selectMapJoinAdditionalAttributes() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
        Root<Brand> rootBrand = criteriaQuery.from(Brand.class);

        MapJoin<Brand, String, String> mapJoin = rootBrand.join(Brand_.additionalAttributes);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(mapJoin.key(), "facebook_key"),
                criteriaBuilder.equal(mapJoin.value(), "facebook_value_1")));
        TypedQuery<Brand> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Brand> resultlist = typedQuery.getResultList();
        for (Brand brand : resultlist) {
            System.out.println(brand.toString());
        }
    }

    @AfterClass
    public static void afterClass() {
        entityManager.close();
        emf.close();
    }

}
