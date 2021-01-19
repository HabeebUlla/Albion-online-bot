import com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker.EventParser;
import com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker.EventStructure;
import com.github.ElgorTheWanderer.AlbionKillboardClient.EventTracker.KillboardEventFormatter;
import com.github.ElgorTheWanderer.AlbionKillboardClient.GuildIdRepository;
import com.github.ElgorTheWanderer.DiscordManager.DiscordManager;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.ElgorTheWanderer.AlbionKillboardClient.AlbionKillboardClientImpl.guildIdRepository;

public class Test {
    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Start");
                for (int i = 0; i < 5; i++) {
                    System.out.println("Guild events requested");
                }
            }
        };
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(task, 5, 10, TimeUnit.SECONDS);
    }
    public static void main(String[] args){
        Test test = new Test();
        test.start();
    }
}
