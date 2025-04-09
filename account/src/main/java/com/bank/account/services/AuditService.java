package com.bank.account.services;


import com.bank.account.dto.AuditDto;
import com.bank.account.model.Audit;

public interface AuditService {
    void logChange(AuditDto auditDto);
     Audit getAudit(Long id);
}
