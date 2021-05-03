package ru.FSPO.AIS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.FSPO.AIS.newdao.RenterLinkRepository;
import ru.FSPO.AIS.newmodels.RenterLink;


@Service("renterDetailsServiceImpl")
public class RenterDetailsServiceImpl implements UserDetailsService{


    private final RenterLinkRepository renterLinkRepository;

    @Autowired
    public RenterDetailsServiceImpl(RenterLinkRepository renterLinkRepository) {
        this.renterLinkRepository = renterLinkRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        RenterLink renterLink = renterLinkRepository.findRenterLinkByLogin(login).orElseThrow(()-> new UsernameNotFoundException("Renter not found"));
        return SecurityUser.fromUser(renterLink);
    }
}
