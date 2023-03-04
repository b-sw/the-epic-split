package com.theepicsplit.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.theepicsplit.models.FilterState;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.APP)
@State(name = "FilterState", storages = @Storage(value = "filterState.xml", roamingType = RoamingType.DISABLED))
public final class StateService implements PersistentStateComponent<FilterState> {
	private FilterState _state = new FilterState();

	public static StateService getInstance() {
		return ApplicationManager.getApplication().getService(StateService.class);
	}

	@Override
	public FilterState getState() {
		return this._state;
	}

	@Override
	public void loadState(@NotNull FilterState state) {
		this._state = state;
	}
}
