package com.pacoprojects.portfolio.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectStatus {

    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    PAUSED("Paused"),
    DELAYED("Delayed"),
    CANCELLED("Cancelled"),
    UNDER_REVIEW("Under review"),
    FUTURE("Future"),
    EMERGENCY("Emergency"),
    MAINTENANCE("Maintenance"),
    TESTING("Testing"),
    LAUNCHED("Launched"),
    HIGHLIGHT("Highlight"),
    OLD("Old");

    private final String description;

    @Override
    public String toString() {
        return this.description;
    }
}
