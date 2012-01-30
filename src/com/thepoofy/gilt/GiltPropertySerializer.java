package com.thepoofy.gilt;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class GiltPropertySerializer extends JsonSerializer<GiltProperty> {

	public GiltPropertySerializer() {

	}

	@Override
	public void serialize(GiltProperty value, JsonGenerator generator,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {

		generator.writeStartObject();
		generator.writeFieldName("name");
		generator.writeString(value.name());
		generator.writeFieldName("label");
		generator.writeString(value.getLabel());
		generator.writeFieldName("site");
		generator.writeString(value.getDivisionKey());
		generator.writeEndObject();
	}
}