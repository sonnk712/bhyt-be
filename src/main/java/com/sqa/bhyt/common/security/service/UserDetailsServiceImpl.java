package com.sqa.bhyt.common.security.service;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sqa.bhyt.common.constants.Constants;
import com.sqa.bhyt.entity.User;
import com.sqa.bhyt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameAndIsDeleted(username, Constants.NOT_ACTIVATE)
				.orElseThrow(()-> new UsernameNotFoundException("User Not Found with username" + username));
		return UserDetailsImpl.build(user);
	}

}
