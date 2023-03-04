package com.theepicsplit.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.util.messages.MessageBus;
import com.theepicsplit.models.FilterState;
import com.theepicsplit.models.FilterStateChangedNotifier;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.APP)
@State(name = "FilterState", storages = @Storage(value = "filterState.xml", roamingType = RoamingType.DISABLED))
public final class PersistsStateService implements PersistentStateComponent<FilterState>, StartupActivity {
	private FilterState _state = new FilterState();

	public static PersistsStateService getInstance() {
		return ApplicationManager.getApplication().getService(PersistsStateService.class);
	}

	@Override
	public void initializeComponent() {
		MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();

		messageBus.syncPublisher(FilterStateChangedNotifier.TOPIC).execute(this._state);
		messageBus.connect().subscribe(FilterStateChangedNotifier.TOPIC, this::loadState);
	}

	@Override
	public FilterState getState() {
		return this._state;
	}

	@Override
	public void loadState(@NotNull FilterState state) {
		this._state = state;
	}

	@Override
	public void runActivity(@NotNull Project project) {
		System.out.println("Run activity");
	}
}
