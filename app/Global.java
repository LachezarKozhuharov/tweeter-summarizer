import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import model.TweetCollector;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		super.onStart(app);
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(new TweetCollector());
	}
}
