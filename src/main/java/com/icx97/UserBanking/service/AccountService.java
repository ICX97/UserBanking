package com.icx97.UserBanking.service;


import com.icx97.UserBanking.dto.AccountDTO;
import com.icx97.UserBanking.exception.CustomException;
import com.icx97.UserBanking.mapper.AccountMapper;
import com.icx97.UserBanking.model.Account;
import com.icx97.UserBanking.model.User;
import com.icx97.UserBanking.repository.AccountRepository;
import com.icx97.UserBanking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper = AccountMapper.INSTANCE;
    private final UserRepository userRepository;

    public AccountDTO createAccount(AccountDTO accountDTO) {
        logger.info("Creating Account: {}", accountDTO.getAccountNumber());
        Account account = accountMapper.accountDTOToAccount(accountDTO);
        User user = userRepository.findById(accountDTO.getUserId())
                .orElseThrow(() -> new CustomException("User not found with id: " + accountDTO.getUserId()));

        account.setUser(user);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.accountToAccountDTO(savedAccount);
    }

    public List<AccountDTO> getAllAccounts() {
        logger.info("Fetching all Accounts");
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::accountToAccountDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO getAccountById(Long id) {
        logger.info("Fetching Account with id: {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new CustomException("Account with id: " + id + " does not exist"));
        return accountMapper.accountToAccountDTO(account);
    }

    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        logger.info("Updating Account with id: {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new CustomException("Account with id: " + id + " does not exist"));

        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setBalance(accountDTO.getBalance());

        Account updatedAccount = accountRepository.save(account);
        return accountMapper.accountToAccountDTO(updatedAccount);
    }

    public void deleteAccount(Long id) {
        logger.info("Deleting Account with id: {}", id);
        accountRepository.deleteById(id);
    }
}
