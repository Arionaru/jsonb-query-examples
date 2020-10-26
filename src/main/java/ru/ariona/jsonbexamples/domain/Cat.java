package ru.ariona.jsonbexamples.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({
        @TypeDef(
                typeClass = JsonBinaryType.class,
                name = "jsonb"
        )
})
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long id;

    private String name;
    @Type(type = "jsonb")
    private CatProps props;

    @Type(type = "jsonb")
    private List<Slave> slaves;

    @Type(type = "jsonb")
    private List<String> toys;

}
