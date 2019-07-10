package com.myRetail.app.repository

import groovy.transform.CompileStatic
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface ProductInfoRepository extends CassandraRepository<ProductInfo> {
    @Query("select * from product_info where product_id = ?0 ")
    Iterable<ProductInfo> findByProductId(Long productID)

}
