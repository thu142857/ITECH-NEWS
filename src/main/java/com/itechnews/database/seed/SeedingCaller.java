package com.itechnews.database.seed;

import com.itechnews.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;


@Component
public class SeedingCaller {

    @Autowired
    ApplicationContext applicationContext;

    private static final String RUNNING_METHOD_NAME = "run";

    private static final Logger LOGGER = LoggerFactory.getLogger(SeedingCaller.class);

    /*
        1. Iterate array of seeder class
        2. Call run method of the class implemented Seeder interface
     */
    void call(Class<?>[] seederClasses) {
        try {
            if (seederClasses != null && seederClasses.length > 0) {
                for (Class<?> clazz : seederClasses) {
                    //way 1 ok
                    //String beanName = clazz.getSimpleName();
                    //beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
                    //CategoriesTableSeeder => bean name: categoriesTableSeeder
                    //Object obj = applicationContext.getBean(beanName);

                    //way 2 ok
                    //Object obj = applicationContext.getBean(clazz);

                    //way 3 ok: don't need to use @component or @Autowired ApplicationContext
                    Object obj = BeanUtil.getBean(clazz);

                    //way 4 exception
                    //Object obj = clazz.newInstance(); //can not auto wired => Null Pointer Exception

                    LOGGER.info("seeding " + clazz.getSimpleName());
                    clazz.getDeclaredMethod(RUNNING_METHOD_NAME, new Class[]{}).invoke(obj);
                    //clazz.getDeclaredMethod(RUNNING_METHOD_NAME, new Class[]{}).invoke(obj, new Object(){});
                    LOGGER.info("seeded " + clazz.getSimpleName());
                    LOGGER.info("===========================");
                }
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
