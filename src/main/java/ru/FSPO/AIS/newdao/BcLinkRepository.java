package ru.FSPO.AIS.newdao;

import org.springframework.data.repository.CrudRepository;
import ru.FSPO.AIS.newmodels.BcLink;
import ru.FSPO.AIS.newmodels.DataJpaTestEntity;

import java.util.Optional;

public interface BcLinkRepository  extends CrudRepository<BcLink, Long> {
    Optional<BcLink> findBcLinkByLogin(String login);
}
