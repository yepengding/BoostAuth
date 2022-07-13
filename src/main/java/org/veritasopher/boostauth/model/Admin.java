package org.veritasopher.boostauth.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Administrator entity
 *
 * @author Yepeng Ding
 */
@Entity
@Table(name = "auth_admin")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "password")
    private String password;

    @Column(name = "level")
    private Integer level;

    @Column(name = "create_date")
    @CreatedDate
    private Date createDate;

    @Column(name = "update_date")
    @LastModifiedDate
    private Date updateDate;

    @Column(name = "status")
    private int status;
}
