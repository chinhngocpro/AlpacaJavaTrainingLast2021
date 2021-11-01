package vn.alpaca.userservice.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "authorities")
@Data
public class AuthorityES {

    @Id
    private int id;

    @Field(name = "permission", type = FieldType.Keyword)
    private String permissionName;

}
