package com.itechnews.database.seed;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder extends SeedingCaller {

    //@Autowired some beans
    @Value("${itechnews.seeder.enalble}")
    private Boolean isSeeding;

    @EventListener
    public void run(ContextRefreshedEvent event) {
        if (isSeeding != null && isSeeding) {
            this.call(new Class[]{
                    RolesTableSeeder.class,
                    UsersTableSeeder.class,
                    TagsTableSeeder.class,
                    PostsTableSeeder.class
                    //some seeder class
            });
        }
    }

}