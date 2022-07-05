package org.veritasopher.boostauth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Token Entity
 *
 * @author Yepeng Ding
 */
@Entity
@Table(name = "auth_token")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", unique = true)
    private String content;

    @Column(name = "issuing_date")
    private Date issuingDate;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "status")
    private int status;

}
