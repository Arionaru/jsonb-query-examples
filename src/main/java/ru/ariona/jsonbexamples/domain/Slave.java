package ru.ariona.jsonbexamples.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Slave {
    @NonNull
    private String name;
    @NonNull
    private Integer age;
}
