package work.lince.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Slf4j
@Service
public class DateService {

    protected OffsetDateTime offsetDateTimeNow() {
        return OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
    }

}