package com.theepicsplit.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import com.theepicsplit.adapters.FileTypeOpenedListener;
import com.theepicsplit.models.FilterState;
import com.theepicsplit.models.FilterStateChangedNotifier;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.APP)
public final class SetsListenerService implements StartupActivity {
	private MessageBusConnection _messageBusConnection;

	public static SetsListenerService getInstance() {
		return ApplicationManager.getApplication().getService(SetsListenerService.class);
	}

	private void _setListener(FilterState filterState) {
		MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();

		this._messageBusConnection.disconnect();
		this._messageBusConnection = messageBus.connect();
		this._trySubscribeToFileTypeOpened(filterState);
	}

	private void _trySubscribeToFileTypeOpened(FilterState filterState) {
		if (!filterState.isFilterEnabled) {
			return;
		}

		this._messageBusConnection.subscribe(
				FileEditorManagerListener.FILE_EDITOR_MANAGER,
				new FileTypeOpenedListener(filterState.filterRegex)
		);
	}

	@Override
	public void runActivity(@NotNull Project project) {
		MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();

		messageBus.connect().subscribe(FilterStateChangedNotifier.TOPIC, this::_setListener);
		this._messageBusConnection = messageBus.connect();
	}
}
