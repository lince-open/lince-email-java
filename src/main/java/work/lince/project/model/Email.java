package work.lince.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String id;

    private Set<String> to;

    private String subject;

    private String body;

    private EmailFormat type;

    private EmailStatus status;

    private String owner;

    private OffsetDateTime create;

}
