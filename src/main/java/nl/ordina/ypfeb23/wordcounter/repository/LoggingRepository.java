package nl.ordina.ypfeb23.wordcounter.repository;


import nl.ordina.ypfeb23.wordcounter.repository.entity.HistoryLoggingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoggingRepository extends JpaRepository<HistoryLoggingEntity, UUID> { }
