package com.theepicsplit.models;

import com.intellij.util.messages.Topic;

public interface FilterStateChangedNotifier {
	String FILTER_STATE_CHANGED_TOPIC_NAME = "ConfigChangedNotifier";
	Topic<FilterStateChangedNotifier> TOPIC = Topic.create(FILTER_STATE_CHANGED_TOPIC_NAME, FilterStateChangedNotifier.class);

	void execute(FilterState newState);
}
