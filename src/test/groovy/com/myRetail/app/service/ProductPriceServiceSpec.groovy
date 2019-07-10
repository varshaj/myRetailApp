//package com.myRetail.app.service
//
//import com.myRetail.app.domain.CurrentPrice
//import com.myRetail.app.repository.ProductInfo
//import com.myRetail.app.repository.ProductInfoRepository
//import spock.lang.Specification
//import spock.lang.Unroll
//
//class ProductPriceServiceSpec extends Specification {
//
//    ProductInfoRepository productInfoRepository = Mock(ProductInfoRepository)
//
//    ProductPriceService productPriceService = new ProductPriceService(
//            productInfoRepository: productInfoRepository
//    )
//
//    long productId = 13860429L
//    String productName = 'test'
//    def "getProductPrice - success scenario"() {
//
//        when:
//        CurrentPrice currentPrice = productPriceService.getProductPrice(productId)
//
//        then:
//
//        1 * productInfoRepository.findByProductId() >> { new ProductInfo(
//                productId: productId,
//                productName: productName,
//                currentPrice: 10.11,
//                currencyCode: "USD"
//        )}
//
//        currentPrice.value == 10.11
//        currentPrice.currency_code == "USD"
//
//    }
// class ProductInfoIterable implements Iterable<ProductInfo> {
//
//     @Override
//     Iterator<ProductInfo> iterator() {
//         return null
//     }
// }
//
//}
