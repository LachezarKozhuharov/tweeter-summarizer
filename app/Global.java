import model.TweetCollectorScheduler;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		TweetCollectorScheduler collector = new TweetCollectorScheduler();
		collector.start();
	}
}
