package cn.accessbright.blade.web.controller;

import cn.accessbright.blade.service.RegisterRequest;
import cn.accessbright.blade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.accessbright.blade.domain.system.User;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;

@RestController
@SessionAttributes("users")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/create")
    public User createUsers() {
        RegisterRequest request = new RegisterRequest();

        request.setUsername("lmt");
        request.setPassword("60123");
        request.setPhone("18721218138");
        request.setEmail("601235723@qq.com");
        userService.register(request);

        return userService.findByUsername(request.getUsername());
    }

    @RequestMapping("/findByUsername")
    public User findByUsername(String username, Map<String, Object> model) {
        User user = userService.findByUsername(username);
        model.put("users", user);
        return user;
    }

    @RequestMapping("/update")
    public User update(@ModelAttribute("users") User user, String phone) {
        user.setPhone(phone);
        userService.update(user);
        return user;
    }

    @RequestMapping("/findId")
    public Integer findIdByUsername(String username) {
        return userService.findIdByUsername(username);
    }

}
