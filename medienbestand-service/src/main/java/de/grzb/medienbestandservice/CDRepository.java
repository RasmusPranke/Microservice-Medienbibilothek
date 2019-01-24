package de.grzb.medienbestandservice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.grzb.materialien.medien.CD;

public interface CDRepository extends CrudRepository<CD, CD.CDId> {

    List<CD> getBy_interpret(String _interpret);

    List<CD> getBy_spiellaenge(int _spieallaenge);

    List<CD> getBy_titel(String _titel);

    List<CD> getBy_kommentar(String _kommentar);

}
