package com.theepicsplit.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.messages.MessageBus;
import com.theepicsplit.infrastructure.FilterStateChangedNotifier;
import com.theepicsplit.state.FilterState;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.APP)
@State(name = "FilterState", storages = @Storage(value = "filterState.xml", roamingType = RoamingType.DISABLED))
public final class PersistsStateService implements PersistentStateComponent<FilterState> {
    private FilterState _state = new FilterState();

    public static PersistsStateService getInstance() {
        return ApplicationManager.getApplication().getService(PersistsStateService.class);
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
    public void initializeComponent() {
        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        FilterStateChangedNotifier handler = newFilterState -> PersistsStateService.getInstance().loadState(newFilterState);

        messageBus.connect().subscribe(FilterStateChangedNotifier.TOPIC, handler);
    }
}
