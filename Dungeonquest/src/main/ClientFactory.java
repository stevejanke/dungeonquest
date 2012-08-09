package main;

import java.util.Random;

import com.google.common.eventbus.EventBus;

public interface ClientFactory {

	MainFrame getMainFrame();

	EventBus getEventBus();
	
	Random getRandomizer();
	
}
