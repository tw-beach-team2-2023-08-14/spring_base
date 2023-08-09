package com.example.domain.util;

import com.example.domain.entity.OrderItem;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class OrderItemSerializer extends StdSerializer<OrderItem> {

  public OrderItemSerializer() {
    this(null);
  }

  public OrderItemSerializer(Class<OrderItem> t) {
    super(t);
  }

  @Override
  public void serialize(
      OrderItem orderItem, JsonGenerator jsonGenerator, SerializerProvider serializer)
      throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("id", orderItem.getId());
    jsonGenerator.writeStringField("name", orderItem.getName());
    jsonGenerator.writeNumberField("price", orderItem.getPrice());
    jsonGenerator.writeNumberField("amount", orderItem.getAmount());
    jsonGenerator.writeEndObject();
  }
}
