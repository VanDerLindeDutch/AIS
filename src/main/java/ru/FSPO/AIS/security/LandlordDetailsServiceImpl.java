package ru.FSPO.AIS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.FSPO.AIS.newdao.BcLinkRepository;
import ru.FSPO.AIS.newmodels.BcLink;
import ru.FSPO.AIS.newmodels.RenterLink;

@Service("landlordDetailsServiceImpl")
public class LandlordDetailsServiceImpl implements UserDetailsService {


    private final BcLinkRepository bcLinkRepository;

    @Autowired
    public LandlordDetailsServiceImpl(BcLinkRepository bcLinkRepository) {
        this.bcLinkRepository = bcLinkRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        BcLink bcLink = bcLinkRepository.findBcLinkByLogin(login).orElseThrow(() -> new UsernameNotFoundException("landlord not foudn"));
        return SecurityUser.fromUser(bcLink);
    }
}

