package cn.accessbright.blade.web.controller;

import cn.accessbright.blade.domain.system.Menu;
import cn.accessbright.blade.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MenuRepository menuRepository;

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {

        List<Menu> firstLevelMenus = menuRepository.findByParentIsNull();

        model.addAttribute("firstLevelMenus", firstLevelMenus);

        Msg msg = new Msg("测试标题", "测试内容", "额外信息，只对管理员显示");
        model.addAttribute("msg", msg);
        return "index";
    }
}
