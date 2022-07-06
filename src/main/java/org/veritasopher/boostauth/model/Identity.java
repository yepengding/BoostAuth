package org.veritasopher.boostauth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Identity entity
 *
 * @author Yepeng Ding
 */
@Entity
@Table(name = "auth_identity")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "source")
    private String source;

    @Column(name = "create_date")
    @CreatedDate
    private Date createDate;

    @Column(name = "update_date")
    @LastModifiedDate
    private Date updateDate;

    @Column(name = "status")
    private int status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token", referencedColumnName = "id")
    private Token token;

}
