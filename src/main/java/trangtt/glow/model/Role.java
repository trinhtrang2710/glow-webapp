package trangtt.glow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="role")
@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="role_type")
    private String roleType;

    @Column(name="description")
    private String description;
}
