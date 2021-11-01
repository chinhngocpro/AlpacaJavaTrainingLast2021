package vn.alpaca.userservice.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Document(indexName = "users")
@Data
public class UserES {

    @Id
    private Integer id;

    @Field(name = "username", type = FieldType.Keyword)
    private String username;

    @Field(name = "password", type = FieldType.Text)
    private String password;

    @Field(name = "full_name", type = FieldType.Text)
    private String fullName;

    @Field(name = "gender", type = FieldType.Boolean)
    private boolean gender;

    @Field(name = "id_card_number", type = FieldType.Keyword)
    private String idCardNumber;

    @Field(name = "date_of_birth", type = FieldType.Date,
            format = DateFormat.date_optional_time)
    private Date dateOfBirth;

    @Field(name = "phone_numbers", type = FieldType.Keyword)
    private List<String> phoneNumbers;

    @Field(name = "email", type = FieldType.Keyword, normalizer = "lowercase")
    private String email;

    @Field(name = "address", type = FieldType.Text)
    private String address;

    @Field(name = "active", type = FieldType.Boolean)
    private boolean active;

    @Field(type = FieldType.Nested, includeInParent = true)
    private RoleES role;
}
