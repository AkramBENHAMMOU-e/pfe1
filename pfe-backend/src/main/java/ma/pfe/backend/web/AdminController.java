package ma.pfe.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.pfe.backend.entites.LoginRequest;
import ma.pfe.backend.entites.LoginResponse;
import ma.pfe.backend.service.IAdminService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AdminController {

    private final IAdminService iAdminService;

    @PostMapping("")
    public LoginResponse login(@RequestBody LoginRequest authRequest) {
        log.info("AuthController.login()...");
        return iAdminService.login(authRequest.getUsername(), authRequest.getPassword());
    }
    @GetMapping("/logout/{id}")
    public void logout( @PathVariable Long id) {
        log.info("AuthController.logout()...");
        iAdminService.logout(id);
    }
}
