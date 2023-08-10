package com.example.domain.util;

import com.example.domain.entity.ProductDetail;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class ProductDetailSerializer extends StdSerializer<ProductDetail> {

  public ProductDetailSerializer() {
    this(null);
  }

  public ProductDetailSerializer(Class<ProductDetail> t) {
    super(t);
  }

  @Override
  public void serialize(
      ProductDetail productDetail, JsonGenerator jsonGenerator, SerializerProvider serializer)
      throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("id", productDetail.getId());
    jsonGenerator.writeStringField("name", productDetail.getName());
    jsonGenerator.writeNumberField("price", productDetail.getPrice());
    jsonGenerator.writeNumberField("amount", productDetail.getAmount());
    jsonGenerator.writeEndObject();
  }
}
