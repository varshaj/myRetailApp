package com.myRetail.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class CurrentPrice {

  @ApiModelProperty(value = "current price", example = "13.00", required = true)
  @NotNull
  @DecimalMin(value = "0.00")
  private BigDecimal value

  @ApiModelProperty(value = "currency code", example = "USD", required = true)
  @NotNull
  private String currency_code

  BigDecimal getValue () {
    return value
  }

  void setValue (BigDecimal value) {
    this.value = value
  }

  String getCurrency_code () {
    return currency_code
  }

  void setCurrency_code (String currency_code) {
    this.currency_code = currency_code
  }

}
