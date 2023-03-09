package nl.ordina.ypfeb23.wordcounter.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenericObjectConverter implements AttributeConverter<Object, String> {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object convertToEntityAttribute(final String object) {
        throw new RuntimeException("convertToEntityAttribute not implemented");
    }
}
