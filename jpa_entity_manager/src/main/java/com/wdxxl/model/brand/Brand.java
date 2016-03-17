package com.wdxxl.model.brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "brand")
@AttributeOverride(name = "id", column = @Column(name = "brand_id") )
public class Brand extends TimeStampObject {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent_brand_id")
    private Brand parentBrand;

    @Column(nullable = false, length = 255, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = BrandSynonym.class)
    @JoinColumn(name = "brand_id")
    @Access(AccessType.PROPERTY)
    private Set<BrandSynonym> brandSynonyms = new HashSet<>();

    @Column(name = "explicit_match_only", columnDefinition = "BIT", length = 1)
    private boolean explicitMatchOnly;

    @ElementCollection
    @CollectionTable(name = "brand_common_name", joinColumns = @JoinColumn(name = "brand_id") )
    @Column(name = "common_name", length = 255)
    @OrderColumn(name = "common_name_index")
    private List<String> commonNames = new ArrayList<>();

    @Column(name = "bad_sem_keyword", columnDefinition = "BIT", length = 1)
    private boolean badSemKeyword;

    @Column(name = "excluded_from_sem", columnDefinition = "BIT", length = 1)
    private boolean excludedFromSem;

    @Column(name = "is_featured", columnDefinition = "BIT", length = 1)
    private boolean isFeatured;

    @Column(name = "size_prefix", nullable = true, length = 5)
    private String sizePrefix;

    @Column(name = "image_url", nullable = true, length = 255)
    @Access(AccessType.FIELD)
    private String imageUrl;

    @ElementCollection
    @MapKeyColumn(name = "attribute_name", length = 128)
    @Column(name = "attribute_value", length = 3072)
    @CollectionTable(name = "brand_additional_attributes",
            joinColumns = @JoinColumn(name = "brand_id") )
    @Access(AccessType.FIELD)
    private final Map<String, String> additionalAttributes = new HashMap<>();

    public Brand() {}

    public String getBrandAttribute(String key) {
        if (additionalAttributes == null || additionalAttributes.isEmpty()) {
            return null;
        }
        return additionalAttributes.get(key);
    }

    public String setBrandAttribute(String key, String value) {
        if (value != null) {
            return additionalAttributes.put(key, value);
        } else {
            return additionalAttributes.remove(key);
        }
    }

    public Brand getParentBrand() {
        return parentBrand;
    }

    public void setParentBrand(Brand parentBrand) {
        this.parentBrand = parentBrand;
    }

    @Column(nullable = false, length = 255, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BrandSynonym> getBrandSynonyms() {
        return brandSynonyms;
    }

    public void setBrandSynonyms(Set<BrandSynonym> brandSynonyms) {
        this.brandSynonyms = brandSynonyms;
    }

    public boolean isExplicitMatchOnly() {
        return explicitMatchOnly;
    }

    public void setExplicitMatchOnly(boolean explicitMatchOnly) {
        this.explicitMatchOnly = explicitMatchOnly;
    }

    public List<String> getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(List<String> commonNames) {
        this.commonNames = commonNames;
    }

    public boolean isBadSemKeyword() {
        return badSemKeyword;
    }

    public void setBadSemKeyword(boolean badSemKeyword) {
        this.badSemKeyword = badSemKeyword;
    }

    public boolean isExcludedFromSem() {
        return excludedFromSem;
    }

    public void setExcludedFromSem(boolean excludedFromSem) {
        this.excludedFromSem = excludedFromSem;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getSizePrefix() {
        return sizePrefix;
    }

    public void setSizePrefix(String sizePrefix) {
        this.sizePrefix = sizePrefix;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("brandId", getId())
                .add("brandName", name)
                .add("parentBrandName", parentBrand == null ? null : parentBrand.getName())
                .add("brandSynonymSize", brandSynonyms.size())
                .add("additionalAttributeSize", additionalAttributes.size())
                .add("commonNamesSize", commonNames.size()).toString();
    }

}
