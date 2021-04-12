package ru.FSPO.AIS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.FSPO.AIS.dao.RenterLinkDAO;
import ru.FSPO.AIS.models.RenterLink;

@Service("renterDetailsServiceImpl")
public class RenterDetailsServiceImpl implements UserDetailsService{

    private final RenterLinkDAO renterLinkDAO;

    @Autowired
    public RenterDetailsServiceImpl(RenterLinkDAO renterLinkDAO) {
        this.renterLinkDAO = renterLinkDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        RenterLink renterLink = renterLinkDAO.getByLogin(login).orElseThrow(()-> new UsernameNotFoundException("Renter not found"));
        return SecurityUser.fromUser(renterLink);
    }
}
