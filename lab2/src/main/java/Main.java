import config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.StoreService;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        AppConfig.class
                );

        StoreService store =
                context.getBean(StoreService.class);

        store.run();

        context.close();
    }
}