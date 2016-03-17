package com.wdxxl.model.brand;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "brand_synonym",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"brand_id", "name", "language"},
                name = "brand_synonym_name_language")})
public class BrandSynonym {
    @Id
    @Column(name = "brand_synonym_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TINYINT")
    // @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean indexable;

    @Column(nullable = true, length = 2)
    private String language;

    public BrandSynonym() {

    }

    public BrandSynonym(String name, String language, boolean indexable) {
        this.name = name;
        this.language = language;
        this.indexable = indexable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public boolean isIndexable() {
        return indexable;
    }

    public void setIndexable(boolean value) {
        indexable = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BrandSynonym) {
            BrandSynonym other = (BrandSynonym) obj;
            return getName().equals(other.getName())
                    && Objects.equals(getLanguage(), other.getLanguage());
        }
        return false;
    }
}
