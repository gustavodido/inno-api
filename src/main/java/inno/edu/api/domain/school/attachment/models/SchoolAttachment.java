package inno.edu.api.domain.school.attachment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.UUID;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SchoolAttachmentPrimaryKey.class)
public class SchoolAttachment {
    @Id
    private UUID schoolId;

    @Id
    private UUID attachmentId;
}