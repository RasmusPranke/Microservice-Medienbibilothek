package de.grzb.medienbestandservice.restapi;

import java.util.List;

import org.springframework.http.ResponseEntity;

import de.grzb.materialien.medien.Medium;

public interface IMediumController<K extends Medium> {

    public List<K> medium_get();

    public ResponseEntity<K> medium_set(K medium);

    public ResponseEntity<Boolean> medium_has(Long id);

    public ResponseEntity<K> medium_remove(K medium);

}
