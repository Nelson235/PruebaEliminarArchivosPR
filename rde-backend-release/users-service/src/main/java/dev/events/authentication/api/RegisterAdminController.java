// AdminRegistrationController.java
package dev.events.authentication.api;

import dev.events.authentication.api.types.RegisterRequest;
import dev.events.authentication.handlers.commands.RegisterAdminHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/public/auth/admin")
public class RegisterAdminController {
    @Autowired
    private RegisterAdminHandler handler;

    @PostMapping(value = "/register")
    public String register(@RequestBody RegisterRequest request) {
        handler.register(new RegisterAdminHandler.Command(
                request.email(),
                request.name(),
                request.password(),
                request.birth(),
                request.phone()
        ));

        return "OK";
    }

}
