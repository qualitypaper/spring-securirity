import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.minus(15, ChronoUnit.MINUTES);
        Duration difference = Duration.between(now, then);
        System.out.println(difference.toMinutes());
    }
}
