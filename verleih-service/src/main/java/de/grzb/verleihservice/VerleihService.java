package de.grzb.verleihservice;

import de.grzb.verleihservice.materialien.*;
import de.grzb.verleihservice.repositories.*;
import java.util.*;
import org.springframework.stereotype.*;

@Service
public class VerleihService {

    private VerleihkartenRepository verleihkartenRepository;
    private VormerkkartenRepository vormerkkartenRepository;

    protected VerleihService(VerleihkartenRepository verleihkartenRepository,
                             VormerkkartenRepository vormerkkartenRepository) {
        this.verleihkartenRepository = verleihkartenRepository;
        this.vormerkkartenRepository = vormerkkartenRepository;
    }

    public List<Verleihkarte> getVerleihkarten() {
        return verleihkartenRepository.getVerleihkarten();
    }

    public boolean istVerliehen(Long mediumId) {
        assert mediumId != null : "Vorbedingung verletzt: medienIdExistiert(mediumId)";
        return verleihkartenRepository.verleihkarteExistiertFuer(mediumId);
    }

    public boolean istVerleihenMoeglich(Long kundenId, List<Long> medienIds) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert medienValide(medienIds) : "Vorbedingung verletzt: medienValide(medienIds)";

        boolean result = sindAlleNichtVerliehen(medienIds);

        for (Long mediumId : medienIds) {
            if (istVorgemerkt(mediumId)) {
                Vormerkkarte karte = getVormerkkarteFuer(mediumId);
                if (!karte.istErsterVormerker(kundenId)) {
                    result = false;
                }
            }
        }

        return result;
    }

    public void nimmZurueck(List<Long> medienIds, Calendar rueckgabeDatum) {
        assert sindAlleVerliehen(medienIds) : "Vorbedingung verletzt: sindAlleVerliehen(medienIds)";
        assert rueckgabeDatum != null : "Vorbedingung verletzt: rueckgabeDatum != null";

        verleihkartenRepository.loescheVerleihkartenFuer(medienIds);
    }

    public boolean sindAlleNichtVerliehen(List<Long> medienIds) {
        assert medienValide(medienIds) : "Vorbedingung verletzt: medienValide(medienIds)";
        boolean result = true;
        for (Long mediumId : medienIds) {
            if (istVerliehen(mediumId)) {
                result = false;
            }
        }
        return result;
    }

    public boolean sindAlleVerliehenAn(Long kundenId, List<Long> medienIds) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert medienValide(medienIds) : "Vorbedingung verletzt: medienValide(medienIds)";

        boolean result = true;
        for (Long mediumId : medienIds) {
            if (!istVerliehenAn(kundenId, mediumId)) {
                result = false;
            }
        }
        return result;
    }

    public boolean istVerliehenAn(Long kundenId, Long mediumId) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert mediumId != null : "Vorbedingung verletzt: mediumId != null";

        return istVerliehen(mediumId) && getEntleiherFuer(mediumId).equals(kundenId);
    }

    public boolean sindAlleVerliehen(List<Long> medienIds) {
        assert medienValide(medienIds) : "Vorbedingung verletzt: medienValide(medienIds)";

        boolean result = true;
        for (Long mediumId : medienIds) {
            if (!istVerliehen(mediumId)) {
                result = false;
            }
        }
        return result;
    }

    public void verleiheAn(Long kundenId, List<Long> medienIds, Calendar ausleihDatum) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert sindAlleNichtVerliehen(medienIds) :
                "Vorbedingung verletzt: sindAlleNichtVerliehen(medienIds) ";
        assert ausleihDatum != null : "Vorbedingung verletzt: ausleihDatum != null";
        assert istVerleihenMoeglich(kundenId, medienIds) :
                "Vorbedingung verletzt:  istVerleihenMoeglich(kundenId, medienIds)";

        int[] arrayDatum = new int[3];
        arrayDatum[0] = ausleihDatum.get(Calendar.DAY_OF_MONTH);
        arrayDatum[1] = ausleihDatum.get(Calendar.MONTH) + 1;
        arrayDatum[2] = ausleihDatum.get(Calendar.YEAR);
        for (Long mediumId : medienIds) {
            Verleihkarte verleihkarte = new Verleihkarte(kundenId, mediumId, arrayDatum);

            if (istVorgemerkt(mediumId)) {
                List<Long> medienIdAsList = new ArrayList<>();
                medienIdAsList.add(mediumId);
                entferneVormerker(kundenId, medienIdAsList);
            }
            verleihkartenRepository.speichere(verleihkarte);
        }
    }

    public List<Long> getAusgelieheneMedienFuer(Long kundenId) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";

        List<Long> result = new ArrayList<>();
        List<Verleihkarte> verleihkarten = getVerleihkartenFuer(kundenId);
        for (Verleihkarte verleihkarte : verleihkarten) {
            result.add(verleihkarte.getMediumId());
        }
        return result;
    }

    public Long getEntleiherFuer(Long mediumId) {
        assert istVerliehen(mediumId) : "Vorbedingung verletzt: verleihkarteExistiertFuer(mediumId)";
        return getVerleihkarteFuer(mediumId).getEntleiherId();
    }

    public Verleihkarte getVerleihkarteFuer(Long mediumId) {
        assert istVerliehen(mediumId) : "Vorbedingung verletzt: verleihkarteExistiertFuer(mediumId)";
        return verleihkartenRepository.getVerleihkarteFuer(mediumId);
    }

    public List<Verleihkarte> getVerleihkartenFuer(Long kundenId) {
        return verleihkartenRepository.getVerleihkartenFuer(kundenId);
    }

    public void entferneVormerker(Long kundenId, List<Long> medienIds) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert sindAlleVorgemerktVon(kundenId, medienIds) :
                "Vorbedingung verletzt: sindAlleVorgemerktVon(kundenId, medienIds)";

        for (Long mediumId : medienIds) {
            Vormerkkarte vormerkkarte = getVormerkkarteFuer(mediumId);
            vormerkkarte.entferneVormerker(kundenId);
            if (vormerkkarte.getVormerkerIds().size() == 0) {
                vormerkkartenRepository.loesche(vormerkkarte);
            }
        }
    }

    public boolean istVorgemerktVon(Long kundenId, Long mediumId) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert mediumId != null : "Vorbedingung verletzt: mediumId != null";

        return getVormerkkarteFuer(mediumId).getVormerkerIds().contains(kundenId);
    }

    public boolean kannVorgemerktWerdenVon(Long kundenId, Long mediumId) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert mediumId != null : "Vorbedingung verletzt: mediumId != null";

        boolean vormerkenMoeglich = true;
        boolean bereitsVonKundeEntliehen = false;
        Vormerkkarte vormerkkarte = vormerkkartenRepository.getVormerkkarteFuer(mediumId);
        Verleihkarte verleihkarte = verleihkartenRepository.getVerleihkarteFuer(mediumId);

        if (vormerkkarte != null) {
            vormerkenMoeglich = vormerkkarte.akzeptiert(kundenId);
        }

        if (verleihkarte != null) {
            bereitsVonKundeEntliehen = verleihkarte.getEntleiherId().equals(kundenId);
        }
        return vormerkenMoeglich && !bereitsVonKundeEntliehen;
    }

    public boolean koennenAlleVorgemerktWerdenVon(Long kundenId, List<Long> medienIds) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert medienValide(medienIds) : "Vorbedingung verletzt: medienValide(medienIds)";

        for (Long mediumId : medienIds) {
            if (!kannVorgemerktWerdenVon(kundenId, mediumId)) {
                return false;
            }
        }
        return true;
    }

    public void merkeVor(Long kundenId, List<Long> medienIds) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert medienValide(medienIds) : "Vorbedingung verletzt: medienValide(medienIds)";
        assert koennenAlleVorgemerktWerdenVon(kundenId, medienIds) :
                "Vorbedingung verletzt: koennenAlleVorgemerktWerdenVon(kundenId, medienIds)";

        for (Long mediumId : medienIds) {
            Vormerkkarte vormerkkarte;
            if (!istVorgemerkt(mediumId)) {
                vormerkkarte = new Vormerkkarte(mediumId, kundenId);
            } else {
                vormerkkarte = getVormerkkarteFuer(mediumId);
                vormerkkarte.merkeVor(kundenId);
            }
            vormerkkartenRepository.speichere(vormerkkarte);
        }
    }

    public boolean sindAlleVorgemerktVon(Long kundenId, List<Long> medienIds) {
        assert kundenId != null : "Vorbedingung verletzt: kundenId != null";
        assert medienValide(medienIds) : "Vorbedingung verletzt: medienValide(medienIds)";
        for (Long mediumId : medienIds) {
            if (!istVorgemerktVon(kundenId, mediumId)) {
                return false;
            }
        }
        return true;
    }

    public boolean istVorgemerkt(Long mediumId) {
        assert mediumId != null : "Vorbedingung verletzt: mediumId != null";
        return vormerkkartenRepository.vormerkkarteExistiertFuer(mediumId);
    }

    public Vormerkkarte getVormerkkarteFuer(Long mediumId) {
        assert mediumId != null : "Vorbedingung verletzt: mediumId != null";
        assert istVorgemerkt(mediumId) : "Vorbedingung verletzt: istVorgemerkt(mediumId)";
        return vormerkkartenRepository.getVormerkkarteFuer(mediumId);
    }

    private boolean medienValide(List<Long> medienIds) {
        assert medienIds != null : "Vorbedingung verletzt: medienIds != null";
        assert !medienIds.isEmpty() : "Vorbedingung verletzt: !medienIds.isEmpty()";

        boolean result = true;
        for (Long mediumId : medienIds) {
            if (mediumId == null) {
                result = false;
                break;
            }
        }
        return result;
    }
}
