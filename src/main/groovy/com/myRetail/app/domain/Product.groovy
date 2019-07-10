package com.myRetail.app.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.annotations.ApiModelProperty

import javax.validation.Valid
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

  @ApiModelProperty(value = "Product Id", example = '13860428', required = true)
  @NotNull
  private Long id

  @ApiModelProperty(value = "Product Name", example = "The Big Lebowski (Blu-ray) (Widescreen)",required = true)
  @NotNull
  private String name

  @ApiModelProperty(value = "Product Price",required = true)
  @NotNull
  @Valid
  private CurrentPrice current_price

  Long getId() {
    return id
  }

  void setId(Long id) {
    this.id = id
  }

  String getName() {
    return name
  }

  void setName(String name) {
    this.name = name
  }

  CurrentPrice getCurrent_price() {
    return current_price
  }

  void setCurrent_price(CurrentPrice current_price) {
    this.current_price = current_price
  }

}
