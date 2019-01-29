package de.grzb.verleihservice.repositories;

import de.grzb.verleihservice.materialien.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public class VormerkkartenRepository {
    private final Repository repository;

    protected static interface Repository extends CrudRepository<Vormerkkarte, Long> { }

    protected VormerkkartenRepository(Repository repository) {
        this.repository = repository;
    }

    public boolean vormerkkarteExistiertFuer(Long mediumId) {
        return repository.existsById(mediumId);
    }

    public Vormerkkarte getVormerkkarteFuer(Long mediumId) {
        return repository.findById(mediumId).get();
    }

    public void speichere(Vormerkkarte vormerkkarte) {
        repository.save(vormerkkarte);
    }

    public void loesche(Vormerkkarte vormerkkarte) {
        repository.delete(vormerkkarte);
    }
}
