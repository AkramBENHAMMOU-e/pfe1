package ma.pfe.backend.service;

import lombok.extern.slf4j.Slf4j;
import ma.pfe.backend.entites.Admin;
import ma.pfe.backend.entites.LoginResponse;
import ma.pfe.backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public LoginResponse login(String username, String password) {
        log.info("AuthService.login()...");

        Optional<Admin> userOptional = adminRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            Admin admin = userOptional.get();
            if(admin.getPassword().equals(password)) {
                return new LoginResponse("dummy-token", "Login successful");
            }
        }
        return new LoginResponse(null, "Login failed");
    }

    @Override
    public void logout(Long id) {
        log.info("AuthService.logout()...");
        log.info("User with ID " + id + " logged out successfully.");
    }
}
