package Tuhoc.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Tuhoc.example.demo.entity.UserRole;
import Tuhoc.example.demo.repo.UserRepo;
@Service
public class LoginService implements UserDetailsService{
	@Autowired
	UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Tuhoc.example.demo.entity.User user = userRepo.findByUsername(username);
		// nguồn xác thực đọc từ Database
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found");
		}	
		
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		
		for(UserRole userRole : user.getUserRole()) {
			list.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		
		// tạo user của Security
		// user đăng nhập hiện tại
		User currentUser = new User(username, user.getPassword(), list);
		
		return currentUser;
	}
}
