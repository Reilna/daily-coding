package com.reilna.bookapi;

import java.time.OffsetDateTime;

public record LoanResponse(
        Long id,
        Long bookInstanceId,
        String username,
        OffsetDateTime loanedAt,
        OffsetDateTime returnedAt
) {
}
