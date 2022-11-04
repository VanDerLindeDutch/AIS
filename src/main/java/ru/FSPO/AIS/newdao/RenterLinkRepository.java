package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.RenterLink;

import java.util.Optional;

public interface RenterLinkRepository extends CrudRepository<RenterLink, Long> {

    Optional<RenterLink> findRenterLinkByLogin(String login);
}
