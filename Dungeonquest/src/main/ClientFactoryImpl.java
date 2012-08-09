package main;

import java.util.Random;

import com.google.common.eventbus.EventBus;

public class ClientFactoryImpl implements ClientFactory {

	private final EventBus eventBus = new EventBus();
	private final MainFrame mainFrame = new MainFrame(this, "Dungeonquest");
	private final Random randomizer = new Random();

	@Override
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public Random getRandomizer() {
		return randomizer;
	}

}
