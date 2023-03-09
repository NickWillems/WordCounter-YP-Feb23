package nl.ordina.ypfeb23.wordcounter.repository.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import nl.ordina.ypfeb23.wordcounter.repository.GenericObjectConverter;

import java.util.Date;


@NoArgsConstructor
@Entity
public class HistoryLoggingEntity {
    public HistoryLoggingEntity(final Date recordedAt, final String request, final Object input, final Object output) {
        this.recordedAt = recordedAt;
        this.request = request;
        this.input = input;
        this.output = output;
    }

    @Id
    @Column
    public Date recordedAt;

    @Column
    public String request;

    @Column
    @Convert(converter = GenericObjectConverter.class)
    public Object input;

    @Column
    @Convert(converter = GenericObjectConverter.class)
    public Object output;
}
