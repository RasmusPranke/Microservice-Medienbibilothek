package de.grzb.verleihservice.repositories;

import de.grzb.verleihservice.materialien.*;
import java.util.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public class VerleihkartenRepository {
    private final Repository repository;

    protected static interface Repository extends CrudRepository<Verleihkarte, Long> {
        List<Verleihkarte> findAllByEntleiherId(Long entleiherId);
        void deleteAllByMediumId(List<Long> medienIds);
    }

    protected VerleihkartenRepository(Repository repository) {
        this.repository = repository;
    }

    public List<Verleihkarte> getVerleihkarten() {
        List<Verleihkarte> verleihkarten = new ArrayList<>();
        repository.findAll().forEach(verleihkarten::add);
        return verleihkarten;
    }

    public List<Verleihkarte> getVerleihkartenFuer(Long kundenId) {
        return repository.findAllByEntleiherId(kundenId);
    }

    public Verleihkarte getVerleihkarteFuer(Long mediumId) {
        return repository.findById(mediumId).get();
    }

    public boolean verleihkarteExistiertFuer(Long mediumId) {
        return repository.existsById(mediumId);
    }

    public void loescheVerleihkartenFuer(List<Long> medienIds) {
        repository.deleteAllByMediumId(medienIds);
    }

    public void speichere(Verleihkarte verleihkarte) {
        repository.save(verleihkarte);
    }
}
