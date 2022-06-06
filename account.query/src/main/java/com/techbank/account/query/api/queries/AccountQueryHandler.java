package com.techbank.account.query.api.queries;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountsList = new ArrayList<>();

        for (BankAccount bankAccount: bankAccounts) {
            bankAccountsList.add(bankAccount);
        }

        return bankAccountsList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        List<BaseEntity> bankAccountLists = new ArrayList<>();

        if (bankAccount.isEmpty()) {
            return null;
        }

        bankAccountLists.add(bankAccount.get());

        return bankAccountLists;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        List<BaseEntity> bankAccountLists = new ArrayList<>();

        if (bankAccount.isEmpty()) {
            return null;
        }

        bankAccountLists.add(bankAccount.get());

        return bankAccountLists;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        return query.getEqualityType() == EqualityType.GREATER_THAN ? accountRepository.findByBalanceGreaterThan(query.getBalance()) : accountRepository.findByBalanceLessThan(query.getBalance());
    }
}
