package me.jaeyeopme.sns.domain.user.api;

import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.application.AccountService;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(AccountAPI.ACCOUNT_API_V1)
@RequiredArgsConstructor
@RestController
public class AccountAPI {

    public static final String ACCOUNT_API_V1 = "/api/v1/accounts";

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final AccountRequest request) {
        final var userId = accountService.create(request);
        final var location = URI.create("%s/%s".formatted(ACCOUNT_API_V1, userId));

        return ResponseEntity.created(location).build();
    }

}
