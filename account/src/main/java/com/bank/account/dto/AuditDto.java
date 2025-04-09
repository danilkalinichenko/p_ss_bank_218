package com.bank.account.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditDto {

    Long id;
    @NotNull
    @Size(min = 1, max = 40)
    String entityType;
    @NotNull
    @Size(min = 1, max = 255)
    String operationType;
    @NotNull
    @Size(min = 1, max = 255)
    String createdBy;
    @Size(min = 1, max = 255)
    String modifiedBy;
    @NotNull
    ZonedDateTime createdAt;
    ZonedDateTime modifiedAt;
    String newEntityJson;
    @NotNull
    String entityJson;
}
