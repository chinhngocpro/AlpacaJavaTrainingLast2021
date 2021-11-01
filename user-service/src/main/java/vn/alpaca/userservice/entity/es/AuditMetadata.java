package vn.alpaca.userservice.entity.es;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.Instant;

@Data
public class AuditMetadata {

    @CreatedBy
    private vn.alpaca.userservice.entity.es.UserES creator;

    @CreatedDate
    @Field(name = "created_date")
    private Instant createdDate;

    @LastModifiedBy
    @Field(name = "last_updater")
    private UserES lastUpdater;

    @LastModifiedDate
    @Field(name = "last_updated_date")
    private Instant lastUpdatedDate;
}
