package ru.FSPO.AIS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.FSPO.AIS.dao.BcLinkDAO;
import ru.FSPO.AIS.models.BcLink;

import java.security.Principal;

@Service("landlordDetailsServiceImpl")
public class LandlordDetailsServiceImpl implements UserDetailsService {

    private final BcLinkDAO bcLinkDAO;

    @Autowired
    public LandlordDetailsServiceImpl(BcLinkDAO bcLinkDAO) {
        this.bcLinkDAO = bcLinkDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        BcLink bcLink = bcLinkDAO.getByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Landlord not found"));
        return SecurityUser.fromUser(bcLink);
    }
}

