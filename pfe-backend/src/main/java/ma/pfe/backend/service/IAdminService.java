package ma.pfe.backend.service;

import ma.pfe.backend.entites.LoginResponse;

public interface IAdminService {



        LoginResponse login(String username, String password);
        void logout(Long id);

}
