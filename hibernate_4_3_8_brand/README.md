
this example going to verify JPA annotation

1. @MappedSuperclass and @AttributeOverride
2. @Access(AccessType.FIELD) and @Access(AccessType.PROPERTY)
3. List<String> commonNames (@ElementCollection and @OrderColumn)
4. Map<String, String> additionalAttributes (@ElementCollection and @MapKeyColumn)
5. Set<BrandSynonym> brandSynonyms (@OneToMany) 
6. Brand parentBrand (@ManyToOne)



mysql -uroot -p63300806 wdxxl -A

truncate table brand;
truncate table brand_common_name;
truncate table brand_additional_attributes;
truncate table brand_synonym;