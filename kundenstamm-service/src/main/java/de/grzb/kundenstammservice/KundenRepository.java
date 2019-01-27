package de.grzb.kundenstammservice;

import org.springframework.data.repository.CrudRepository;

import de.grzb.fachwerte.Kundennummer;
import de.grzb.materialien.Kunde;

public interface KundenRepository extends CrudRepository<Kunde, Kundennummer> {

}
