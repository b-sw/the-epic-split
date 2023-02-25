package com.theepicsplit.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.theepicsplit.state.FilterState;

@Service(Service.Level.APP)
public final class SetsListenerService {
    private final FilterState _state = new FilterState();

    public static SetsListenerService getInstance() {
        return ApplicationManager.getApplication().getService(SetsListenerService.class);
    }
}
