package inno.edu.api.domain.user.transaction.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private UUID id;
    private UUID userId;
    private UUID appointmentId;

    private BigDecimal value;

    @Enumerated
    private TransactionType type;
}
