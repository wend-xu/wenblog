package com.wende.spring.boot.wenblog.controller;

import com.wende.spring.boot.wenblog.dao.HeroDao;
import com.wende.spring.boot.wenblog.domain.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {
    @Autowired
    HeroDao heroDao;

    @RequestMapping("/all")
    public List<Hero> findAllUser(@RequestParam(defaultValue = "666") String token){
        if(token != null && token.equals("10086")){
            List<Hero> heroes = heroDao.findAll();
            return heroes;
        }
       else{
           Hero hero = new Hero();
           hero.setName("hahaha");
           hero.setId(999);
           List<Hero> heroes = new ArrayList<>();
           heroes.add(hero);
           return heroes;
        }

    }

    @RequestMapping("/byId")
    public Hero findById(@RequestParam int id){
        Optional<Hero> optionalHero = heroDao.findById(id);
        return  optionalHero.get();
    }

}
