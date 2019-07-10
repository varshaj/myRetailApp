package com.myRetail.app.repository

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import org.springframework.cassandra.core.PrimaryKeyType
import org.springframework.data.cassandra.mapping.Column
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.mapping.Table


@CompileStatic
@EqualsAndHashCode
@Table("product_info")
class ProductInfo {

    @PrimaryKeyColumn(name="product_id", ordinal = 0, type= PrimaryKeyType.PARTITIONED)
    Long productId

    @Column("currency_code")
    String currencyCode

    @Column("current_price")
    BigDecimal currentPrice

    @Column("product_name")
    String productName

    Long getProductId () {
        return productId
    }

    void setProductId (Long productId) {
        this.productId = productId
    }

    String getCurrencyCode () {
        return currencyCode
    }

    void setCurrencyCode (String currencyCode) {
        this.currencyCode = currencyCode
    }

    BigDecimal getCurrentPrice () {
        return currentPrice
    }

    void setCurrentPrice (BigDecimal currentPrice) {
        this.currentPrice = currentPrice
    }

    String getProductName () {
        return productName
    }

    void setProductName (String productName) {
        this.productName = productName
    }

}
