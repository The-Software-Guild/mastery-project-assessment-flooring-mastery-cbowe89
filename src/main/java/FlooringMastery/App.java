package FlooringMastery;

import FlooringMastery.controller.Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        Controller controller = ctx.getBean("controller", Controller.class);

        // Run the controller
        controller.run();
    }
}
