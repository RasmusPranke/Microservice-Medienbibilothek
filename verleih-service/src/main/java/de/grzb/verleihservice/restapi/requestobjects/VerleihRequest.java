package de.grzb.verleihservice.restapi.requestobjects;

import java.util.*;

public class VerleihRequest {
    private Long kundenId;
    private List<Long> medienIds;

    private Calendar datum;

    public Long getKundenId() {
        return kundenId;
    }

    public void setKundenId(Long kundenId) {
        this.kundenId = kundenId;
    }

    public List<Long> getMedienIds() {
        return medienIds;
    }

    public void setMedienIds(List<Long> medienIds) {
        this.medienIds = medienIds;
    }

    public Calendar getDatum() {
        return datum;
    }

    public void setDatum(Calendar datum) {
        this.datum = datum;
    }
}
