package vn.alpaca.userservice.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Set;

@Document(indexName = "roles")
@Data
public class RoleES {

  @Id private int id;

  @Field(name = "name", type = FieldType.Keyword)
  private String name;

  @Field(name = "authorities", type = FieldType.Nested, includeInParent = true)
  private Set<AuthorityES> authorities;
}
