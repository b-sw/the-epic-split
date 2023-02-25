package com.theepicsplit.infrastructure;

import com.intellij.util.messages.Topic;
import com.theepicsplit.state.FilterState;

public interface FilterStateChangedNotifier {
    String FILTER_STATE_CHANGED_TOPIC_NAME = "ConfigChangedNotifier";
    Topic<FilterStateChangedNotifier> TOPIC = Topic.create(FILTER_STATE_CHANGED_TOPIC_NAME, FilterStateChangedNotifier.class);

    void execute(FilterState newState);
}
