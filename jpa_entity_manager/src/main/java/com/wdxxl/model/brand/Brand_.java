package com.wdxxl.model.brand;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Brand.class)
public abstract class Brand_ extends com.wdxxl.model.brand.TimeStampObject_ {

	public static volatile SetAttribute<Brand, BrandSynonym> brandSynonyms;
	public static volatile ListAttribute<Brand, String> commonNames;
	public static volatile SingularAttribute<Brand, Brand> parentBrand;
	public static volatile SingularAttribute<Brand, String> imageUrl;
	public static volatile SingularAttribute<Brand, String> name;
	public static volatile SingularAttribute<Brand, Boolean> badSemKeyword;
	public static volatile SingularAttribute<Brand, Boolean> excludedFromSem;
	public static volatile SingularAttribute<Brand, Boolean> isFeatured;
	public static volatile SingularAttribute<Brand, String> sizePrefix;
	public static volatile SingularAttribute<Brand, Boolean> explicitMatchOnly;
	public static volatile MapAttribute<Brand, String, String> additionalAttributes;

}

