package com.techbank.account.query;

import com.techbank.account.query.api.queries.*;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QueryApplication {
    @Autowired
    private QueryHandler queryHandler;

    @Autowired
    private QueryDispatcher queryDispatcher;

    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        queryDispatcher.registerHandlers(FindAllAccountQuery.class, queryHandler::handle);
        queryDispatcher.registerHandlers(FindAccountByIdQuery.class , queryHandler::handle);
        queryDispatcher.registerHandlers(FindAccountByHolderQuery.class, queryHandler::handle);
        queryDispatcher.registerHandlers(FindAccountWithBalanceQuery.class, queryHandler::handle);
    }
}
