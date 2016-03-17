package com.wdxxl.model.brand;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TimeStampObject.class)
public abstract class TimeStampObject_ {

	public static volatile SingularAttribute<TimeStampObject, Boolean> deleted;
	public static volatile SingularAttribute<TimeStampObject, Date> created;
	public static volatile SingularAttribute<TimeStampObject, Long> id;
	public static volatile SingularAttribute<TimeStampObject, Date> lastModified;
	public static volatile SingularAttribute<TimeStampObject, Integer> version;

}

