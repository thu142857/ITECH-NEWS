package com.itechnews.database.seed;


import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder extends SeedingCaller {

    //@Autowired some beans

    @EventListener
    public void run(ContextRefreshedEvent event) {
        this.call(new Class[]{
                RolesTableSeeder.class,
                UsersTableSeeder.class,
                TagsTableSeeder.class,
                PostsTableSeeder.class
                //some seeder class
        });
    }

}