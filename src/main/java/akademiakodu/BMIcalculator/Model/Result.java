package akademiakodu.BMIcalculator.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users_result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "result_id")
    private Integer id;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "result")
    private Double result;

    @Column(name = "info")
    private String info;

    @Column(name = "date")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
