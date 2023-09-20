package com.pacoprojects.portfolio.email;

import lombok.Builder;

@Builder
public record EmailObject(String recipient, String subject, String message, String attachment) {
}
