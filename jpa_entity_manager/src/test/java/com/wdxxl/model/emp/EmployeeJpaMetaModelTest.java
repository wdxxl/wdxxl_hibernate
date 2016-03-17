package com.wdxxl.model.emp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeJpaMetaModelTest {
    static EntityManagerFactory emf;
    static EntityManager entityManager;

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("jcg-JPA");
        entityManager = emf.createEntityManager();

        // Build Mock Data
        entityManager.getTransaction().begin();
        Employee employee = new Employee();
        employee.setName("wdxxl1");
        entityManager.persist(employee);
        System.out.println("----COMIITING employee:" + employee.toString());

        Employee employee2 = new Employee();
        employee2.setName("wdxxl2");
        entityManager.persist(employee2);
        System.out.println("----COMIITING employee:" + employee2.toString());

        Employee employee3 = new Employee();
        employee3.setName("wdxxl1");
        entityManager.persist(employee3);
        System.out.println("----COMIITING employee:" + employee3.toString());

        entityManager.getTransaction().commit();
    }

    @Test
    public void selectAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);
        CriteriaQuery<Employee> select = criteriaQuery.select(rootEmployee);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(select);
        List<Employee> resultlist = typedQuery.getResultList();

        for (Employee employee : resultlist) {
            System.out.println(employee.toString());
        }
    }

    // 排序结果 orderBy desc
    @Test
    public void selectAllDesc() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);
        // Ordering the records
        CriteriaQuery<Employee> select = criteriaQuery.select(rootEmployee);
        select.orderBy(criteriaBuilder.desc(rootEmployee.get("name")));
        TypedQuery<Employee> typedQuery = entityManager.createQuery(select);
        List<Employee> resultlist = typedQuery.getResultList();

        for (Employee employee : resultlist) {
            System.out.println(employee.toString());
        }
    }

    // 4.Predicate 过滤条件
    // CriteriaBuilder 也是作为Predicate 实例的工厂，Predicate 对象通过调用CriteriaBuilder 的条件方法（ equal，notEqual，
    // gt， ge，lt， le，between，like等）创建。
    // SQL:SELECT * FROM Employee WHERE employeeId > 1;
    @Test
    public void selectPredicate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);
        Predicate condition = criteriaBuilder.gt(rootEmployee.get(Employee_.employeeId), 1);
        // Predicate condition = criteriaBuilder.like(employee.get(Employee_.name), "%xxl1");
        criteriaQuery.where(condition);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Employee> resultlist = typedQuery.getResultList();
        for (Employee employee : resultlist) {
            System.out.println(employee.toString());
        }
    }

    // 5.Predicate[] 多个过滤条件
    // SQL:SELECT * FROM Employee WHERE upper(name) like '%XXL1' and employeeId > 1;
    @Test
    public void selectPredicateAnd() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);

        List<Predicate> predicatesList = new ArrayList<Predicate>();
        predicatesList.add(criteriaBuilder
                .like(criteriaBuilder.upper(rootEmployee.get(Employee_.name)), "%XXL1"));
        predicatesList.add(criteriaBuilder.gt(rootEmployee.get(Employee_.employeeId), 1));
        criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));

        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Employee> resultlist = typedQuery.getResultList();
        for (Employee employee : resultlist) {
            System.out.println(employee.toString());
        }
    }

    // 7.Expression 用在查询语句的select，where和having子句中，该接口有 isNull, isNotNull 和 in方法
    @Test
    public void testExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);
        criteriaQuery.where(rootEmployee.get(Employee_.employeeId).in(1, 3));
        List<Employee> resultlist = entityManager.createQuery(criteriaQuery).getResultList();
        for (Employee employee : resultlist) {
            System.out.println(employee.toString());
        }
        // SQL: SELECT * FROM employee WHERE employee_id in (1 , 3)

        // 定义一个Expression
        Expression<Long> exp = rootEmployee.get(Employee_.employeeId);
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(2L);
        List<Predicate> predicatesList = new ArrayList<Predicate>();
        predicatesList.add(exp.in(idList));
        criteriaQuery.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
        List<Employee> resultlist2 = entityManager.createQuery(criteriaQuery).getResultList();
        for (Employee employee : resultlist2) {
            System.out.println(employee.toString());
        }
        // SQL: SELECT * FROM employee WHERE employee_id in (1 , 2)
    }

    // 8.复合谓词
    // Criteria
    // Query也允许开发者编写复合谓词，通过该查询可以为多条件测试下面的查询检查两个条件。
    // 首先，name属性是否以xxl1，其次，employee的id属性是否是1。逻辑操作符and执行获得结果记录。
    // SQL:SELECT * FROM Employee WHERE name like '%xxl1' and employeeId = 1;
    @Test
    public void selectSingularAttributeAnd() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);
        Predicate condition =
                criteriaBuilder.and(criteriaBuilder.like(rootEmployee.get(Employee_.name), "%xxl1"),
                        criteriaBuilder.equal(rootEmployee.get(Employee_.employeeId), 1));
        criteriaQuery.where(condition);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Employee> resultlist = typedQuery.getResultList();
        for (Employee employee : resultlist) {
            System.out.println(employee.toString());
        }
    }

    // 参数化表达式
    // 在JPQL中，查询参数是在运行时通过使用命名参数语法(冒号加变量，如:name)传入的。
    // 在Criteria查询中，查询参数是在运行时创建ParameterExpression对象并为在查询前调用TypeQuery,setParameter方法设置而传入的
    // SQL:SELECT * FROM Employee WHERE name = 'wdxxl2';
    @Test
    public void selectParameterExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);
        ParameterExpression<String> name = criteriaBuilder.parameter(String.class);
        Predicate condition = criteriaBuilder.equal(rootEmployee.get(Employee_.name), name);
        criteriaQuery.where(condition);
        TypedQuery<Employee> testQuery = entityManager.createQuery(criteriaQuery);
        List<Employee> resultList = testQuery.setParameter(name, "wdxxl2").getResultList();
        for (Employee employee : resultList) {
            System.out.println(employee.toString());
        }
    }

    // 排序结果 orderBy desc
    // SELECT * FROM Employee ORDER BY age ASC
    @Test
    public void selectOrderByDesc() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);

        CriteriaQuery<Employee> select = criteriaQuery.select(rootEmployee)
                .orderBy(criteriaBuilder.desc(rootEmployee.get("name")));
        // CriteriaQuery<Object> select =
        // criteriaQuery.select(rootEmployee).orderBy(criteriaBuilder.asc(rootEmployee.get(Employee_.name)));
        TypedQuery<Employee> typedQuery = entityManager.createQuery(select);
        List<Employee> resultList = typedQuery.getResultList();
        for (Employee employee : resultList) {
            System.out.println(employee.toString());
        }
    }

    // 分组
    // CriteriaQuery 实例的groupBy 方法用于基于Expression的结果分组。查询通过设置额外表达式，以后调用having方法。
    // SELECT name, COUNT(name) FROM employee GROUP BY name HAVING name like '%xxl%'
    @Test
    public void selectGroupHaving() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);

        criteriaQuery.groupBy(rootEmployee.get(Employee_.name));
        criteriaQuery.having(criteriaBuilder.like(rootEmployee.get(Employee_.name), "%xxl%"));
        criteriaQuery.select(criteriaBuilder.tuple(rootEmployee.get(Employee_.name),
                criteriaBuilder.count(rootEmployee.get(Employee_.name))));
        TypedQuery<Tuple> q = entityManager.createQuery(criteriaQuery);
        List<Tuple> resultList = q.getResultList();
        for (Tuple result : resultList) {
            System.out.println(result.get(0).toString() + ":" + result.get(1).toString());
        }
    }

    @AfterClass
    public static void afterClass() {
        entityManager.close();
        emf.close();
    }

}
